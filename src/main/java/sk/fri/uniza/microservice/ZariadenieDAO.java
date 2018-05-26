package sk.fri.uniza.microservice;

import io.dropwizard.hibernate.AbstractDAO;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import org.hibernate.SessionFactory;

/**
 * Trieda definuje Create Read Update a Deleteoperácie pre prácu s objektom typu Zariadenie v databáze.
 * @author Zubaľ,Šibíková
 */
public class ZariadenieDAO extends AbstractDAO<Zariadenie> {

    /**
     * Konštruktor tejto triedy predá objekt typu SessionFactory rodičovi
     * @param sessionFactory pre rodiča
     */
    public ZariadenieDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
        
    }
    
    /**
     * Vymaže objekt daný parametrom z databázy
     * @param zariadenie objekt, ktorý má byť vymazaný
     */
    public void delete(Zariadenie zariadenie){
        List<Data> listZariadeni = currentSession().createQuery("FROM Data AS d where d.idZariadenia = :id").setParameter("id", zariadenie.getId()).getResultList();
        if(listZariadeni.size()>0){   
        for(int i=0;i<=listZariadeni.size()-1;i++){
        currentSession().delete(listZariadeni.get(i));
        }
        }
        currentSession().delete(zariadenie);
    }

    /**
     * Vyhľadá v databáze objekt typu Zariadenie určený parametrom "id"
     * @param id objektu, ktorý má byť nájdený
     * @return Zariadenie ako Optional
     */
    public Optional<Zariadenie> findById(Long id) {
        if (id != null) {
            return Optional.ofNullable(get(id));
        }
        throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Uloží objekt Zariadenie do databázy
     * @param zariadenie objekt, ktorý má byť uložený
     * @return Zariadenie
     */
    public Zariadenie create(Zariadenie zariadenie) {
        return persist(zariadenie);
    }

    /**
     * Vyhľadá všetky objekty typu Zariadenie v databáze
     * Query je umiestnené v triede Zariadenie
     * @return vráti list všetkých objektov typu zariadenie
     */
    public List<Zariadenie> findAll() {
        return list(namedQuery("sk.fri.uniza.microservice.Zariadenie.findAll"));
    }

}
