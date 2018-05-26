package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;
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
 * Treida, ktorá definuje funkcie rest rozhrania
 * @author Zubaľ,Šibíková
 */
@Path("/data")
public class DataResource {

    private final DataDAO dataDAO;

    /**
     * Konštruktor triedy DataResource. Inicializuje premennú "dataDAO"
     * @param dataDAO nová hodnota premennej "dataDAO"
     */
    public DataResource(DataDAO dataDAO) {
        this.dataDAO = dataDAO;
    }
    
    /**
     * Funkcia rest rozhrania. Uloží objekt Data do databázy.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data/add
     * @param idz id Zariadenia, ktoré túto hodnotu nameralo
     * @param hodnota hodnota nameraných údajov
     * @return uloženú triedu "Data" vo formáte JSON
     */
    @GET
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Data getAddForm(@DefaultValue("0") @QueryParam("idz") long idz,@DefaultValue("0") @QueryParam("hodnota") float hodnota) {
        return dataDAO.create(new Data(new Float(hodnota),new Long(idz)));
    }
      
    /**
     * Funkcia rest rozhrania. 
     * Načíta všetky objekty typu Data z databýzy a zobrazí ich na stránke v prehliadači.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data/list
     * @return DataListView 
     */    
    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public DataListView getSaying() {
        return new DataListView(dataDAO.findAll());

    }
    
    /**
     * Funkcia rest rozhrania. Vymaže objekt Data z databázy.
     * Prístup k funkcii má ADMIN. 
     * Funkcia je typu DELETE na URL adrese http://localhost:8080/data/
     * @param id id dát v databáze, ktoré majú byť vymazané
     * @return vymazanú triedu "Data" vo formáte JSON
     */    
    @DELETE
    @RolesAllowed("ADMIN")
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Data deleteData2(@PathParam("id") LongParam id) {
        Optional<Data> result = dataDAO.findById(id.get());
        if (result.isPresent()) {
            dataDAO.delete(result.get());
            return result.get();
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     * Funkcia rest rozhrania. Zmení parametre objektu Dáta v databáze.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data/edit/
     * @param id id dát v databáze, ktoré majú byť zmenené
     * @return DataAddEditView 
     */      
    @GET
    @Path("/edit/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public DataAddEditView getEditForm(@PathParam("id") LongParam id) {
        Optional<Data> result = dataDAO.findById(id.get());

        if (result.isPresent()) {
            return new DataAddEditView(result.get());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     * Funkcia rest rozhrania. Zmení parametre objektu Dáta v databáze.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/data/edit
     * @param _id id dát v databáze, ktoré majú byť zmenené
     * @param hodnota nová hodnota dát v databáze
     * @return DataView stránka v prehliadači pre zobrazenie informácií o objekte Data
     */      
    @POST
    @Path("/edit")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @UnitOfWork
    public DataView editData(@FormParam("id") String _id, @FormParam("hodnota") Float hodnota) {
        Optional<Data> result = dataDAO.findById(Long.parseLong(_id));
        if (result.isPresent()) {
            result.get().setHodnota(hodnota);
            return new DataView(result.get());
        } else {
            Data create = dataDAO.create(new Data(hodnota,new Long(0)));
            return new DataView(create);
        }
    }
    
    /**
     * Funkcia rest rozhrania. Zobrazí objekt Data na stránke v prehliadači.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data/
     * @param id id dát v databáze, ktoré majú byť zobrazené
     * @return DataView 
     */      
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public DataView getData(@PathParam("id") LongParam id) {
        Optional<Data> result = dataDAO.findById(id.get());

        if (result.isPresent()) {
            return new DataView(result.get());
        }

        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     * Funkcia rest rozhrania. Vráti list objektov vo formáte JSON.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data
     * @return list objektov Data 
     */       
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public List<Data> listDatas() {
        return dataDAO.findAll();
    }
    
    /**
     * Funkcia rest rozhrania. Vymaže objekt Data z databázy 
     * a zvyšné objekty zobrazí na stránke v prehliadači.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu GET na URL adrese http://localhost:8080/data/delete/
     * @param id id objektu Data v databáze, ktorý má byť vymazaný 
     * @return DataListView  
     */      
    @GET
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_HTML)
    @UnitOfWork
    public DataListView deleteData(@PathParam("id") LongParam id) {
        Optional<Data> result = dataDAO.findById(id.get());
        if (result.isPresent()) {
            dataDAO.delete(result.get());
            return new DataListView(dataDAO.findAll());
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }
    
    /**
     * Funkcia rest rozhrania. Uloží objekt Data do databázy 
     * a vráti ho vo formáte JSON.
     * Prístup k funkcii majú všetci. 
     * Funkcia je typu POST na URL adrese http://localhost:8080/data
     * @param data objekt, ktorý má byť uložený
     * @return Data objekt vo formáte JSON
     */      
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @UnitOfWork
    public Data createData(Data data) {
        return dataDAO.create(data);
    }
}
