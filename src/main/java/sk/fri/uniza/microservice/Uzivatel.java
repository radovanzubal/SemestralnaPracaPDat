package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 * Táto trieda je definovaná kvôli ukladaniu a načítavaniu údajov o používateľovi z databázy
 * @author Zubaľ,Šibíková
 */
@Entity
@Table(name = "Uzivatel")
@NamedQueries({
    @NamedQuery(
            name = "sk.fri.uniza.microservice.Uzivatel.findAll",
            query = "SELECT s from Uzivatel s"
    )
})
public class Uzivatel {
    /**
     * Prázdny konštruktor
     */
    public Uzivatel() {  
    }
    
    /**
     * Konštruktor tejto triedy. Inicializuje globálne premenné "meno", "heslo" a "pravomoc" 
     * @param meno nová hodnota premennej
     * @param heslo nová hodnota premennej
     * @param pravomoc nová hodnota premennej
     */
    public Uzivatel(String meno,String heslo,String pravomoc) {
       this.meno=meno;
       this.heslo=heslo;
       this.pravomoc=pravomoc;
    }

    @Id
    @GeneratedValue
    private Long id; //id riadku 

    @Column(name = "meno")
    private String meno;

    @Column(name = "heslo")
    private String heslo;
    
    @Column(name = "pravomoc")
    private String pravomoc;    

    /**
     * Getter pre premennú "pravomoc"
     * @return pravomoc
     */
    @JsonProperty
    public String getPravomoc() {
        return pravomoc;
    }
    
     /**
      *Setter pre premennú "pravomoc" 
      * @param pravomoc nová hodnota premennej
      */
    public void setPravomoc(String pravomoc){
    this.pravomoc=pravomoc;
    }
    
    /**
     * Getter pre premennú "id"
     * @return id
     */
    @JsonProperty
    public Long getId() {
        return id;
    }
    
    /**
     * Setter pre premennú "id"
     * @param id nová hodnota premennej
     */
    public void setId(long id){
    this.id=id;
    }
    
    /**
     * Getter pre premennú "meno"
     * @return meno
     */
    @JsonProperty
    public String getMeno() {
        return meno;
    }

    /**
     * Setter pre premennú "meno"
     * @param meno nová hodnota premennej
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }
    
    /**
     * Setter pre premennú "heslo"
     * @param heslo nová hodnota premennej
     */
    public void setHeslo(String heslo){
    this.heslo=heslo;
    }
    
    /**
     * Getter pre premennú "heslo"
     * @return heslo
     */
    @JsonProperty
    public String getHeslo(){
    return heslo;
    }
}