package sk.fri.uniza.microservice;

import com.codahale.metrics.health.HealthCheck;
/**
 * Trieda testuje funkčnosť aplikácie.
 * @author Zubaľ,Šibíková
 */
public class TemplateHealthCheck extends HealthCheck {

    private final String template;
   
    /**
    * Konštruktor triedy. Inicializuje hodnotu premennej "template"   
    * @param template nová hodnota premennej "template"
    */
    public TemplateHealthCheck(String template) {
        this.template = template;
    }

    /**
     * Kontrouje premennú "template" či obsahuje text "TEST"
     * @return Result.healthy()
     * @throws Exception v prípade chyby
     */
    
    @Override
    protected Result check() throws Exception {
        final String saying = String.format(template, "TEST");
        if (!saying.contains("TEST")) {
            return Result.unhealthy("template doesn't include a name");
        }
        return Result.healthy();
    }
}
