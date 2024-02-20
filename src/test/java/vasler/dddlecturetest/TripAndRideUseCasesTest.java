package vasler.dddlecturetest;

import org.jmolecules.ddd.types.Association;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.domain.model.rider.Rider;
import vasler.dddlecture.domain.model.trip.Trip;
import vasler.dddlecture.ports.primary.driver.RideOfferUseCase;
import vasler.dddlecture.ports.primary.driver.TripCreationUseCase;
import vasler.dddlecture.ports.primary.driver.dto.*;
import vasler.dddlecture.ports.primary.rider.RideReservationRequest;
import vasler.dddlecture.ports.primary.rider.RideReservationResponse;
import vasler.dddlecture.ports.primary.rider.RideReservationUseCase;
import vasler.dddlecture.ports.secondary.repository.Drivers;
import vasler.dddlecture.ports.secondary.repository.Riders;
import vasler.dddlecture.ports.secondary.repository.Trips;
import vasler.dddlecturetest._config_.PostgresTestConfiguration;
import vasler.dddlecturetest._config_.TestConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles({"integration-test"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
@ExtendWith(MockitoExtension.class)
class TripAndRideUseCasesTest
{
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TripAndRideUseCasesTest.class);

    @Autowired
    TripCreationUseCase tripCreationUseCase;

    @Autowired
    RideOfferUseCase rideOfferUseCase;

    @Autowired
    RideReservationUseCase rideReservationUseCase;

    @Autowired
    Drivers drivers;

    @Autowired
    Riders riders;

    @Autowired
    Trips trips;

    private final String tripCreationRequestOneId = UUID.randomUUID()
                                                        .toString();
    private final String tripCreationRequestTwoId = UUID.randomUUID()
                                                        .toString();
    private final UUID driverOneId = UUID.randomUUID();
    private final UUID driverTwoId = UUID.randomUUID();
    private Driver driverOne;
    private Driver driverTwo;
    private final UUID riderId = UUID.randomUUID();
    private Rider rider;
    private UUID tripOneId;
    private UUID tripTwoId;

    private TripCreationRequest buildTripCreationRequest(Driver driver, String tripCreationRequestId, LocalDateTime departureTime, LocalDateTime arrivalTime)
    {
        return TripCreationRequest.builder()
                                  .origin("Departure address")
                                  .destination("Destination address")
                                  .departureTime(departureTime)
                                  .arrivalTime(arrivalTime)
                                  .driver(driver.getId()
                                                   .id())
                                  .requestId(tripCreationRequestId)
                                  .build();
    }

    @Test
    @Order(1)
    @Commit
    void fixture()
        throws Exception
    {
        driverOne = Driver.builder()
                          .id(new Driver.DriverId(driverOneId))
                          .name("Test driver One")
                          .email("test.driver.one@driver.com")
                          .build();
        driverOne = drivers.save(driverOne);

        driverTwo = Driver.builder()
                          .id(new Driver.DriverId(driverTwoId))
                          .name("Test driver Two")
                          .email("test.driver.two@driver.com")
                          .build();
        driverTwo = drivers.save(driverTwo);

        rider = Rider.builder()
                     .id(new Rider.RiderId(riderId))
                     .name("Test rider")
                     .email("test.rider@rider.com")
                     .updateTime(LocalDateTime.now())
                     .build();
        rider = riders.save(rider);
    }

    private UUID fixture_createTrip(TripCreationRequest tripCreationRequest, UUID driverId, Driver driver)
    {
        var tripCreationResponse = tripCreationUseCase.createTrip(tripCreationRequest);
        Assertions.assertTrue(tripCreationResponse.isSuccess());
        Assertions.assertEquals(TripCreationResponse.OutcomeCause.TRIP_CREATED, tripCreationResponse.getOutcomeCause());

        UUID tripId = tripCreationResponse.getTripId();
        trips.findAll()
             .forEach(c -> System.out.println(c.getId()));

        var driversTrips = trips.findByDriver(Association.forAggregate(driver));
        Assertions.assertEquals(1, driversTrips.size());

        driversTrips.stream()
                    .map(trip -> drivers.resolveRequired(trip.getDriver()))
                    .forEach(d -> Assertions.assertEquals(driverId, d.getId().id()));

        return tripId;
    }

    @Test
    @Order(2)
    @Commit
    void givenTripWithRequestIdDoesNotExist_whenCreatingTrip_TripCreated()
        throws Exception
    {
        tripOneId = fixture_createTrip(buildTripCreationRequest(driverOne,
                                                                tripCreationRequestOneId,
                                                                LocalDateTime.of(2024, 2, 20, 8, 0),
                                                                LocalDateTime.of(2024, 2, 20, 16, 0)),
                                       driverOneId, driverOne);
        tripTwoId = fixture_createTrip(buildTripCreationRequest(driverTwo,
                                                                tripCreationRequestTwoId,
                                                                LocalDateTime.of(2024, 2, 20, 12, 0),
                                                                LocalDateTime.of(2024, 2, 21, 2, 0)),
                                       driverTwoId, driverTwo);
    }

    @Test
    @Order(3)
    @Commit
    void givenTripWithRequestIdExists_whenCreatingTrip_NewTripNotCreated()
        throws Exception
    {
        var tripCreationResponse = tripCreationUseCase.createTrip(
            buildTripCreationRequest(driverOne,
                                     tripCreationRequestOneId,
                                     LocalDateTime.of(2024, 2, 20, 8, 0),
                                     LocalDateTime.of(2024, 2, 20, 16, 0)
            )
        );

        Assertions.assertTrue(tripCreationResponse.isSuccess());
        Assertions.assertEquals(TripCreationResponse.OutcomeCause.TRIP_ALREADY_CREATED, tripCreationResponse.getOutcomeCause());
        Assertions.assertEquals(tripOneId, tripCreationResponse.getTripId());

        trips.findAll()
             .forEach(c -> System.out.println(c.getRequestId()));


        var driversTrips = trips.findByDriver(Association.forAggregate(driverOne));
        Assertions.assertEquals(1, driversTrips.size());
    }

    private void fixture_createRideOffer(UUID tripId)
    {
        RideOfferRequest rideOfferRequest = RideOfferRequest.builder()
                                                            .offeredSeatCount(4)
                                                            .trip(tripId)
                                                            .build();

        var rideOfferResponse = rideOfferUseCase.offerRide(rideOfferRequest);
        Assertions.assertEquals(true, rideOfferResponse.isSuccess());
        Assertions.assertEquals(RideOfferResponse.OutcomeCause.RIDE_OFFER_SUCCESSFUL, rideOfferResponse.getOutcomeCause());

        var trip = trips.findById(new Trip.TripId(tripId));
        var rideOffer = trip.get().getRideOffer();
        Assertions.assertEquals(4, rideOffer.getOfferedSeatCount());
        Assertions.assertEquals(4, rideOffer.getAvailableSeatCount());

    }

    @Test
    @Order(4)
    @Commit
    void givenRideNotYetOffered_whenOfferingRide_RideOfferCreated()
        throws Exception
    {
        fixture_createRideOffer(tripOneId);
        fixture_createRideOffer(tripTwoId);
    }

    @Test
    @Order(5)
    @Commit
    void givenRideNotAlreadyOffered_whenOfferingRide_RideAlreadyOffered()
        throws Exception
    {
        RideOfferRequest rideOfferRequest = RideOfferRequest.builder()
                                                            .offeredSeatCount(4)
                                                            .trip(tripOneId)
                                                            .build();

        var rideOfferResponse = rideOfferUseCase.offerRide(rideOfferRequest);
        Assertions.assertEquals(false, rideOfferResponse.isSuccess());
        Assertions.assertEquals(RideOfferResponse.OutcomeCause.RIDE_ALREADY_OFFERED, rideOfferResponse.getOutcomeCause());

        var trip = trips.findById(new Trip.TripId(tripOneId));
        var rideOffer = trip.get().getRideOffer();
        Assertions.assertEquals(4, rideOffer.getOfferedSeatCount());
        Assertions.assertEquals(4, rideOffer.getAvailableSeatCount());
    }

    @Test
    @Order(6)
    @Commit
    void givenNotEnoughSeatsAvailable_whenReservingRide_RideNotReserved()
    {
        RideReservationRequest rideReservationRequest = RideReservationRequest
            .builder()
            .trip(tripOneId)
            .seatCount(5)
            .rider(riderId)
            .build();

        var rideReservationResponse = rideReservationUseCase.reserveRide(rideReservationRequest);

        Assertions.assertFalse(rideReservationResponse.isSuccess());
        Assertions.assertEquals(RideReservationResponse.OutcomeCause.NOT_ENOUGH_SEATS_AVAILABLE, rideReservationResponse.getOutcomeCause());
    }

    @Test
    @Order(7)
    @Commit
    void givenEnoughSeatsAvailableAndRideNotReserved_whenReservingRide_RideReserved()
    {
        RideReservationRequest rideReservationRequest = RideReservationRequest
            .builder()
            .trip(tripOneId)
            .seatCount(1)
            .rider(riderId)
            .build();

        var rideReservationResponse = rideReservationUseCase.reserveRide(rideReservationRequest);

        Assertions.assertTrue(rideReservationResponse.isSuccess());
        Assertions.assertEquals(RideReservationResponse.OutcomeCause.RIDE_RESERVATION_SUCCESSFUL, rideReservationResponse.getOutcomeCause());
    }

    @Test
    @Order(8)
    @Commit
    void givenEnoughSeatsAvailableAndRideReserved_whenReservingRide_RideNotReserved()
    {
        RideReservationRequest rideReservationRequest = RideReservationRequest
            .builder()
            .trip(tripOneId)
            .seatCount(1)
            .rider(riderId)
            .build();

        var rideReservationResponse = rideReservationUseCase.reserveRide(rideReservationRequest);

        Assertions.assertFalse(rideReservationResponse.isSuccess());
        Assertions.assertEquals(RideReservationResponse.OutcomeCause.RIDE_ALREADY_RESERVED, rideReservationResponse.getOutcomeCause());
    }

    @Test
    @Order(9)
    @Commit
    void givenTripTimePeriodsOverlap_whenReservingRide_RideNotReserved()
    {
        RideReservationRequest rideReservationRequest = RideReservationRequest
            .builder()
            .trip(tripTwoId)
            .seatCount(1)
            .rider(riderId)
            .build();

        var rideReservationResponse = rideReservationUseCase.reserveRide(rideReservationRequest);

        Assertions.assertFalse(rideReservationResponse.isSuccess());
        Assertions.assertEquals(RideReservationResponse.OutcomeCause.RIDE_ALREADY_RESERVED_FOR_TIME_PERIOD, rideReservationResponse.getOutcomeCause());
    }
}
