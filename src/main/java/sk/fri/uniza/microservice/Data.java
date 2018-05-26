package sk.fri.uniza.microservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
/**
 * Definuje dáta a jeho základné funkcie a premenné tak ako budú uložené v databáze
 * @author Zubaľ,Šibíková
 */
@Entity
@Table(name = "Data")
@NamedQueries({
    @NamedQuery(
            name = "sk.fri.uniza.microservice.Data.findAll",
            query = "SELECT s from Data s"
    )
})
public class Data {

    /**
    * Konštruktor triedy Data. inicializuje premennu dateOfStart.
    */
    public Data() {
        Calendar cal=Calendar.getInstance();
        dateOfStart=  "Y:"+cal.get(Calendar.YEAR)+" M:"+(cal.get(Calendar.MONTH)+1)+" D:"+cal.get(Calendar.DAY_OF_MONTH)+
       " H:"+cal.get(Calendar.HOUR_OF_DAY)+" m:"+cal.get(Calendar.MINUTE)+" s:"+cal.get(Calendar.SECOND)+
        " ms:"+cal.get(Calendar.MILLISECOND);
    }
    
    /**
    * Konštruktor triedy Data. inicializuje premenne hodnota, idZariadenia a dateOfStart.
    * @param  hodnota  hodnota dát typu Float.
    * @param  idZariadenia id zariadenia, ktoré túto hodnotu nameralo typ Long.
    */
    public Data(Float hodnota,Long idZariadenia) {
        this.hodnota = hodnota;
        Calendar cal=Calendar.getInstance();
        dateOfStart=  "Y:"+cal.get(Calendar.YEAR)+" M:"+(cal.get(Calendar.MONTH)+1)+" D:"+cal.get(Calendar.DAY_OF_MONTH)+
        " H:"+cal.get(Calendar.HOUR_OF_DAY)+" m:"+cal.get(Calendar.MINUTE)+" s:"+cal.get(Calendar.SECOND)+
        " ms:"+cal.get(Calendar.MILLISECOND);      
        this.idZariadenia=idZariadenia;
    }

    @Id
    @GeneratedValue
    private Long id; //id riadku v databáze

    @Column(name = "hodnota")
    private Float hodnota;//hodnota, ktorú zariadenie nameralo

    @Column(name = "owner_id")
    private Long idZariadenia;//id zariadenia, ktoré nameralo hodnotu
    
    @Column(name = "dateOfStart")
    String dateOfStart;
    
   /**
   * getter pre premennú "id"
   * @return      id 
   */
    @JsonProperty
    public Long getId() {
        return id;
    }
  
   /**
   * setter pre premennú "id"
   * @param  id  nová hodnota premennej "id"
   */    
    public void setId(long id){
    this.id=id;
    }
 
   /**
   * getter pre premennú "hodnota"
   * @return    hodnota 
   */
    @JsonProperty
    public String getHodnota() {
        return hodnota.toString().replace(",",".");
    }
    
   /**
   * setter pre premennú "hodnota"
   * @param  hodnota  nová hodnota premennej "hodnota"
   */  
    public void setHodnota(Float hodnota) {
        this.hodnota = hodnota;
    }
    
   /**
   * setter pre premennú "idZariadenia"
   * @param      idZariadenia nová hodnota premennej "idZariadenia" 
   */
    public void setIdZariadenia(Long idZariadenia){
    this.idZariadenia=idZariadenia;
    }
    
   /**
   * getter pre premennú "idZariadenia"
   * @return      idZariadenia
   */
    @JsonProperty
    public Long getIdZariadenia(){
    return idZariadenia;
    }

   /**
   * getter pre premennú "dateOfStart"
   * @return      dateOfStart 
   */    
    @JsonProperty
    public String getDateOfStart() {
        return dateOfStart;
    }

   /**
   * setter pre premennú "dateOfStart"
   * @param    dateOfStart  nová hodnota premennej "dateOfStart" 
   */    
    public void setDateOfStart(String dateOfStart) {
        this.dateOfStart = dateOfStart;
    }
    
}

