package org.example.specifications;

import jakarta.persistence.criteria.*;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class FilterSpecifications<T> implements Specification<T> {

    public List<SearchCriteria> criteriaList;

    public FilterSpecifications(List<SearchCriteria> criteriaList){
        this.criteriaList = criteriaList;
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        for (SearchCriteria criteria : criteriaList) {
            if (criteria.getOperation().equalsIgnoreCase(">")) {
                if(root.get(criteria.getKey()).getJavaType() == LocalDateTime.class){
                    final Expression as = root.get(criteria.getKey()).as(LocalDate.class);
                    predicates.add(builder.greaterThanOrEqualTo(as,
                            criteria.getValue().toString().split("T")[0]));
                }else {
                    predicates.add(builder.greaterThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                }
            }else if (criteria.getOperation().equalsIgnoreCase("<")) {
                if(root.get(criteria.getKey()).getJavaType() == LocalDateTime.class){
                    final Expression as = root.get(criteria.getKey()).as(LocalDate.class);
                    predicates.add(builder.lessThanOrEqualTo(as,
                            criteria.getValue().toString().split("T")[0]));
                }else {
                    predicates.add(builder.lessThanOrEqualTo(root.get(criteria.getKey()), criteria.getValue().toString()));
                }
            }else if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (root.get(criteria.getKey()).getJavaType() == String.class) {
                    predicates.add(builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%"));
                } else if(root.get(criteria.getKey()).getJavaType() == LocalDateTime.class){
                    final Expression as = root.get(criteria.getKey()).as(LocalDate.class);
                    predicates.add(builder.equal(as,
                            criteria.getValue().toString().split("T")[0]));
                }else {
                    predicates.add(builder.equal(root.get(criteria.getKey()), criteria.getValue()));
                }
            }
        }
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
