package io.github.zutherb.appstash.common.service;

import java.util.List;

/**
 * Abstract Repository that provides all must crud functions
 *
 * @author zutherb
 */
public interface AbstractService <D>{
    void dropCollection();
    long countAll();
    List<D> findAll();
    void save(D object);
    void delete(D object);
}
