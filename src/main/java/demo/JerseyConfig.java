package demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

/**
 * Created by ctranxuan on 22/07/15.
 */
@Component
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(SseResource.class);
        register(CorsResponseFilter.class);

    }
}
