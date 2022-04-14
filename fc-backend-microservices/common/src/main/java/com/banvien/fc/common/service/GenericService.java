package com.banvien.fc.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericService<T, ID extends Serializable> {

    Optional<T> findById(final ID id);

    List<T> findAll();

    void delete(final ID id);

    Optional<T> save(T obj);

}
