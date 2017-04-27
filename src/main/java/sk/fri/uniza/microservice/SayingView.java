/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.views.View;

/**
 *
 * @author hudik1
 */
public class SayingView extends View {

    private final Saying saying;

    public Saying getSaying() {
        return saying;
    }

    public SayingView(Saying person) {
        super("saying.ftl");
        this.saying = person;
    }

    
}
