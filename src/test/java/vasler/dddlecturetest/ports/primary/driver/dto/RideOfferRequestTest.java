package vasler.dddlecturetest.ports.primary.driver.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vasler.dddlecture.ports.primary.ValueValidationException;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferRequest;
import vasler.dddlecturetest._config_.PostgresTestConfiguration;
import vasler.dddlecturetest._config_.TestConfig;

import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
public class RideOfferRequestTest {

    @Test
    public void givenArgumentsValid_whenBuildingRideOfferingRequest_NoExceptionThrown() {
        Assertions.assertDoesNotThrow(() -> RideOfferRequest.builder()
            .offeredSeatCount(20)
            .trip(UUID.randomUUID())
            .build()
        );
    }

    @Test
    public void givenNumberOfSeatsTooLarge_whenBuildingRideOfferingRequest_NoExceptionThrown() {
        ValueValidationException exception = Assertions.assertThrows(ValueValidationException.class,
            () -> RideOfferRequest.builder()
                .offeredSeatCount(50)
                .trip(UUID.randomUUID())
                .build()
        );

        Assertions.assertEquals(1, exception.getPropertyErrors().size());
    }

    @Test
    public void givenArgumentsNull_whenBuildingRideOfferinngRequest_ExceptionThrownWithCorrectNumberOfPropertyErrors() {
        ValueValidationException exception = Assertions.assertThrows(ValueValidationException.class,
            () -> RideOfferRequest.builder().build());

        Assertions.assertEquals(2, exception.getPropertyErrors().size());
    }
}
