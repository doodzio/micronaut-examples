package example.micronaut;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import java.math.BigInteger;

@Controller("/")
public class BigIntegerController {

  @Post("/object")
  public Value<String> forObject(@Body Value<Object> value) {
    return new Value<>(value.getValue().getClass().getSimpleName());
  }

  @Post("/bigInteger")
  public Value<String> forBigInteger(@Body Value<BigInteger> value) {
    return new Value<>(value.getValue().getClass().getSimpleName());
  }
}
