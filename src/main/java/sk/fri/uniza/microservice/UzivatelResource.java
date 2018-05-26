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
 * Trieda definuje funkcie rest rozhrania
 * @author Zubaľ,Šibíková
 */
@Path("/uzivatel")
public class UzivatelResource {

    private final UzivatelDAO uzivatelDAO;

    /**
     * Konštruktor tejto triedy. Inicializuje globálnu premennú "uzivatelDAO"
     * @param uzivatelDAO nová hodnota premennej
     */
    public UzivatelResource(UzivatelDAO uzivatelDAO) {
        this.uzivatelDAO = uzivatelDAO;
    }

    /**
     Funkcia rest rozhrania. Uloží objekt Uzivatel do databázy 
     * a vráti ho vo formáte JSON.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/uzivatel/add
     * @param meno nové meno užívateľa 
     * @param heslo nové heslo užívateľa
     * @param pravomoc právomoc nového užívateľa
     * @return objekt Uzivatel vo formáte JSON
     */
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Uzivatel createUzivatel(@DefaultValue("nezadane") @QueryParam("meno") String meno,@DefaultValue("nezadane") @QueryParam("heslo") String heslo,@DefaultValue("guest") @QueryParam("pravomoc") String pravomoc) {
        try{
        if(!pravomoc.equals("admin")&&!pravomoc.equals("user")){pravomoc="guest";}
        }catch(Exception e){pravomoc="guest";}
        
        return uzivatelDAO.create(new Uzivatel(meno,heslo,pravomoc));
    }

    /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači v ktorej zobrazí  
     * bližšie informácie o objekte Uzivatel načítaného z databázy, ktorý bol nájdený podľa 
     * parametru metódy "id"
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel/{id}
     * @param id id užívateľa pod ktorým je uložený v databáze 
     * @return stránku v prehliadači určenú dokumentom "uzivatel.ftl"
     */
    @GET
    @Path("/{id}")
    @RolesAllowed("BASIC_USER")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public UzivatelView getUzivatel(@PathParam("id") LongParam id) {
        Optional<Uzivatel> result = uzivatelDAO.findById(id.get());

        if (result.isPresent()) {
            return new UzivatelView(result.get());
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

     /**
     *Funkcia rest rozhrania. Zmení parametre objektu typu Uzivatel v databáze
     * Prístup k funkcii má ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel/edit/{id}
     * @param id id užívateľa pod ktorým je uložený v databáze 
     * @return stránku v prehliadači určenú dokumentom "uzivatelAddEdit.ftl"
     */
    @GET
    @Path("/edit/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public UzivatelAddEditView getEditForm(@PathParam("id") LongParam id) {
        Optional<Uzivatel> result = uzivatelDAO.findById(id.get());

        if (result.isPresent()) {
            return new UzivatelAddEditView(result.get());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

     /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači v ktorej zobrazí  
     * bližšie informácie o objekte typu Uzivatel pridanom do databázy
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel/add
     * @return stránku v prehliadači určenú dokumentom "uzivatelAddEdit.ftl"
     */
    @GET
    @Path("/add")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public View getAddForm() {
        return new View("uzivatelAddEdit.ftl", StandardCharsets.UTF_8) {
        };
    }

     /**
     *Funkcia rest rozhrania. Vytvorí stránku v prehliadači v ktorej zobrazí  
     * bližšie informácie o objekte Uzivatel, ktorého parametre zmení v databáze
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/uzivatel/edit
     * @param _id id užívateľa pod ktorým je uložený v databáze 
     * @param meno nové meno užívateľa 
     * @param heslo nové heslo užívateľa 
     * @param pravomoc nová právomoc užívateľa 
     * @return stránku v prehliadači určenú dokumentom "uzivatel.ftl"
     */
    @POST
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    public UzivatelView editUzivatel(@FormParam("id") String _id, @FormParam("meno") String meno,@FormParam("heslo") String heslo,@FormParam("pravomoc") String pravomoc) {
        Optional<Uzivatel> result = uzivatelDAO.findById(Long.parseLong(_id));
        try{
        if(!pravomoc.equals("admin")&&!pravomoc.equals("user")){pravomoc="guest";}
        }catch(Exception e){pravomoc="guest";}
        
        if (result.isPresent()) {
            result.get().setHeslo(heslo);
            result.get().setMeno(meno);
            result.get().setPravomoc(pravomoc);
            return new UzivatelView(result.get());
        } else {
            Uzivatel create = uzivatelDAO.create(new Uzivatel(meno,heslo,pravomoc));
            return new UzivatelView(create);
        }
    }

     /**
     *Funkcia rest rozhrania. Vytvori objekt typu Uzivatel a uloží ho do databázy
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/uzivatel
     * @param uzivatel objekt typu Uzivatel, ktorý má byť uložený do databázy
     * @return objekt Uzivatel vo formate JSON
     */    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Uzivatel createUzivatel(Uzivatel uzivatel) {
        try{
        if(!uzivatel.getPravomoc().equals("admin")&&!uzivatel.getPravomoc().equals("user")){uzivatel.setPravomoc("guest");}
        }catch(Exception e){uzivatel.setPravomoc("guest");}
        return uzivatelDAO.create(uzivatel);
    }
    
     /**
     *Funkcia rest rozhrania. Vymaže objekt typu Uzivatel z databázy
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel/delete/{id}
     * @param id objektu Uzivatel v databáze, ktorý má byť vymazaný
     * @return vráti stránku v prehliadači definovanú dokumentom "uzivatelList.ftl"
     */
    @GET
    @Path("/delete/{id}")
    @RolesAllowed("ADMIN")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public UzivatelListView deleteUzivatel(@PathParam("id") LongParam id) {
        Optional<Uzivatel> result = uzivatelDAO.findById(id.get());
        if (result.isPresent()) {
            uzivatelDAO.delete(result.get());
            return new UzivatelListView(uzivatelDAO.findAll());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     *Funkcia rest rozhrania. Vymaže objekt typu Uzivatel z databázy
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu DELETE na URL adrese http://localhost:8080/uzivatel/{id}
     * @param id objektu Uzivatel v databáze, ktorý má byť vymazaný
     * @return Uzivatel vo formáte JSON
     */
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Uzivatel deleteUzivatel2(@PathParam("id") LongParam id) {
        Optional<Uzivatel> result = uzivatelDAO.findById(id.get());
        if (result.isPresent()) {
            uzivatelDAO.delete(result.get());
            return result.get();
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

  /**
     *Funkcia rest rozhrania. Všetky objekty typu Uzivatel nájdené v databáze 
     * Zobrazí na stránke v prehliadači
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel/list
     * @return vráti stránku v prehliadači definovanú dokumentom "uzivatelList.ftl"
     */
    @GET
    @Path("/list")
    @RolesAllowed("BASIC_USER")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public UzivatelListView getUzivatel() {
        return new UzivatelListView(uzivatelDAO.findAll());

    }

    /**
     *Funkcia rest rozhrania. Vráti list všetkých objektov typu Uzivatel nájdených v databáze
     * vo formáte JSON.
     * Prístup k funkcii majú BASIC_USER a ADMIN. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/uzivatel
     * @return list objektov typu Uzivatel vo formáte JSON
     */
    @GET
    @RolesAllowed("BASIC_USER")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Uzivatel> listUzivatel() {
        return uzivatelDAO.findAll();
    }

}
