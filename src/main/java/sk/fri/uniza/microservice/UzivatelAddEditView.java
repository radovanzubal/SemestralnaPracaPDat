package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "uzivatelAddEdit.ftl"
 * @author Zubaľ,Šibíková
 */
public class UzivatelAddEditView extends View {

    private final Uzivatel uzivatel;

    /**
     * Getter pre premennú "uzivatel"
     * @return uzivatel
     */
    public Uzivatel getUzivatel() {
        return uzivatel;
    }

    
    /**
     * Konštruktor tejto triedy. Inicializuje premennú uzivatel a predáva rodičovi 
     * dokument pre vygenerovanie stránky v prehliadači "uzivatelAddEdit.ftl"
     * @param uzivatel nová hodnota premennej
     */
    public UzivatelAddEditView(Uzivatel uzivatel) {
        super("uzivatelAddEdit.ftl");
        this.uzivatel = uzivatel;
    }

    

}
