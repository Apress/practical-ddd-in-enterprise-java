package com.practicalddd.cargotracker.handlingms.domain.model;

public enum HandlingEventType {


    /* Loaded onto voyage from port location.*/
    LOAD,
    // Unloaded from voyage to port location
    UNLOAD,
    // Received by carrier
    RECEIVE,
    // Cargo claimed by recepient
    CLAIM,
    // Cargo went through customs
    CUSTOMS;
}
