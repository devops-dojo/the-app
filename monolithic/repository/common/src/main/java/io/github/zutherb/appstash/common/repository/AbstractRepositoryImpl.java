package io.github.zutherb.appstash.common.repository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author zutherb
 */
public abstract class AbstractRepositoryImpl<S> implements AbstractRepository<S> {

    protected MongoOperations mongoOperations;
    private Class<S> clazz;

    public AbstractRepositoryImpl(MongoOperations mongoOperations, Class<S> clazz) {
        this.mongoOperations = mongoOperations;
        this.clazz = clazz;
    }

    @Override
    public void save(S entity) {
        mongoOperations.save(entity);
    }

    @Override
    public void dropCollection() {
        mongoOperations.dropCollection(clazz);
    }

    @Override
    public long countAll() {
        return mongoOperations.count(new Query(), clazz);
    }

    @Override
    public List<S> findAll() {
        return mongoOperations.findAll(clazz);
    }

    @Override
    public void removeAll(){
        mongoOperations.remove(new Query(), clazz);
    }

    @Override
    public void delete(S entity) {
        mongoOperations.remove(entity);
    }

    @Override
    public boolean collectionExists(){
        return mongoOperations.collectionExists(clazz);
    }

}
