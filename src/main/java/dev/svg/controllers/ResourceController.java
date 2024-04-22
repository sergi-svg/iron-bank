package dev.svg.controllers;


import java.util.HashMap;
import java.util.List;

public interface ResourceController <T> {

    T getResourceByParams(HashMap<String,String> params);
    List<T> getAllResources();
    T updateResource(T t);
    T createResource(T t);
    void deleteResource(Long id);
}
