/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

/**
 *
 * @author hudik1
 */
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
//import com.example.helloworld.resources.HelloWorldResource;
//import com.example.helloworld.health.TemplateHealthCheck;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    static SessionFactory buildSessionFactory;

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.configure();

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        buildSessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<HelloWorldConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<HelloWorldConfiguration>(){
            @Override
            public Map<String, Map<String, String>> getViewConfiguration(HelloWorldConfiguration configuration) {
                
                return super.getViewConfiguration(configuration); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    

    @Override
    public void run(HelloWorldConfiguration configuration,
            Environment environment) {
        
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        
        final SayingResource sayingResource = new SayingResource();
        
        final TemplateHealthCheck healthCheck
                = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
        environment.jersey().register(sayingResource);
        

    }

}
