package demo;

import org.glassfish.jersey.media.sse.EventOutput;
import static org.glassfish.jersey.media.sse.SseFeature.*;

import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseBroadcaster;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Streamdata.io - http://streamdata.io
 * Created by ctranxuan on 22/07/15.
 */
@Component
@Path("/sse")
public class SseResource {

    private static final String SPECIAL_USER_TYPE = "special";
    private static final String STANDARD_USER_TYPE = "standard";

    private Map<String, SseBroadcaster> broadcasters = new HashMap<>();

    public SseResource() {
        broadcasters.put(SPECIAL_USER_TYPE, new SseBroadcaster());
        broadcasters.put(STANDARD_USER_TYPE, new SseBroadcaster());

        broadcast();
    }

    @GET
    @Produces(SERVER_SENT_EVENTS)
    public EventOutput listenToBroadcast(@QueryParam("type") final String type) {
        final EventOutput eventOutput = new EventOutput();

        if (SPECIAL_USER_TYPE.equals(type) || STANDARD_USER_TYPE.equals(type)) {
            broadcasters.get(type).add(eventOutput);

        } else {
            throw new RuntimeException("Unknown type: " + type);

        }

        return eventOutput;
    }

    private void broadcast() {
        new Thread(() -> {
            while (true) {
                SseBroadcaster specialUsersBroadcaster = broadcasters.get(SPECIAL_USER_TYPE);

                OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                OutboundEvent event = eventBuilder.name("message")
                        .mediaType(MediaType.TEXT_PLAIN_TYPE)
                        .id(UUID.randomUUID().toString())
                        .data(String.class, "special user: *" + new Date().toString() + "*")
                        .build();
                specialUsersBroadcaster.broadcast(event);

                try {
                    Thread.sleep(TimeUnit.SECONDS.toMillis(5));

                } catch (InterruptedException ignore) {

                }
            }

        }).start();

        new Thread(new Runnable() {
            private Random random = new Random();

            public void run() {
                while (true) {
                    SseBroadcaster allUsersBroadcaster = broadcasters.get(STANDARD_USER_TYPE);

                    OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                    OutboundEvent event = eventBuilder.name("message")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .id(UUID.randomUUID().toString())
                            .data(String.class, "standard user: hello world! Your dice is " + random.nextInt(23))
                            .build();
                    allUsersBroadcaster.broadcast(event);

                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

                    } catch (InterruptedException ignore) {

                    }
                }
            }


        }).start();

    }
}
