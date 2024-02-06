package vasler.dddlecturetest.ports.primary.driver;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import vasler.dddlecture.ports.primary.driver.TripUseCase;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
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
	Trips trips;

	@Test
	@Order(1)
	@Commit
	void whenCreatingTrip_TripCreated() throws Exception {
		var tripCreationRequest = TripCreationRequest.builder()
				.origin("Adresa polazista")
				.destination("Adresa odredista")
				.departureTime(LocalDateTime.now())
				.driver(UUID.randomUUID())
				.build();

		tripUseCase.createTrip(tripCreationRequest);

		// TODO - verify data is in the database

		trips.findAll().forEach(c -> System.out.println(c.getId()));


		Assertions.assertTrue(true);
	}
}
