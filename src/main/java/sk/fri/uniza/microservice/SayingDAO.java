/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;

/**
 *
 * @author Martin
 */
public class SayingDAO extends AbstractDAO<Saying> {

    public SayingDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    public void delete(Saying saying){
        currentSession().delete(saying);
    }

    public Optional<Saying> findById(Long id) {
        if (id != null) {
            return Optional.ofNullable(get(id));
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    public Saying create(Saying person) {
        return persist(person);
    }

    public List<Saying> findAll() {
        return list(namedQuery("sk.fri.uniza.microservice.Saying.findAll"));
    }

}
