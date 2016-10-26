package io.github.zutherb.appstash.common.repository;

import java.util.List;

/**
 * @author zutherb
 */
public interface AbstractRepository <S> {
    void save(S entity);
    void dropCollection();
    long countAll();
    List<S> findAll();
    void removeAll();
    void delete(S entity);
    boolean collectionExists();


}
