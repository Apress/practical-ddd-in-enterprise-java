package com.practicalddd.cargotracker.booking.infrastructure.services.http;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import com.practicalddd.cargotracker.shareddomain.model.TransitPath;

/**
 * Type safe Rest client for the Routing Service API
 */

@ApplicationScoped
public class ExternalCargoRoutingClient {

    public TransitPath findOptimalRoute(String origin, String destination, String arrivalDeadline){
        final String REST_URI
                = "http://localhost:9080/cargotracker/serviceapi/voyageRouting/optimalRoute";

        Client client = ClientBuilder.newClient();

        return client
                .target(REST_URI)
                .queryParam("origin",origin)
                .queryParam("destination",destination)
                .queryParam("deadline",arrivalDeadline)
                .request(MediaType.APPLICATION_JSON)
                .get(TransitPath.class);

    }

}
