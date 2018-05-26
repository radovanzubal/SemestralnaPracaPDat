package sk.fri.uniza.microservice;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * Trieda definuje funkciu prihlásenia a role používateľov. 
 * @author Zubaľ,Šibíková
 */
public class ExampleAuthenticator implements Authenticator<BasicCredentials, User> {
    /**
     * Valid users with mapping user -> roles
     */
    private static final Map<String, Set<String>> VALID_USERS = ImmutableMap.of(
        "guest", ImmutableSet.of(),
        "user", ImmutableSet.of("BASIC_USER"),
        "admin", ImmutableSet.of("ADMIN", "BASIC_USER")
    );
    
    
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory buildSessionFactory = new MetadataSources(registry).addResource("hibernate.cfg2.xml").buildMetadata().buildSessionFactory();
        
    /**
     * Funkcia zavolaná pri prihlasovaní. V databáze vyhľadá všetky objekty typu Uzivatel
     *  so zhodným menom a medzi nimi je potom hľadaný taký ktorý má meno a heslo zhodné s zadaným 
     * @param credentials BasicCredentials
     * @throws AuthenticationException
     * @return Optional User  
     */    
    @Override
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
       ///////načíta z databázy všetkých užívateľov ktorý majú meno rovnaké ako je meno zadané pri prihlásení  
       ///////nie je case sensitive 
       Session session2 = buildSessionFactory.openSession();
       session2.beginTransaction();   
       List <Uzivatel> u =  session2.createQuery("FROM Uzivatel AS uz where uz.meno = :meno").setParameter("meno",credentials.getUsername()).getResultList();
       session2.close();
       
       /////////////////////// prejde list objektov typu Uzivatel a hľadá takého ktorého meno a heslo sa zhodujú so 
       /////////////////////// zadaným menom a heslom ak nájde vráti objekt User s danou právomocou
       if( u.size()>0){
                   for(int i=0;i<u.size();i++){
                   if(u.get(i).getHeslo().equals(credentials.getPassword())&&u.get(i).getMeno().equals(credentials.getUsername())){
                      return Optional.of(new User(credentials.getUsername(), VALID_USERS.get(u.get(i).getPravomoc())));
                    }
                   }
                       }
        return Optional.empty();
    }
}