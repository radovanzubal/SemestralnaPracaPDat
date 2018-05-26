package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;
/**
 * Trieda pre definovanie Create  Read Update a Delete operácií
 * pre objekt Data v databáze 
 * @author Zubaľ,Šibíková
 */
public class DataDAO extends AbstractDAO<Data> {

    /**
    * Konštruktor triedy DataDAO, ktorá slúži pre definovanie metód pre komunikáciu s databázou.
    * Rodičovi predáva parameter "sessionFactory"
    *@param sessionFactory parameter predaný rodičovy
    */
    public DataDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    /**
    *Vymaže inštanciu predanú ako parameter tejto metódy z databázy
    *@param  dat inštancia triedy ktorá má byť vymazaná z databázy 
    */
    public void delete(Data dat){
        currentSession().delete(dat);
    }

    /**
    *V databáze vyhľadá podľa parametru "id" objekt typu "Data", ktorý táto metóda vráti vo forme Optional
    *@param id id dát v databáze 
    * @return objekt Data ako Optional
    */
    public Optional<Data> findById(Long id) {
        if (id != null) {
            return Optional.ofNullable(get(id));
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Funkcia pre uloženie objektu typu Data do databázy
     * @param dat trieda typu Data, ktorá má byť uložená do databázy 
     * @return Data vráti uloženú triedu s "id", ktoré mu bolo pridelené
     */
    public Data create(Data dat) {
        return persist(dat);
    }

    /**
     *Funkcia nájde v databáze a vráti všetky objekty typu Data
     * použité query sa nachádza v triede Data
     * @return List všetkých nájdených objektov typu Data 
     */
    public List<Data> findAll() {
        return list(namedQuery("sk.fri.uniza.microservice.Data.findAll"));
    }

}