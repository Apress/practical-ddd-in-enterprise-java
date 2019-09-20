package com.practicalddd.cargotracker.bookingms.infrastructure.services.http;

import javax.ws.rs.*;
import com.practicalddd.cargotracker.shareddomain.model.TransitPath;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Type safe Rest client for the Routing Service API
 */

@RegisterRestClient(baseUri="http://localhost:8081/cargoRouting") //Annotation to register this as a Rest client
public interface ExternalCargoRoutingClient {
    //The method signature / method resource details should be exactly as the calling service
    @GET
    @Path("/optimalRoute")
    @Produces({"application/json"})
    public TransitPath findOptimalRoute(
            @QueryParam("origin") String originUnLocode,
            @QueryParam("destination") String destinationUnLocode,
            @QueryParam("deadline") String deadline);
}
