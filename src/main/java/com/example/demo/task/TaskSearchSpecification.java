package com.example.demo.task;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.NotSerializableException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

public class TaskSearchSpecification implements Specification<TaskEntity> {
    private static final long serialVersionUID = -2179946021301597602L;

    private static final String CRITERIA_AND = "and";
    private static final String CRITERIA_OR = "or";
    private static final String SEPARATOR = ":";

    private final List<String> filters;

    public TaskSearchSpecification(@Nullable List<String> filters) {
        this.filters = unmodifiableList(Optional.ofNullable(filters).orElse(emptyList()));
    }

    @Override
    public Predicate toPredicate(Root<TaskEntity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        var criteriaType = getCriteriaType();
        var predicates = getPredicates(root, builder);

        return criteriaType.equalsIgnoreCase(CRITERIA_AND) ? builder.and(predicates) : builder.or(predicates);
    }

    private String getCriteriaType() {
        return filters.stream()
                      .filter(Objects::nonNull)
                      // this can be improved by taking the value from config
                      .filter(f -> f.startsWith("criteria" + SEPARATOR))
                      .map(f -> f.split(SEPARATOR))
                      .filter(f -> f.length >= 2)
                      .map(f -> f[1])
                      .findFirst()
                      .orElse(CRITERIA_AND);
    }

    private Predicate[] getPredicates(Root<TaskEntity> root, CriteriaBuilder builder) {
        return filters.stream()
                      .map(filter -> filter.split(SEPARATOR))
                      .filter(keyValue -> keyValue.length >= 2)
                      .map(keyValue -> predicateFromKeyValueFilter(keyValue, root, builder))
                      .filter(Objects::nonNull)
                      .toArray(Predicate[]::new);
    }

    @Nullable
    private static Predicate predicateFromKeyValueFilter(String[] keyValueFilter, Root<TaskEntity> root,
                                                         CriteriaBuilder builder) {
        var field = keyValueFilter[0];
        var value = keyValueFilter[1];

        switch (field) {
            // instead of mapping directly to the entity field, it'd be better to decouple the actual request param,
            // from the entity field. That's why a DTO was created in the first place, and here the same approach should
            // be implemented as an improvement
            case "completed":
                return builder.equal(root.get(field), Boolean.valueOf(value));
            case "priority":
                return builder.equal(root.get(field), TaskPriority.from(value));
            default:
                // fall through
        }
        return null;
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        throw new NotSerializableException("com.example.demo.task.TaskSearchSpecification");
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        throw new NotSerializableException("com.example.demo.task.TaskSearchSpecification");
    }
}
