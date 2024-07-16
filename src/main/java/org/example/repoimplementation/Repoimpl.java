package org.example.repoimplementation;

import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import org.example.entity.Entites;
import org.example.repositary.Repo;
import java.util.List;

import io.ebean.Query;


public class Repoimpl implements Repo {

    private  EbeanServer ebeanServer;


    public Repoimpl(EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }

    @Override
    public Entites findById(Long id) {
        return ebeanServer.find(Entites.class,id);
    }

    @Override
    public List<Entites> findAll() {
        Query<Entites> query = ebeanServer.find(Entites.class);
        return query.findList();
    }

    @Override
    @Transactional
    public void save(Entites user) {
        ebeanServer.save(user);
    }

    @Override
    public void update(Entites user) {
        ebeanServer.update(user);
    }

    @Override
    public void delete(Entites user) {
        ebeanServer.delete(user);
    }
}
