# Define Spring Cloud Stream using Functional Programming

## Source/Supplier

### Bean

```java
@Bean
public Supplier<String> someSource() {
    // Publishes the String "Some String!" every 3 seconds.
    return () -> Flux.interval(3, ChronoUnit.SECONDS).map(i -> "Some String!");
}
```

### Binding

Output Supplier's events to exchange <b>some-source-events</b>.

```
spring.cloud.stream.bindings.someSource-out-0.destination=some-supplier-events
```

The <b>-out-0</b> part is standard for output.

## Transformer/Processor/Function

### Bean

```java
@Bean
public Function<String, String> someTransformer() {
    return s -> s.toUppercase();
}
```

### Binding

Handle events coming from exchange <b>some-source-events</b>.

```
spring.cloud.stream.bindings.someTransformer-in-0.destination=some-source-events
spring.cloud.stream.bindings.someTransformer-in-0.group=to-uppercase-transformer
```

The <b>-in-0</b> part is standard for input.

We also need to define where to output once transformed.

```
spring.cloud.stream.bindings.someTransformer-out-0.destination=some-transformer-events
```

## Sink/Consumer

### Bean

```java
@Bean
public Consumer<String> someSink() {
    return s -> log.info("Result::: {}", s);
}
```

### Binding

Handle events coming from exchange <b>some-transformer-events</b>.

```
spring.cloud.stream.bindings.someSink-in-0.destination=some-transformer-events
spring.cloud.stream.bindings.someSink-in-0.group=some-consumer
```

## Register Functions to Stream

```
spring.cloud.function.definition=someSource;someTransformer;someSink
```