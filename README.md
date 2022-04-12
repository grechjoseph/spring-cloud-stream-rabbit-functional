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
spring.cloud.stream.bindings.someSource-out-0.destination=some-source-events
```

#### Alias for Bindings
```
spring.cloud.stream.function.bindings.someSource-out-0=do-the-source
spring.cloud.stream.bindings.do-the-source.destination=some-source-events
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

#### Alias for Bindings
```
spring.cloud.stream.function.bindings.someTransformer-in-0=do-the-transform
spring.cloud.stream.bindings.do-the-transform.destination=some-source-events
spring.cloud.stream.bindings.do-the-transform.group=to-uppercase-transformer
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
#### Alias for Bindings
```
spring.cloud.stream.function.bindings.someSink-in-0=do-the-sink
spring.cloud.stream.bindings.do-the-sink.destination=some-transformer-events
spring.cloud.stream.bindings.do-the-sink.group=some-consumer
```

## Register Functions to Stream

```
spring.cloud.function.definition=someSource;someTransformer;someSink
```