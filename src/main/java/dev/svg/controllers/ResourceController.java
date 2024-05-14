package dev.svg.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ResourceController <T> {

    List<T> getAllResources();
    List<T> getResourcesByParam(HashMap<String,String> params);

    Optional<T> getResourceByParams(HashMap<String,String> params);

    T updateResource(String id, T t);
    T createResource(T t);

    void deleteResource(String id);
    void deleteAllResources();
}
