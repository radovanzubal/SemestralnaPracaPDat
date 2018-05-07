/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.dropwizard.views.View;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author hudik1
 */
@Path("/saying")
@RolesAllowed("BASIC_USER")
public class SayingResource {

    private final SayingDAO sayingDAO;

    public SayingResource(SayingDAO sayingDAO) {
        this.sayingDAO = sayingDAO;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public SayingView getSaying(@PathParam("id") LongParam id) {
        Optional<Saying> result = sayingDAO.findById(id.get());

        if (result.isPresent()) {
            return new SayingView(result.get());
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public SayingAddEditView getEditForm(@PathParam("id") LongParam id) {
        Optional<Saying> result = sayingDAO.findById(id.get());

        if (result.isPresent()) {
            return new SayingAddEditView(result.get());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public View getAddForm() {
        return new View("sayingAddEdit.ftl", StandardCharsets.UTF_8) {
        };
    }

    @POST
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    public SayingView editSaying(@FormParam("id") String _id, @FormParam("content") String content /* Saying saying*/) {
        Optional<Saying> result = sayingDAO.findById(Long.parseLong(_id));
        if (result.isPresent()) {
            result.get().setContent(content);
            return new SayingView(result.get());
        } else {
            Saying create = sayingDAO.create(new Saying(content));
            return new SayingView(create);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Saying createSaying(Saying saying) {
        return sayingDAO.create(saying);
    }

    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public SayingListView deleteSaying(@PathParam("id") LongParam id) {
        Optional<Saying> result = sayingDAO.findById(id.get());
        if (result.isPresent()) {
            sayingDAO.delete(result.get());
            return new SayingListView(sayingDAO.findAll());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Saying deleteSaying2(@PathParam("id") LongParam id) {
        Optional<Saying> result = sayingDAO.findById(id.get());
        if (result.isPresent()) {
            sayingDAO.delete(result.get());
            return result.get();
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public SayingListView getSaying() {
        return new SayingListView(sayingDAO.findAll());

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Saying> listSayings() {
        return sayingDAO.findAll();
    }
}
