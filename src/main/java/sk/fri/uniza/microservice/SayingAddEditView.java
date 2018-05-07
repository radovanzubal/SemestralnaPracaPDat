/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 *
 * @author Martin
 */
public class SayingAddEditView extends View {

    private final Saying saying;

    public Saying getSaying() {
        return saying;
    }

    public SayingAddEditView(Saying saying) {
        super("sayingAddEdit.ftl");
        this.saying = saying;
    }

    

}
