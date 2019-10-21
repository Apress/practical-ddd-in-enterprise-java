package com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.assembler;

import com.practicalddd.cargotracker.bookingms.domain.commands.BookCargoCommand;
import com.practicalddd.cargotracker.bookingms.interfaces.rest.transform.dto.BookCargoResource;

/**
 * Assembler class to convert the Book Cargo Resource Data to the Book Cargo Model
 */
public class BookCargoCommandDTOAssembler {

    /**
     * Static method within the Assembler class
     * @param bookCargoResource
     * @return BookCargoCommand Model
     */
    public static BookCargoCommand toCommandFromDTO(BookCargoResource bookCargoResource){

        return new BookCargoCommand(bookCargoResource.getBookingId(),
                                    bookCargoResource.getBookingAmount(),
                                    bookCargoResource.getOriginLocation(),
                                    bookCargoResource.getDestLocation(),
                                    bookCargoResource.getDestArrivalDeadline());
    }
}
