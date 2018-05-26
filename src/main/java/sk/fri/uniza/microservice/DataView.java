package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
/**
 * Trieda vytvorístránku v prehliadači podľa dokumentu "data.ftl"
 * @author Zubaľ,Šibíková
 */
public class DataView extends View {

    private final Data data;
    
    /**
    * Getter pre premennú "data"
    * @return data
    */
    public Data getData() {
        return data;
    }

    /**
     * Konštruktor triedy DataView. Inicializuje premennú "data" a predáva rodičovi 
     * dokument pre vygenerovanie stránky v prehliadači "data.ftl"
     * @param data 
     */
    public DataView(Data data) {
        super("data.ftl");
        this.data = data;
    }

    
}