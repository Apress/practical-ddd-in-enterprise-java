package com.practicalddd.cargotracker.bookingms.application.internal.outboundservices.acl;

import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.CargoItinerary;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.Leg;
import com.practicalddd.cargotracker.bookingms.domain.model.valueobjects.RouteSpecification;
import com.practicalddd.cargotracker.shareddomain.TransitEdge;
import com.practicalddd.cargotracker.shareddomain.TransitPath;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Anti Corruption Service Class
 */

@Service
public class ExternalCargoRoutingService {

    /**
     * The Booking Bounded Context makes an external call to the Routing Service of the Routing Bounded Context to
     * fetch the Optimal Itinerary for a Cargo based on the Route Specification
     * @param routeSpecification
     * @return
     */
    public CargoItinerary fetchRouteForSpecification(RouteSpecification routeSpecification){

        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> params = new HashMap<>();
        params.put("origin",routeSpecification.getOrigin().getUnLocCode());
        params.put("destination",routeSpecification.getDestination().getUnLocCode());
        params.put("arrivalDeadline",routeSpecification.getArrivalDeadline().toString());

        TransitPath transitPath = restTemplate.getForObject("<<ROUTING_SERVICE_URL>>/cargorouting/",
                    TransitPath.class,params);


        List<Leg> legs = new ArrayList<>(transitPath.getTransitEdges().size());
        for (TransitEdge edge : transitPath.getTransitEdges()) {
            legs.add(toLeg(edge));
        }

        return new CargoItinerary(legs);

    }

    /**
     * Anti-corruption layer conversion method from the routing service's domain model (TransitEdges)
     * to the domain model recognized by the Booking Bounded Context (Legs)
     * @param edge
     * @return
     */
    private Leg toLeg(TransitEdge edge) {
        return new Leg(
                edge.getVoyageNumber(),
                edge.getFromUnLocode(),
                edge.getToUnLocode(),
                edge.getFromDate(),
                edge.getToDate());
        }
}
