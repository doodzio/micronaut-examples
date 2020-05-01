# Reproduce 

1. Create mn app

```shell script
mn create-app example.micronaut.app

```

2. Create conroller

```shell script
nm create-controller big-integer 
```

3. Add model and change controller 
    1. Create Model
        ```java
        class Value<T> {
            public Value(T value) {
              this.value = value;
            }
        
            T value;
        }
        ```
    
    2. Change controller path and implement controller methods
    4. Add tests

4. Run test
```shell script
./gradlew test
```

## Reference

Jackson documentation
[USE_BIG_INTEGER_FOR_INTS](http://fasterxml.github.io/jackson-databind/javadoc/2.10/com/fasterxml/jackson/databind/DeserializationFeature.html#USE_BIG_INTEGER_FOR_INTS)
