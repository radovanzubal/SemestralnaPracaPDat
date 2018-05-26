package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
import java.util.List;
/**
 * Trieda pre vytvorenie stránky v prehliadači podľa dokumentu "dataList.ftl"
 * @author Zubaľ,Šibíková
 */
public class DataListView extends View{

    private List<Data> datas;

    /**
     * Getter pre premennú "datas"
     * @return datas 
     */
    public List<Data> getDatas() {
        return datas;
    }
    
    /**
     * Konštruktor triedy DataListView, inicialiazuje premennú "datas" a rodičovi 
     * predáva pre zobrazenie dokument "dataList.ftl"
     * @param datas List objektov typu Data, ktoré budú zobrazené v "dataList.ftl" 
     */
    public DataListView(List<Data> datas) {
        super("dataList.ftl");
        this.datas = datas;
    }
}
