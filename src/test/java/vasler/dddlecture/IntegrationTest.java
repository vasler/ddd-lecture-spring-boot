package vasler.dddlecture;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Commit;
import vasler.dddlecture._config_.PostgresTestConfiguration;

@SpringBootTest(webEnvironment = org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(PostgresTestConfiguration.class)
class IntegrationTest {
	private static final Logger log = org.slf4j.LoggerFactory.getLogger(IntegrationTest.class);

	@Test
	@Order(1)
	@Commit
	void test() throws Exception {
		Assertions.assertTrue(true);
	}
}
