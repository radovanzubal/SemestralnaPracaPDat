package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
/**
 * Táto trieda vytvára stránku v prehliadači podľa dokumentu "dataAddEdit.ftl" 
 * @author Zubaľ,Šibíková
 */
public class DataAddEditView extends View {

    private final Data data;

    /**
    * getter pre premennú "data"
    *@return data
    */
    public Data getData() {
        return data;
    }

    
    /**
    *Konštruktor triedy DataAddEditView.
    * Inicializuje premennú "data" a rodičovi typu View predáva "dataAddEdit.ftl"
    *@param data objekt typu Data ktorý bdue zobrazený na stránke v prehliadači
    */
    public DataAddEditView(Data data) {
        super("dataAddEdit.ftl");
        this.data = data;
    }

    

}
