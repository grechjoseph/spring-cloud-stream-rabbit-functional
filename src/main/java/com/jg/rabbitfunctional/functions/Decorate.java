package com.jg.rabbitfunctional.functions;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

@Slf4j
public class Decorate implements Function<String, String> {

    @Override
    public String apply(final String s) {
        return "*** " + s + " ***";
    }

}
