# Jakarta EE 8 Implementation utilizing Open Liberty

This Chapter contains a complete DDD implementation of the Cargo Tracker application based on the Jakarta EE Platform utilizing 
the OpenLiberty (v19.0.0.9) implementation

The implementation adopts a modulithic based architectural style and uses the following technologies
  - Jakarta EE v8.0 (WebProfile) as the core chassis
  - CDI Events as the messaging infrastructure which enables loose coupling between the various modules
  - MySql as the underlying Database
  - Single WAR file which contains all the modules
  - OpenLiberty v19.0.0.9 as the runtime which has support for Jakarta EE 8.0
  
The documentation covers the setup and testing process needed to run the cargotracker monolith correctly. 

# Test Case

The test case is as follows

- A Cargo is booked to be delivered from Hong Kong to New York with the delivery deadline of 28 September 2019
- Based on the specifications the Cargo is routed accordingly by assigning an itinierary
- The Cargo is handled at the various ports of the itinerary and is finally claimed by the customer
- The customer can track the cargo at any point of time with a unique Tracking Number


# Modules

Booking Module

    This module takes care of all the operations associated with the booking of the Cargo. 
    
Routing Module

    This module takes care of all the operations associated with the routing of the Cargo. 

Tracking Module

    This module takes care of all the operations associated with the tracking of the Cargo. 

Handling Module

    This module takes care of all the operations associated with the handling of the Cargo. 

