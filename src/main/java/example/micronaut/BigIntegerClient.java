package example.micronaut;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

import java.math.BigInteger;

@Client("/")
public interface BigIntegerClient {

  @Post("/object")
  HttpResponse<Value<String>> forObject(@Body Value<Object> value);

  @Post("/bigInteger")
  HttpResponse<Value<String>> forBigInteger(@Body Value<BigInteger> value);
}
