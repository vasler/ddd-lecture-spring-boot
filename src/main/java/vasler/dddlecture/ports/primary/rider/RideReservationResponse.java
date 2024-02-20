package vasler.dddlecture.ports.primary.rider;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RideReservationResponse
{
    public enum OutcomeCause
    {
        RIDE_RESERVATION_SUCCESSFUL("Ride reservation successfully."),
        RIDE_RESERVATION_UNSUCCESSFUL("Ride reservation failed."),
        RIDE_ALREADY_RESERVED("Ride already reserved."),
        RIDE_ALREADY_RESERVED_FOR_TIME_PERIOD("Ride already reserved for time period."),
        NOT_ENOUGH_SEATS_AVAILABLE("Not enough seats available."),
        TRIP_NOT_FOUND("Trip not found."),
        RIDER_NOT_FOUND("Rider not found."),
        INTERNAL_ERROR("Internal error.");

        private final String humanReadableMessage;
        OutcomeCause(String humanReadableMessage)
        {
            this.humanReadableMessage = humanReadableMessage;
        }
    }

    private final boolean success;
    private final OutcomeCause outcomeCause;

    public static RideReservationResponse ofSuccess()
    {
        return RideReservationResponse.builder()
                                      .success(true)
                                      .outcomeCause(OutcomeCause.RIDE_RESERVATION_SUCCESSFUL)
                                      .build();
    }

    public static RideReservationResponse ofInternalError()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(RideReservationResponse.OutcomeCause.INTERNAL_ERROR)
                                      .build();
    }

    public static RideReservationResponse ofRideAlreadyReserved()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(OutcomeCause.RIDE_ALREADY_RESERVED)
                                      .build();
    }

    public static RideReservationResponse ofRideAlreadyReservedForTimePeriod()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(OutcomeCause.RIDE_ALREADY_RESERVED_FOR_TIME_PERIOD)
                                      .build();
    }

    public static RideReservationResponse ofNotEnoughSeatsAvailable()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(OutcomeCause.NOT_ENOUGH_SEATS_AVAILABLE)
                                      .build();
    }

    public static RideReservationResponse ofTripNotFound()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(OutcomeCause.TRIP_NOT_FOUND)
                                      .build();
    }

    public static RideReservationResponse ofRiderNotFound()
    {
        return RideReservationResponse.builder()
                                      .success(false)
                                      .outcomeCause(OutcomeCause.RIDER_NOT_FOUND)
                                      .build();
    }
}