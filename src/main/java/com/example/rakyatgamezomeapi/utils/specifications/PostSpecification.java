package com.example.rakyatgamezomeapi.utils.specifications;

import com.example.rakyatgamezomeapi.model.dto.response.PostResponse;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PostSpecification {
    public static Specification<PostResponse> getSpecification(String q){
        return (root, cq, cb) -> {
            if(!StringUtils.hasText(q)) return cb.conjunction();

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.like(cb.lower(root.get("title")), "%" + q.toLowerCase() + "%"));
            predicates.add(cb.like(cb.lower(root.get("body")), "%" + q.toLowerCase() + "%"));

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }
}
