package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
import java.util.List;
/**
 * Trieda vytvorí stránku v prehliadači podľa dokumentu "zariadenieList.ftl"
 * @author Zubaľ,Šibíková
 */
public class ZariadenieListView extends View{

    private List<Zariadenie> zariadenia;

    /**
     * Getter pre premennú zariadenia 
     * @return zariadenia
     */
    public List<Zariadenie> getZariadenia() {
        return zariadenia;
    }
    
       /**
     * Konštruktor tejto triedy.
     * Inicializuje premennú zariadenia a rodičovi predá dokument 
     * pre vygenerovanie stránky v prehliadači
     * @param zariadenia nová hodnota globálnej premennej "zariadenia", 
     * ktorej parametre budú zobrazené na stránke v prehliadači 
     */
    public ZariadenieListView(List<Zariadenie> zariadenia) {
        super("zariadenieList.ftl");
        this.zariadenia = zariadenia;
    }
    
    
    
}
