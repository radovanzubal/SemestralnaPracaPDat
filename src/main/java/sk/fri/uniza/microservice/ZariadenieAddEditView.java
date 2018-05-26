package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "zariadenieAddEdit.ftl"
 * @author Zubaľ,Šibíková
 */
public class ZariadenieAddEditView extends View {

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
    public ZariadenieAddEditView(Zariadenie zariadenie) {
        super("zariadenieAddEdit.ftl");
        this.zariadenie = zariadenie;
    }

    

}
