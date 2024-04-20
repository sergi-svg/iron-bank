package dev.svg.controllers;

import java.util.List;

public interface ResourceController <T> {

    T getResourceByParam(String param);
    List<T> getAllResources();
    T updateResource(T t);
    T createResource(T t);
}
