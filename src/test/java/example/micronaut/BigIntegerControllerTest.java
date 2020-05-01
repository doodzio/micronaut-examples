package example.micronaut;

import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.LoggerFactory;

import javax.inject.Inject;
import java.math.BigInteger;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;

@MicronautTest
public class BigIntegerControllerTest {

  public static final BigInteger BIG_INTEGER_VALUE =
      BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN);

  public static final String INVALID_JSON =
      "Invalid JSON: Numeric value (92233720368547758070) out of range of long (-9223372036854775808 - 9223372036854775807)";

  @Inject BigIntegerClient bigIntegerClient;

  @Test
  public void shouldAcceptBigIntegerAsAsItself() {
    HttpClientResponseException httpClientException =
        Assertions.assertThrows(
            HttpClientResponseException.class,
            () -> {
              Value<BigInteger> value = new Value<>(BIG_INTEGER_VALUE);
              bigIntegerClient.forBigInteger(value);
            });

    Assertions.assertEquals(BAD_REQUEST, httpClientException.getStatus());
    LoggerFactory.getLogger(getClass()).info(() -> httpClientException.getMessage());
    Assertions.assertTrue(httpClientException.getMessage().contains(INVALID_JSON));
  }
}
