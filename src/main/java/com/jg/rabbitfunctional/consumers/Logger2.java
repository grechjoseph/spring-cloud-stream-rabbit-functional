package com.jg.rabbitfunctional.consumers;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class Logger2 implements Consumer<String> {

    @Override
    public void accept(final String s) {
        log.info("Logger2 ::: {}", s);
    }

}
