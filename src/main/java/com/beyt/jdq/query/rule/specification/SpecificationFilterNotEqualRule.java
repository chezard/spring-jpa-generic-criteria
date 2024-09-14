package com.beyt.jdq.query.rule.specification;

import com.beyt.jdq.dto.Criteria;
import com.beyt.jdq.util.SpecificationUtil;
import lombok.extern.slf4j.Slf4j;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * Created by tdilber at 25-Aug-19
 */
@Slf4j
public class SpecificationFilterNotEqualRule implements ISpecificationFilterRule {

    @Override
    public Predicate generatePredicate(Path<?> root, CriteriaBuilder builder, Criteria criteria) {
        SpecificationUtil.checkHasFirstValue(criteria);
        Predicate[] predicates = new Predicate[criteria.getValues().size()];
        for (int i = 0; i < criteria.getValues().size(); i++) {
            predicates[i] = builder.notEqual(root.get(criteria.getKey()), criteria.getValues().get(i));
        }

        return builder.and(predicates);
    }
}
