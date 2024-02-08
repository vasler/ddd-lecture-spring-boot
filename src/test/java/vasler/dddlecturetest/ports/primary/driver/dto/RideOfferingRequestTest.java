package vasler.dddlecturetest.ports.primary.driver.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import vasler.dddlecture.ports.primary.ValueValidationException;
import vasler.dddlecture.ports.primary.driver.dto.RideOfferingRequest;
import vasler.dddlecture.ports.primary.driver.dto.TripCreationRequest;
import vasler.dddlecturetest._config_.PostgresTestConfiguration;
import vasler.dddlecturetest._config_.TestConfig;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest(classes = TestConfig.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
public class RideOfferingRequestTest {

    @Test
    public void givenArgumentsValid_whenBuildingRideOfferingRequest_NoExceptionThrown() {
        Assertions.assertDoesNotThrow(() -> RideOfferingRequest.builder()
            .seats(20)
            .build()
        );
    }

    @Test
    public void givenNumberOfSeatsTooLarge_whenBuildingRideOfferingRequest_NoExceptionThrown() {
        ValueValidationException exception = Assertions.assertThrows(ValueValidationException.class,
                () -> RideOfferingRequest.builder()
                        .seats(50)
                        .build()
        );

        Assertions.assertEquals(1, exception.getPropertyErrors().size());
    }

    @Test
    public void givenArgumentsNull_whenBuildingRideOfferinngRequest_ExceptionThrownWithCorrectNumberOfPropertyErrors() {
        ValueValidationException exception = Assertions.assertThrows(ValueValidationException.class,
            () -> RideOfferingRequest.builder().build());

        Assertions.assertEquals(1, exception.getPropertyErrors().size());
    }
}
