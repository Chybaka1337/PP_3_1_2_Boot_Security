package ru.kata.spring.boot_security.demo.util;

import org.modelmapper.ModelMapper;

public class Util {
    private static final ModelMapper MAPPER = new ModelMapper();

    public static ModelMapper getMapper() {
        return MAPPER;
    }
}
