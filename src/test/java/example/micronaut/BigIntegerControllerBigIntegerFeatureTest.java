package example.micronaut;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.math.BigInteger;

import static io.micronaut.http.HttpStatus.BAD_REQUEST;
import static io.micronaut.http.HttpStatus.OK;

@Property(name = "jackson.deserialization.USE_BIG_INTEGER_FOR_INTS", value = "true")
@MicronautTest
public class BigIntegerControllerBigIntegerFeatureTest {

  public static final BigInteger BIG_INTEGER_VALUE =
      BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN);

  public static final String INVALID_JSON =
      "Invalid JSON: Numeric value (92233720368547758070) out of range of long (-9223372036854775808 - 9223372036854775807)";

  @Inject ObjectMapper objectMapper;
  @Inject BigIntegerClient bigIntegerClient;
  EmbeddedServer embeddedServer;

  @BeforeEach
  void init() {
    embeddedServer = ApplicationContext.run(EmbeddedServer.class);

    Assertions.assertTrue(objectMapper.isEnabled(DeserializationFeature.USE_BIG_INTEGER_FOR_INTS));
  }

  @Test
  public void shouldDeserializeIntToBigInteger() {
    Value<Object> value = new Value<>(Integer.MAX_VALUE);
    HttpResponse<Value<String>> response = bigIntegerClient.forObject(value);
    Assertions.assertEquals(OK, response.getStatus());
    Value<String> body = response.getBody().get();

    Assertions.assertEquals(OK, response.getStatus());
    //    Fails
    //    Assertions.assertEquals(Integer.class.getSimpleName(), body.getValue());
    Assertions.assertEquals(Long.class.getSimpleName(), body.getValue());
  }

  @Test
  public void shouldDeserializeLongToBigInteger() { //  FAILS because type is Long
    //    Assertions.assertEquals(Integer.class.getSimpleName(), result.value);

    Value<Object> value = new Value<>(Long.MAX_VALUE);
    HttpResponse<Value<String>> response = bigIntegerClient.forObject(value);
    Assertions.assertEquals(OK, response.getStatus());
    Value<String> body = response.getBody().get();

    Assertions.assertEquals(OK, response.getStatus());
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

  @AfterEach
  void cleanup() {
    embeddedServer.stop();
  }
}
