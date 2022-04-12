package com.jg.rabbitfunctional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.time.temporal.ChronoUnit.SECONDS;

@Slf4j
@Configuration
public class StreamConfig {

    @Bean
    public Consumer<String> manualSupplier(final StreamBridge streamBridge) {
        return s -> streamBridge.send("stringSupplier-out-0", s);
    }

    @Bean
    public Supplier<Flux<String>> stringSupplier() {
        return () -> Flux.interval(Duration.of(3, SECONDS))
                .map(i -> LocalDateTime.now() + " ::: Hello World!");
    }

    @Bean
    public Function<String, String> toUppercase() {
        return String::toUpperCase;
    }

    @Bean
    public Function<String, String> decorate() {
        return s -> "*** " + s + " ***";
    }

    @Bean
    public Consumer<String> logger1() {
        return s -> log.info("Logger1 ::: {}", s);
    }

    @Bean
    public Consumer<String> logger2() {
        return s -> log.info("Logger2 ::: {}", s);
    }

}
