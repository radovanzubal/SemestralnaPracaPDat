/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.views.View;
import java.util.List;
/**
 *
 * @author Martin
 */
public class SayingListView extends View{

    private List<Saying> sayings;

    public List<Saying> getSayings() {
        return sayings;
    }
    
    public SayingListView(List<Saying> sayings) {
        super("sayingList.ftl");
        this.sayings = sayings;
    }
    
    
    
}
