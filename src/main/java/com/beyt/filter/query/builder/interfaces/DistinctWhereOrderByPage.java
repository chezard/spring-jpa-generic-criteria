package com.beyt.filter.query.builder.interfaces;

import com.beyt.dto.Criteria;
import com.beyt.filter.query.simplifier.QuerySimplifier;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DistinctWhereOrderByPage<T, ID> {
    WhereOrderByPage<T, ID> distinct(boolean distinct);

    OrderByPage<T, ID> where(Criteria... criteria);

    PageableResult<T, ID> orderBy(QuerySimplifier.OrderByRule... pairs);

    Result<T, ID> page(int pageNumber, int pageSize);

    List<T> getResult();

    Page<T> getResultAsPage();

    <ResultValue> List<ResultValue> getResult(Class<ResultValue> resultValueClass);

    <ResultValue> Page<ResultValue> getResultAsPage(Class<ResultValue> resultValueClass);
}
