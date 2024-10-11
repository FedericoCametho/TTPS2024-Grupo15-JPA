package org.example.ttps2024grupo15.dao;

import java.util.List;

public interface GenericDAO <T>  {
    public T save(T t);
    public T getById(Long id);
    public T get(T t);
    public T update(T t);
    public boolean delete(T t);
    public boolean delete(Long id);
    public List<T> getAll();
    public List<T> getAllByOrder(String columnOrder);
}
