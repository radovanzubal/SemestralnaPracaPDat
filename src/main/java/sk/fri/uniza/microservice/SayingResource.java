/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author hudik1
 */
@Path("/saying/{id}")
@Produces(MediaType.TEXT_HTML)
public class SayingResource {
    
    @GET
    public SayingView getSaying(@PathParam("id") String id)
    {
        Session session = HelloWorldApplication.buildSessionFactory.openSession();
        Transaction beginTransaction = session.beginTransaction();

        Query query = session.createQuery("from Saying where id=" + id);
        Saying uniqueResult = (Saying) query.uniqueResult();

        session.getTransaction().commit();
        session.close();
        if (uniqueResult == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
        }
        
        
        return new SayingView(uniqueResult);
    }
//    public ersonView getPerson(@PathParam("id") String id) {
//        return new PersonView(dao.find(id));
//    } 
    
}
