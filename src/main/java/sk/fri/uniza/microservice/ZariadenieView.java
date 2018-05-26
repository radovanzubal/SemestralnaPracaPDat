package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "zariadenie.ftl"
 * @author Zubaľ,Šibíková
 */
public class ZariadenieView extends View {

    private final Zariadenie zariadenie;

    /**
     * Getter pre premennú "zariadenie"
     * @return zariadenie
     */
    public Zariadenie getZariadenie() {
        return zariadenie;
    }

     /**
     * Konštruktor tejto triedy.
     * Inicializuje premennú zariadenie a rodičovi predá dokument 
     * pre vygenerovanie stránky v prehliadači
     * @param zariadenie nová hodnota globálnej premennej "zariadenie", 
     * ktorej parametre budú zobrazené na stránke v prehliadači 
     */
    public ZariadenieView(Zariadenie zariadenie) {
        super("zariadenie.ftl");
        this.zariadenie = zariadenie;
    }

    
}
