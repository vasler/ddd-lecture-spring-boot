package vasler.dddlecturetest.ports.primary.driver.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vasler.dddlecture.ports.primary.ValueValidationException;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecturetest._config_.PostgresTestConfiguration;
import vasler.dddlecturetest._config_.TestConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
public class TripCreationRequestTest {

    @Test
    public void givenArgumentsValid_whenBuildingTripCreationRequest_NoExceptionThrown() {
        Assertions.assertDoesNotThrow(() -> TripCreationRequest.builder()
            .origin("origin")
            .destination("destination")
            .departureTime(LocalDateTime.now())
            .driver(UUID.randomUUID())
            .build()
        );
    }

    @Test
    public void givenArgumentsNull_whenBuildingTripCreationRequest_ExceptionThrownWithCorrectNumberOfPropertyErrors() {
        ValueValidationException exception = Assertions.assertThrows(ValueValidationException.class,
            () -> TripCreationRequest.builder().build());

        Assertions.assertEquals(4, exception.getPropertyErrors().size());
    }
}
