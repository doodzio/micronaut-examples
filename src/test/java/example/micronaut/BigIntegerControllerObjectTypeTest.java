package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigInteger;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.OK;

@MicronautTest
public class BigIntegerControllerObjectTypeTest {

  public static final BigInteger BIG_INTEGER_VALUE =
      BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN);

  public static final String INVALID_JSON =
      "Invalid JSON: Numeric value (92233720368547758070) out of range of long (-9223372036854775808 - 9223372036854775807)";

  @Inject BigIntegerClient bigIntegerClient;

  @Test
  public void shouldDeserializeToInt() {
    Value<Object> value = new Value<>(Integer.MAX_VALUE);
    HttpResponse<Value<String>> response = bigIntegerClient.forObject(value);

    Assertions.assertEquals(OK, response.getStatus());
    Value<String> body = response.getBody().get();
    //  FAILS because type is Long
    //    Assertions.assertEquals(Integer.class.getSimpleName(), result.value);

    Assertions.assertEquals(Long.class.getSimpleName(), body.getValue());
  }

  @Test
  public void shouldDeserializeToLong() {
    Value<Object> value = new Value<>(Long.MAX_VALUE);
    HttpResponse<Value<String>> response = bigIntegerClient.forObject(value);

    Assertions.assertEquals(OK, response.getStatus());
    Value<String> body = response.getBody().get();
    Assertions.assertEquals(Long.class.getSimpleName(), body.getValue());
  }

  @Test
  public void shouldDeserializeToBigInteger() {
    HttpClientResponseException httpClientException =
        Assertions.assertThrows(
            HttpClientResponseException.class,
            () -> {
              Value<Object> value = new Value<>(BIG_INTEGER_VALUE);
              bigIntegerClient.forObject(value);
            });

    Assertions.assertEquals(BAD_REQUEST, httpClientException.getStatus());
    Assertions.assertTrue(httpClientException.getMessage().contains(INVALID_JSON));
  }
}
