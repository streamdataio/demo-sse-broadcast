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

    private SseBroadcaster broadcaster = new SseBroadcaster();

    public SseResource() {
        broadcast();
    }

    @GET
    @Produces(SERVER_SENT_EVENTS)
    public EventOutput listenToBroadcast() {
        final EventOutput eventOutput = new EventOutput();
        broadcaster.add(eventOutput);
        return eventOutput;
    }

    private void broadcast() {
        new Thread(() -> {
            while (true) {
                OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                OutboundEvent event = eventBuilder.name("special")
                        .mediaType(MediaType.TEXT_PLAIN_TYPE)
                        .id(UUID.randomUUID().toString())
                        .data(String.class, "special user: *" + new Date().toString() + "*")
                        .build();
                broadcaster.broadcast(event);

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
                    OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                    OutboundEvent event = eventBuilder.name("standard")
                            .mediaType(MediaType.TEXT_PLAIN_TYPE)
                            .id(UUID.randomUUID().toString())
                            .data(String.class, "standard user: hello world! Your dice is " + random.nextInt(23))
                            .build();
                    broadcaster.broadcast(event);

                    try {
                        Thread.sleep(TimeUnit.SECONDS.toMillis(5));

                    } catch (InterruptedException ignore) {

                    }
                }
            }


        }).start();

    }
}
