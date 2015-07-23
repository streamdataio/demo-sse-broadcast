package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * Streamdata.io - http://streamdata.io
 * Created by ctranxuan on 22/07/15.
 */
@SpringBootApplication
public class DemoSseBroadcastApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DemoSseBroadcastApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoSseBroadcastApplication.class, args);
    }
}
