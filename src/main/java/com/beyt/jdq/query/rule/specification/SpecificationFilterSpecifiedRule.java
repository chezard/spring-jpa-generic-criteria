package com.beyt.jdq.query.rule.specification;

import com.beyt.jdq.dto.Criteria;
import com.beyt.jdq.exception.DynamicQueryNoAvailableValueException;
import com.beyt.jdq.util.SpecificationUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

/**
 * Created by tdilber at 25-Aug-19
 */
@Slf4j
public class SpecificationFilterSpecifiedRule implements ISpecificationFilterRule {

    @Override
    public Predicate generatePredicate(Path<?> root, CriteriaBuilder builder, Criteria criteria) {
        SpecificationUtil.checkHasFirstValue(criteria);
        if (!criteria.getValues().get(0).toString().equalsIgnoreCase("true") && !criteria.getValues().get(0).toString().equalsIgnoreCase("false")) {
            throw new DynamicQueryNoAvailableValueException("Specified rule first value must be true or false. But you send " + criteria.getValues().get(0).toString());
        }

        return criteria.getValues().get(0).toString().equalsIgnoreCase("true") ? builder.isNotNull(root.get(criteria.getKey())) : builder.isNull(root.get(criteria.getKey()));
    }
}
