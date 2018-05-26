package sk.fri.uniza.microservice;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import java.util.Map;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * Hlavná trieda programu. Vytvorí databázu zaregistruje "Resource! triedy a pridá do databázy dvoch adminov. 
 * @author Zubaľ,Šibíková
 */
public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    static SessionFactory buildSessionFactory;

    private final HibernateBundle<HelloWorldConfiguration> hibernateBundle = new HibernateBundle<HelloWorldConfiguration>(Zariadenie.class,Data.class,Uzivatel.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(HelloWorldConfiguration t) {
            return t.getDataSourceFactory();
        }
    };


    /**
     * spustí "run" metódu tejto triedy a uloží do databázy dvoch užívateľov.
     * Prvý užívateľ s menom "Radko", heslom "van" a právomocou "admin"   
     * Druhý užívateľ s menom "Zdenka", heslom "zdenka" a právomocou "admin"   
     * @param args vstupné parametre programu
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {

        new HelloWorldApplication().run(args);
        
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory buildSessionFactory = new MetadataSources(registry).addResource("hibernate.cfg2.xml").buildMetadata().buildSessionFactory();
        Session session2 = buildSessionFactory.openSession();
        session2.beginTransaction();         
        session2.save(new Uzivatel("Radko","van","admin"));
        session2.save(new Uzivatel("Zdenka","zdenka","admin"));
        session2.getTransaction().commit();
        session2.close();
        
    }
    
    
    /**
    * Vráti meno aplikácie 
    *@return String "hello_world"
    */
    @Override
    public String getName() {
        return "hello-world";
    }

     /**
     * Inicializačná funkcia bootstrapu
     * @param bootstrap 
     */
    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(new AssetsBundle());
        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>() {
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(HelloWorldConfiguration configuration) {

                return super.getViewConfiguration(configuration); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    /**
     * Funkcia pre vytvorenie a nastavenie resource tried a pre vytvorenie DAO objektov 
     * @param configuration HelloWorldConfiguration
     * @param environment Environment
     */
    @Override
    public void run(HelloWorldConfiguration configuration,Environment environment) {

        final ZariadenieDAO dao = new ZariadenieDAO(hibernateBundle.getSessionFactory());
        final DataDAO dao2 = new DataDAO(hibernateBundle.getSessionFactory());
        final UzivatelDAO dao3 = new UzivatelDAO(hibernateBundle.getSessionFactory());

        final ZariadenieResource sayingResource = new ZariadenieResource(dao);
        final DataResource dataResource = new DataResource(dao2);
        final UzivatelResource uzivatelResource = new UzivatelResource(dao3);

        final TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());

        environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ExampleAuthenticator())
                .setAuthorizer(new ExampleAuthorizer())
                .setRealm("SUPER SECRET STUFF")
                .buildAuthFilter()));
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
        environment.jersey().register(RolesAllowedDynamicFeature.class);

        environment.healthChecks().register("template", healthCheck);
        
        environment.jersey().register(sayingResource);
        environment.jersey().register(dataResource);
        environment.jersey().register(uzivatelResource);            
    }
}
