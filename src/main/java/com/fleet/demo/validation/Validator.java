package com.fleet.demo.validation;

@FunctionalInterface
public interface Validator<T> {

    boolean validate(T input);
}
