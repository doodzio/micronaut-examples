package example.micronaut;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value<T> {

  private T value;

  @JsonCreator
  public Value(@JsonProperty("value") T value) {
    this.value = value;
  }

  public T getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "Value{" + "value=" + value + '}';
  }
}
