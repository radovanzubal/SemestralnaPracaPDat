package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
import java.util.List;
/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "uzivateList.ftl"
 * @author Zubaľ,Šibíková
 */
public class UzivatelListView extends View{

    private List<Uzivatel> uzivatels;

    /**
     * Getter pre globálny parameter uzivatels 
     * @return uzivatels 
     */
    public List<Uzivatel> getUzivatels() {
        return uzivatels;
    }
    
    /**
     * Konštruktor tejto triedy. Inicializuje premennú uzivatels a predáva rodičovi 
     * dokument pre vygenerovanie stránky v prehliadači "uzivatelList.ftl"
     * @param uzivatels nová hodnota premennej
     */
    public UzivatelListView(List<Uzivatel> uzivatels) {
        super("uzivatelList.ftl");
        this.uzivatels = uzivatels;
    }
}
