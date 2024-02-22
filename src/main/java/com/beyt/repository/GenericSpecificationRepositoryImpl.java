package com.beyt.repository;

import com.beyt.dto.Criteria;
import com.beyt.dto.DynamicQuery;
import com.beyt.filter.DatabaseFilterManager;
import com.beyt.filter.query.builder.QueryBuilder;
import com.beyt.util.ListConsumer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public class GenericSpecificationRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericSpecificationRepository<T, ID>, JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    private final EntityManager entityManager;

    public GenericSpecificationRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
                                              EntityManager entityManager) {

        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAllWithCriteria(List<Criteria> criteriaList) {
        return DatabaseFilterManager.findAll(this, criteriaList);
    }

    @Override
    public List<T> findAllWithSearchQuery(DynamicQuery dynamicQuery) {
        return DatabaseFilterManager.getEntityListBySelectableFilterAsList(this, dynamicQuery);
    }

    @Override
    public Page<T> findAllWithSearchQueryAsPage(DynamicQuery dynamicQuery) {
        return DatabaseFilterManager.getEntityListBySelectableFilterAsPage(this, dynamicQuery);
    }

    @Override
    public List<Tuple> findAllWithSearchQueryWithTuple(DynamicQuery dynamicQuery) {
        return DatabaseFilterManager.getEntityListBySelectableFilterWithTupleAsList(this, dynamicQuery);
    }

    @Override
    public Page<Tuple> findAllWithSearchQueryWithTupleAsPage(DynamicQuery dynamicQuery) {
        return DatabaseFilterManager.getEntityListBySelectableFilterWithTupleAsPage(this, dynamicQuery);
    }

    @Override
    public <ResultType> List<ResultType> findAllWithSearchQuery(DynamicQuery dynamicQuery, Class<ResultType> resultTypeClass) {
        return DatabaseFilterManager.getEntityListBySelectableFilterWithReturnTypeAsList(this, dynamicQuery, resultTypeClass);
    }

    @Override
    public <ResultType> Page<ResultType> findAllWithSearchQueryAsPage(DynamicQuery dynamicQuery, Class<ResultType> resultTypeClass) {
        return DatabaseFilterManager.getEntityListBySelectableFilterWithReturnTypeAsPage(this, dynamicQuery, resultTypeClass);
    }

    @Override
    public QueryBuilder<T, ID> query() {
        return new QueryBuilder<>(this);
    }

    @Override
    public Page<T> findAllWithCriteria(List<Criteria> criteriaList, Pageable pageable) {
        return DatabaseFilterManager.findAll(this, criteriaList, pageable);
    }

    static <T> Specification<T> getSpecificationWithCriteria(List<Criteria> criteriaList) {
        return DatabaseFilterManager.getSpecification(criteriaList);
    }

    public Class<T> getDomainClass() {
        return super.getDomainClass();
    }

    @Override
    public long countWithCriteria(List<Criteria> criteriaList) {
        return DatabaseFilterManager.count(this, criteriaList);
    }

    @Override
    public void fetchPartially(ListConsumer<T> processor, int pageSize) {
        fetchPartially(null, processor, pageSize);
    }

    @Override
    public void fetchPartially(Specification<T> specification, ListConsumer<T> processor, int pageSize) {
        Page<T> page = this.findAll((Specification<T>) null, PageRequest.of(0, pageSize));
        processor.accept(page.getContent());
        long totalElements = page.getTotalElements();
        for (int i = 1; (long) i * pageSize < totalElements; i++) {
            page = this.findAll(specification, PageRequest.of(i, pageSize));
            processor.accept(page.getContent());
        }
    }

    @Override
    public void fetchPartiallyWithCriteria(List<Criteria> criteriaList, ListConsumer<T> processor, int pageSize) {
        long totalElements = DatabaseFilterManager.count(this, criteriaList);

        for (int i = 0; (long) i * pageSize < totalElements; i++) {
            processor.accept(DatabaseFilterManager.findAll(this, criteriaList, PageRequest.of(i, pageSize)).getContent());
        }
    }
}
