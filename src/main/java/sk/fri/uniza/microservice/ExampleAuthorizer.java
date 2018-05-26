package sk.fri.uniza.microservice;

import io.dropwizard.auth.Authorizer;
/**
 * Trieda definuje funkciu kontrolujúcu rolu prihlaseného "usera"
 * @author Zubaľ,Šibíková
 */
public class ExampleAuthorizer implements Authorizer<User> {
   
    /**
     * Funkcia zavolaná pri prihlasovaní. 
     * @param user užívateľ
     * @param role právomoc užívateľa 
     * @return boolean true ak bola používateľovi pridelená daná právomoc  
     */     
    @Override
    public boolean authorize(User user, String role) {
        return user.getRoles() != null && user.getRoles().contains(role);
    }
}
