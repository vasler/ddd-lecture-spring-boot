package vasler.dddlecturetest.ports.primary.driver;

import org.jmolecules.ddd.types.Association;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import vasler.dddlecture.domain.model.driver.Driver;
import vasler.dddlecture.ports.primary.driver.TripUseCase;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingRequest;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecture.ports.secondary.repository.Drivers;
import vasler.dddlecture.ports.secondary.repository.Trips;
import vasler.dddlecturetest._config_.PostgresTestConfiguration;
import vasler.dddlecturetest._config_.TestConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
class TripCreationTest {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(TripCreationTest.class);

	@Autowired
	TripUseCase tripUseCase;

	@Autowired
	Drivers drivers;

	@Autowired
	Trips trips;

	private Driver driver;

	@Test
	@Order(1)
	@Commit
	void whenCreatingTrip_TripCreated() throws Exception {
		UUID driverId = UUID.randomUUID();

		driver = Driver.builder()
				.id(new Driver.DriverId(driverId))
				.name("Test driver")
				.email("test.driver@driver.com")
				.build();

		var savedDriver = drivers.save(driver);

		var tripCreationRequest = TripCreationRequest.builder()
				.origin("Adresa polazista")
				.destination("Adresa odredista")
				.departureTime(LocalDateTime.now())
				.driver(savedDriver.getId().id())
				.build();

		tripUseCase.createTrip(tripCreationRequest);

		// TODO - verify data is in the database

		trips.findAll().forEach(c -> System.out.println(c.getId()));


		var driversTrips = trips.findByDriver(Association.forAggregate(driver));
		Assertions.assertEquals(1, driversTrips.size());

		driversTrips.stream()
				.map(trip -> drivers.resolveRequired(trip.getDriver()))
				.forEach(driver -> Assertions.assertEquals(driverId, driver.getId().id()));

		var rideOffer = RideOfferingRequest.builder()
						.seats(4)
								.trip(tr)
		tripUseCase.offerRide()
	}
}
