package com.jg.rabbitfunctional;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Consumer;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final Consumer<String> jgConsumeIt;

    @GetMapping("/{message}")
    public void publishMessage(@PathVariable final String message) {
        jgConsumeIt.accept(message);
    }

}
