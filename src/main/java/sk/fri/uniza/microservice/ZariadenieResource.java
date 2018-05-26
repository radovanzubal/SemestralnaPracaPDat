package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
import io.dropwizard.views.View;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Trieda definuje funkcie rest rozhrania. 
 * @author Zubaľ,Šibíková
 */
@Path("/zariadenie")
public class ZariadenieResource {

    private final ZariadenieDAO zariadenieDAO;

    /**
     * Konštruktor tejto triedy inicializuje globálnu premennú "zariadenieDAO"
     * @param zariadenieDAO nová hodnota premennej
     */
    public ZariadenieResource(ZariadenieDAO zariadenieDAO) {
        this.zariadenieDAO = zariadenieDAO;
    }

    /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači, v ktorej zobrazí objekt typu Zariadenie.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie/{id}
     * @param id zariadenia, ktorého parametre majú byť zobrazené
     * @return stránka v prehliadači definovaná dokumentom "zariadenie.ftl"
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public ZariadenieView getZariadenie(@PathParam("id") LongParam id) {
        Optional<Zariadenie> result = zariadenieDAO.findById(id.get());

        if (result.isPresent()) {
            return new ZariadenieView(result.get());
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
        /**
     *Funkcia rest rozhrania. Vráti objekt typu Zariadenie vo formáte JSON.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/zariadenie/{id}
     * @param id zariadenia, ktoré má byť nájdené
     * @return objekt typu Zariadenie vo formáte JSON
     */
    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Zariadenie getZariadenieOne(@PathParam("id") LongParam id) {
        Optional<Zariadenie> result = zariadenieDAO.findById(id.get());

        if (result.isPresent()) {
            return result.get();
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači pre zmenenie parametrov objektu Zariadenie.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie/edit/{id}
     * @param id zariadenia, ktorého parametre majú byť zmenené
     * @return stránku v prehliadači definovanú dokumentom "zariadenieAddEdit.ftl"
     */
    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public ZariadenieAddEditView getEditForm(@PathParam("id") LongParam id) {
        Optional<Zariadenie> result = zariadenieDAO.findById(id.get());

        if (result.isPresent()) {
            return new ZariadenieAddEditView(result.get());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

        /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači pre pridanie objektu typu Zariadenie.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie/add
     * @return list objektov typu Uzivatel vo formáte JSON
     */
    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public View getAddForm() {
        return new View("zariadenieAddEdit.ftl", StandardCharsets.UTF_8) {
        };
    }

            /**
     * Funkcia rest rozhrania. Pridá objekt typu Zariadenie do databázy.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/zariadenie/add
     *@param typ nového zariadenia
     * @return objekt typu zariadenie vo formáte JSON
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Zariadenie postAddForm(@DefaultValue("nezadane") @QueryParam("typ") String typ) {
        return zariadenieDAO.create(new Zariadenie(typ));
    }
    
            /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači pre zmenu parametrov objektu typu Zariadenie.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/zariadenie/edit
     *@param _id id zariadenia v databáze, ktorého parametre majú byť zmenené
     * @param content nový typ zariadenia
     * @return stránku v prehliadači definovaanú dokumentom "zariadenieAddEdit.ftl"
     */
    @POST
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public ZariadenieView editZariadenie(@FormParam("id") String _id, @FormParam("content") String content ) {
        Optional<Zariadenie> result = zariadenieDAO.findById(Long.parseLong(_id));
        if (result.isPresent()) {
            result.get().setContent(content);
            return new ZariadenieView(result.get());
        } else {
            Zariadenie create = zariadenieDAO.create(new Zariadenie(content));
            return new ZariadenieView(create);
        }
    }

            /**
     *Funkcia rest rozhrania. Vytvorí objekt typu Zariadenie a uloží ho do databázy.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/zariadenie
     * @param zariadenie objekt, ktorý má byť uložený
     * @return objekt typu Zariadenie vo formáte JSON
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public Zariadenie createZariadenie(Zariadenie zariadenie) {
        return zariadenieDAO.create(zariadenie);
    }

            /**
     *Funkcia rest rozhrania. Vymaže z databázy objekt typu Zariadenie, ktoré je dané parametrom id.
     * Zvyšné zariadenia z databázy zobrazí na stránke v prehliadači.
     * Prístup k funkcii má ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie/delete/{id}
     *@param id zariadenia, ktoré má byť vymazané.
     * @return stránku v prehliadači zvyšných objektov typu Zariadenie z databázy.
     */
    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    @RolesAllowed("ADMIN")
    public ZariadenieListView deleteZariadenie(@PathParam("id") LongParam id) {
        Optional<Zariadenie> result = zariadenieDAO.findById(id.get());
        if (result.isPresent()) {
            zariadenieDAO.delete(result.get());
            return new ZariadenieListView(zariadenieDAO.findAll());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
     /**
     *Funkcia rest rozhrania. Vymaže z databázy objekt typu Zariadenie, ktoré je dané parametrom id.
     * Prístup k funkcii má ADMIN. 
     * Funkcia je typu DELETE na URL adrese http://localhost:8080/zariadenie/{id}
     *@param id zariadenia, ktoré má byť vymazané.
     * @return objekt typu Zariadenie vo formáte JSON
     */
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Zariadenie deleteZariadenie2(@PathParam("id") LongParam id) {
        Optional<Zariadenie> result = zariadenieDAO.findById(id.get());
        if (result.isPresent()) {
            zariadenieDAO.delete(result.get());
            return result.get();
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

          /**
     *Funkcia rest rozhrania. Všetky objekty typu Zariadenie zobrazí na stránke v prehliadači.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie/list
     * @return stránku v prehliadači definovanú dokumentom "zariadenieList.ftl".
     */
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public ZariadenieListView getZariadenie() {
        return new ZariadenieListView(zariadenieDAO.findAll());

    }

          /**
     *Funkcia rest rozhrania. Vráti list všetkých objektov typu Zariadenie vo formáte JSON.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/zariadenie
     * @return list objektov typu Zariadenie vo formáte JSON
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @RolesAllowed("BASIC_USER")
    public List<Zariadenie> listZariadenia() {
        return zariadenieDAO.findAll();
    }
}
