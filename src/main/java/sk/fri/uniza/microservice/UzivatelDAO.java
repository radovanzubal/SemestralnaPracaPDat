package sk.fri.uniza.microservice;


import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;
/**
 * Trieda definuje základné Create Read Update a Delete funkcie pre prácu s objektom Uzivatel v databáze
 * @author Zubaľ,Šibíková
 */
public class UzivatelDAO extends AbstractDAO<Uzivatel> {

    /**
     * Konštruktor tejto triedy. Rodičovi predá objekt typu SessionFactory. 
     * Táto trieda definuje základné CRUD operácie pre objekt Uzivatel v databáze.
     * @param sessionFactory objekt predaný rodičovi.
     */
    public UzivatelDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    
    /**
     * Funkcia, ktorá vymaže objekt Uzivatel z databázy
     * @param uzivatel objekt, ktorý má byť vymazaný
     */
    public void delete(Uzivatel uzivatel){
        currentSession().delete(uzivatel);
    }

    /**
     * Funkcia, ktorá podľa "id" v databáze nájde objekt typu Uzivatel a vráti ho vo formáte Optional 
     * @param id užívateľa v databáze
     * @return Optional Uzivatel
     */
    public Optional<Uzivatel> findById(Long id) {
        if (id != null) {
            return Optional.ofNullable(get(id));
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Uloží objekt typu Uzivatel do databázy
     * @param uzivatel objekt, ktorý má byť uložený
     * @return Uzivatel
     */
    public Uzivatel create(Uzivatel uzivatel) {
        return persist(uzivatel);
    }

    /**
     * V databáze nájde všetky objekty typu Uzivatel, ktoré funkcia vráti v liste. 
     * Query pre komunikáciu s databázou je zadané v triede Uzivatel
     * @return List objektov typu Uzivatel 
     */
    public List<Uzivatel> findAll() {
        return list(namedQuery("sk.fri.uniza.microservice.Uzivatel.findAll"));
    }
}

