package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "uzivatel.ftl"
 * @author Zubaľ,Šibíková
 */
public class UzivatelView extends View {

    private final Uzivatel uzivatel;

    /**
     * Getter pre globálnu premennú "uzivatel"
     * @return uzivatel
     */
    public Uzivatel getUzivatel() {
        return uzivatel;
    }
    
/**
 * Konštruktor tejto triedy.
 * Inicializuje premennú uzivatel a rodičovi predá dokument 
 * pre vygenerovanie stránky v prehliadači
 * @param uzivatel nová hodnota globálnej premennej "uzivatel", 
 * ktorej parametre budú zobrazené na stránke v prehliadači 
 */
    public UzivatelView(Uzivatel uzivatel) {
        super("uzivatel.ftl");
        this.uzivatel = uzivatel;
    }

    
}