# Database Details / DDL / DML

    Database Name -> cargotracker (user: cargotracker / pw: cargotracker)
    Tables ->
    
    ##Cargo Table DDL
	CREATE TABLE `cargo` (
	  `ID` int(11) NOT NULL AUTO_INCREMENT,
	  `BOOKING_ID` varchar(20) NOT NULL,
	  `TRANSPORT_STATUS` varchar(100) NOT NULL,
	  `ROUTING_STATUS` varchar(100) NOT NULL,
	  `spec_origin_id` varchar(20) DEFAULT NULL,
	  `spec_destination_id` varchar(20) DEFAULT NULL,
	  `SPEC_ARRIVAL_DEADLINE` date DEFAULT NULL,
	  `origin_id` varchar(20) DEFAULT NULL,
	  `BOOKING_AMOUNT` int(11) NOT NULL,
	  `handling_event_id` int(11) DEFAULT NULL,
	  `next_expected_location_id` varchar(20) DEFAULT NULL,
	  `next_expected_handling_event_type` varchar(20) DEFAULT NULL,
	  `next_expected_voyage_id` varchar(20) DEFAULT NULL,
	  `last_known_location_id` varchar(20) DEFAULT NULL,
	  `current_voyage_number` varchar(100) DEFAULT NULL,
	  `last_handling_event_id` int(11) DEFAULT NULL,
	  `last_handling_event_type` varchar(20) DEFAULT NULL,
	  `last_handling_event_location` varchar(20) DEFAULT NULL,
	  `last_handling_event_voyage` varchar(20) DEFAULT NULL,
	  PRIMARY KEY (`ID`)
	) ENGINE=InnoDB AUTO_INCREMENT=2923 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    
    ##Leg Table DDL
    	CREATE TABLE `LEG` (
	  `ID` int(11) NOT NULL AUTO_INCREMENT,
	  `LOAD_TIME` timestamp NULL DEFAULT NULL,
	  `UNLOAD_TIME` timestamp NULL DEFAULT NULL,
	  `load_location_id` varchar(20) DEFAULT NULL,
	  `unload_location_id` varchar(20) DEFAULT NULL,
	  `voyage_number` varchar(100) DEFAULT NULL,
	  `CARGO_ID` int(11) DEFAULT NULL,
	  PRIMARY KEY (`ID`)
	) ENGINE=InnoDB AUTO_INCREMENT=3095 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    ##Location Table DDL
    CREATE TABLE `location` (
  	`ID` int(11) DEFAULT NULL,
  	`NAME` varchar(50) DEFAULT NULL,
  	`UNLOCODE` varchar(100) DEFAULT NULL
	) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

    ##Voyage Table DDL
    CREATE TABLE `voyage` (
  	`Id` int(11) NOT NULL AUTO_INCREMENT,
  	`voyage_number` varchar(20) NOT NULL,
  	PRIMARY KEY (`Id`)
	) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    ##Carrier Movement Table DDL -
    CREATE TABLE `carrier_movement` (
	  `Id` int(11) NOT NULL AUTO_INCREMENT,
	  `arrival_location_id` varchar(100) DEFAULT NULL,
	  `departure_location_id` varchar(100) DEFAULT NULL,
	  `voyage_id` int(11) DEFAULT NULL,
	  `arrival_date` date DEFAULT NULL,
	  `departure_date` date DEFAULT NULL,
	  PRIMARY KEY (`Id`)
	) ENGINE=InnoDB AUTO_INCREMENT=1358 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    ### Data to ensure Routing works fine ->
    	insert voyage (Id,voyage_number) values(3,'0100S');
    insert voyage (Id,voyage_number) values(4,'0101S');
    insert voyage (Id,voyage_number) values(5,'0102S');

    insert into carrier_movement (Id,arrival_location_id,departure_location_id,voyage_id,arrival_date,departure_date) 		values (1355,'CNHGH','CNHKG',3,'2019-08-28','2019-08-25');
    insert into carrier_movement (Id,arrival_location_id,departure_location_id,voyage_id,arrival_date,departure_date) 		values (1356,'JNTKO','CNHGH',4,'2019-09-10','2019-09-01');
    insert into carrier_movement (Id,arrival_location_id,departure_location_id,voyage_id,arrival_date,departure_date) 		values (1357,'USNYC','JNTKO',5,'2019-09-25','2019-09-15');  
   
    ##Tracking_activity DDL
   	 CREATE TABLE `tracking_activity` (
	  `Id` int(11) NOT NULL AUTO_INCREMENT,
	  `tracking_number` varchar(20) NOT NULL,
	  `booking_id` varchar(20) DEFAULT NULL,
	  PRIMARY KEY (`Id`)
	) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    ##Tracking_handling_events DDL
	  CREATE TABLE `tracking_handling_events` (
	  `Id` int(11) NOT NULL AUTO_INCREMENT,
	  `tracking_id` int(11) DEFAULT NULL,
	  `event_type` varchar(225) DEFAULT NULL,
	  `event_time` timestamp NULL DEFAULT NULL,
	  `location_id` varchar(100) DEFAULT NULL,
	  `voyage_number` varchar(20) DEFAULT NULL,
	  PRIMARY KEY (`Id`)
	) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
  ##Handling_activity DDL
	  CREATE TABLE `handling_activity` (
	  `id` int(11) NOT NULL AUTO_INCREMENT,
	  `event_completion_time` timestamp NULL DEFAULT NULL,
	  `event_type` varchar(225) DEFAULT NULL,
	  `booking_id` varchar(20) DEFAULT NULL,
	  `voyage_number` varchar(100) DEFAULT NULL,
	  `location` varchar(100) DEFAULT NULL,
	  PRIMARY KEY (`id`)
	) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
   
  # Running the application
  
    Execute maven command mvn install . This command builds the cargotracker application and creates a .war file in the target directory. 
    It also configures and installs Open Liberty into the target/liberty/wlp directory.
    
    Execute maven command mvn liberty:start server. This goal starts an Open Liberty server instance. 
    The  pom.xml is already configured to start the application in this server instance.
   
  # JSON Requests (Test via Postman) ->
    
    
    Cargo Booking (http://localhost:9080/cargotracker/serviceapi/cargobooking)
    --------------------------------------------------------------------------

    {
        "bookingAmount": 100,
        "originLocation": "CNHKG",
        "destLocation" : "USNYC",
        "destArrivalDeadline" : "2019-09-28"
    }
    
    This returns a unique "Booking Id" which should be put into all requests with the placeholder <<BookingId>>
    
    Cargo Routing (http://localhost:8080/cargotracker/serviceapi/cargorouting)
    --------------------------------------------------------------------------
    {
      "bookingId": "<<BookingId>>"
    }

 
    Cargo Handling (http://localhost:9080/cargotracker/serviceapi/cargohandling)
    -----------------------------------------------------------------------------
    Run in Sequence
    
    Recieved at port
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "CNHKG",
	    "handlingType" : "RECEIVE",
	    "completionTime": "2019-08-23",
	    "voyageNumber" : ""
    }
    
    Loaded onto carrier
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "CNHKG",
	    "handlingType" : "LOAD",
	    "completionTime": "2019-08-25",
	    "voyageNumber" : "0100S"
    }
    
    Unloaded
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "CNHGH",
	    "handlingType" : "UNLOAD",
	    "completionTime": "2019-08-28",
	    "voyageNumber" : "0100S"
    }
    
    Loaded onto next carrier
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "CNHGH",
	    "handlingType" : "LOAD",
	    "completionTime": "2019-09-01",
	    "voyageNumber" : "0101S"
    }
    
    Unloaded
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "JNTKO",
	    "handlingType" : "UNLOAD",
	    "completionTime": "2019-09-10",
	    "voyageNumber" : "0101S"
    }
    
    Loaded onto next carrier
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "JNTKO",
	    "handlingType" : "LOAD",
	    "completionTime": "2019-09-15",
	    "voyageNumber" : "0102S"
    }
    
    Unloaded
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "USNYC",
	    "handlingType" : "UNLOAD",
	    "completionTime": "2019-09-25",
	    "voyageNumber" : "0102S"
    }
    
    Customs
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "USNYC",
	    "handlingType" : "CUSTOMS",
	    "completionTime": "2019-09-26",
	    "voyageNumber" : ""
    }
    
    Claimed
    {
	    "bookingId" : "<<BookingId>>",
	    "unLocode" : "USNYC",
	    "handlingType" : "CLAIM",
	    "completionTime": "2019-09-28",
	    "voyageNumber" : ""
    }
