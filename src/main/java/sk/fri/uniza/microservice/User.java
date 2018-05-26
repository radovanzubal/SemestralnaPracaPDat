package sk.fri.uniza.microservice;

import java.security.Principal;
import java.util.Set;
/**
 * Trieda obsahuje údaje prihláseného používateľa.
 * @author Zubaľ,Šibíková
 */
public class User implements Principal {
    private final String name;

    private final Set<String> roles;

    /**
     * Konštruktor tejto triedy. Inicializuje hodnoty premenných "name" a "roles"=null
     * @param name nová hodnota premennej name
     */
    public User(String name) {
        this.name = name;
        this.roles = null;
    }

    
    /**
     * Konštruktor tejto triedy. Inicializuje hodnoty premenných "name" a "roles"
     * @param name nová hodnota premennej
     * @param roles nová hodnota premennej 
     */
    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    /**
     * Getter pre premennú "name"
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Vráti id 
     * @return náhodný int v rozmedzí 0 až 99
     */
    public int getId() {
        return (int) (Math.random() * 100);
    }

    /**
     * Getter pre premennú "roles"
     * @return roles
     */
    public Set<String> getRoles() {
        return roles;
    }
}
