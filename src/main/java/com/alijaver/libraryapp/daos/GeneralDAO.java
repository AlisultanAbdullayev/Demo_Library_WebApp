package com.alijaver.libraryapp.daos;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface GeneralDAO<T> {
    List<T> getAll();

    List<T> getAll(Sort sort);

    T get(Long id);

    T save(T obj);

    void delete(T object);

    List<T> search(String text);

}
