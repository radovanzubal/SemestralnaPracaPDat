package sk.fri.uniza.microservice;

import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import io.dropwizard.db.DataSourceFactory;
import java.util.Collections;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * Trieda definujúca konfiguráciu databázy.
 * @author Zubaľ,Šibíková
 */
public class HelloWorldConfiguration extends Configuration {

    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private Map<String, Map<String, String>> viewRendererConfiguration = Collections.emptyMap();

    /**
     * Getter pre premennú "database"
     * @return database
     */
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    /**
     * Setter pre premennú "database"
     * @param dataSourceFactory nová hodnota premennej
     */
    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    /**
     * Getter pre premennú "template"
     * @return template 
     */
    @JsonProperty
    public String getTemplate() {
        return template;
    }

    /**
     * Setter pre premennú "template"
     * @param template nová hodnota premennej
     */
    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Getter pre premennú "defaultName"
     * @return defaultName 
     */
    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * Setter pre premennú defaultName
     * @param name nová hodnota premennej 
     */
    @JsonProperty
    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    /**
     * Getter pre premennú "viewRendererConfiguration"
     * @return viewRendererConfiguration
     */
    @JsonProperty("viewRendererConfiguration")
    public Map<String, Map<String, String>> getViewRendererConfiguration() {
        return viewRendererConfiguration;
    }

    /**
     * Setter pre premennú "viewRendererConfiguration"
     * @param viewRendererConfiguration nová hodnota premennej 
     */
    @JsonProperty("viewRendererConfiguration")
    public void setViewRendererConfiguration(Map<String, Map<String, String>> viewRendererConfiguration) {
        final ImmutableMap.Builder<String, Map<String, String>> builder = ImmutableMap.builder();
        for (Map.Entry<String, Map<String, String>> entry : viewRendererConfiguration.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
        }
        this.viewRendererConfiguration = builder.build();
    }
}
