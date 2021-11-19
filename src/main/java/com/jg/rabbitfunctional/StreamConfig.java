package com.jg.rabbitfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Configuration
public class StreamConfig {

    /**
     * Bean to use to publish manually onto the stream.
     */
    @Bean
    public Consumer<String> jgConsumeIt(final StreamBridge streamBridge) {
        return input -> streamBridge.send("jgProduceIt-out-0", input);
    }

    /**
     * Bean is started which publishes a random UUID string to stream every second.
     */
    @Bean
    public Supplier<Flux<String>> jgProduceIt() {
        return () -> Flux.interval(Duration.ofSeconds(1)).map(i -> UUID.randomUUID().toString());
    }

    /**
     * Converts message to upper case.
     */
    @Bean
    public Function<String, String> jgToUpperCase() {
        return String::toUpperCase;
    }

    /**
     * Logs the result.
     */
    @Bean
    public Consumer<String> jgLogIt1() {
        return s -> log.info("Logger 1 Result: {}", s);
    }

    /**
     * Logs the result.
     */
    @Bean
    public Consumer<String> jgLogIt2() {
        return s -> log.info("Logger 2 Result: {}", s);
    }

}
