package ir.asta.warehouseone.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig(){
        packages("ir.asta.warehouseone.service");

        register(Endpoint.class);
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
    }
}



