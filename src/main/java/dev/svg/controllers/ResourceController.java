package dev.svg.controllers;


import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface ResourceController <T> {

    Optional<T> getResourceByParams(HashMap<String,String> params);
    List<T> getAllResources();
    T updateResource(Long id, T t);
    T createResource(T t);
    void deleteResource(String id);
}
