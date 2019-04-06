package com.lambda.demo.interfaces;

import java.util.List;

@FunctionalInterface
public interface ListToStringFunction {

    String execute(List<String> list);

}
