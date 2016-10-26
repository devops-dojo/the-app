package io.github.zutherb.appstash.common.service;

import io.github.zutherb.appstash.common.repository.AbstractRepository;
import io.github.zutherb.appstash.common.repository.AbstractRepository;
import org.dozer.Mapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zutherb
 */
public class AbstractServiceImpl<D, S> implements AbstractService<D> {

    private Mapper dozerMapper;
    private AbstractRepository<S> repository;
    private Class<D> destinationClass;
    private Class<S> sourceClass;

    public AbstractServiceImpl(AbstractRepository<S> repository, Mapper dozerMapper, Class<D> destinationClass, Class<S> sourceClass) {
        this.repository = repository;
        this.dozerMapper = dozerMapper;
        this.destinationClass = destinationClass;
        this.sourceClass = sourceClass;
    }

    @Override
    public void save(D object) {
        S mappedObject = dozerMapper.map(object, sourceClass);
        repository.save(mappedObject);
    }

    @Override
    public void dropCollection() {
        repository.dropCollection();
    }

    @Override
    public long countAll() {
        return repository.countAll();
    }

    @Override
    public List<D> findAll() {
        List<S> sourceEntities = repository.findAll();
        return mapListOfSourceEntitiesToDestinationEntities(sourceEntities);
    }


    @Override
    public void delete(D object) {
        repository.delete(dozerMapper.map(object, sourceClass));
    }

    /**
     * Method maps a list of source entities to destination entities
     *
     * @param sourceEntities list of source entities
     * @return list of destination entities
     */
    protected List<D> mapListOfSourceEntitiesToDestinationEntities(List<S> sourceEntities) {
        return sourceEntities.stream()
                .map(this::mapSourceEntityToDestinationEntity)
                .collect(Collectors.toList());
    }

    protected D mapSourceEntityToDestinationEntity(S sourceEntity) {
        return dozerMapper.map(sourceEntity, destinationClass);
    }

    protected Mapper getDozerMapper() {
        return dozerMapper;
    }

    protected AbstractRepository<S> getRepository() {
        return repository;
    }
}
