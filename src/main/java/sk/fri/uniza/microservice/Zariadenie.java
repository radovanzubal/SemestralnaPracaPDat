package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Length;
/**
 * Trieda definuje triedu Zariadenie (senzor) tak ako bude ukladaný do databázy.
 * @author Zubaľ,Šibíková
 */
@Entity
@Table(name = "Zariadenie")
@NamedQueries({
    @NamedQuery(
            name = "sk.fri.uniza.microservice.Zariadenie.findAll",
            query = "SELECT s from Zariadenie s"
    )
})
public class Zariadenie {

    @Id
    @GeneratedValue
    private long id;
     
    @Length(max = 20)
    private String content;//typ senzoru

    /**
     * Prázdny konštruktor tejto triedy
     */
    public Zariadenie() {        
    }

    /**
     * Konštruktor tejto triedy inicializuje premennú content(predstavuje typ senzoru)
     * @param content nová hodnota premennej "content"
     */
    public Zariadenie(String content) {
        this.content = content;
    }

    /**
     * Konštruktor tejto triedy inicializuje premennú content(predstavuje typ senzoru) a premennú "id"
     * @param content nová hodnota premennej "content"
     * @param id nová hodnota premennej "id"
     */
    public Zariadenie(long id, String content) {
        this.id = id;
        this.content = content;
    }

   
    /**
     * Getter pre premennú "id"
     * @return id
     */
    @JsonProperty
    public long getId() {
        return id;
    }

    /**
     * Setter pre premennú "id"
     * @param id nová hodnota premennej
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Setter pre premennú "content"
     * @param content nová hodnota premennej
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter pre premennú "content"
     * @return content
     */
    @JsonProperty
    public String getContent() {
        return content;
    }

    /**
     * Vytvorí hash kód
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.content);
        return hash;
    }

    /**
     * Prepísanie metódy "equals" používanej pri zisťovaní rovnosti dvoch tried 
     * @param obj
     * @return true, ak sú rovnaké
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Zariadenie other = (Zariadenie) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        return true;
    }
}
