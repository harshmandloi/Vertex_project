package org.example.repositary;

import org.example.entity.Entites;

import java.util.List;

public interface Repo {

    Entites findById(Long id);

    List<Entites> findAll();

    void save(Entites user);

    void update(Entites user);

    void delete(Entites user);
}

