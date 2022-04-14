package com.banvien.fc.common.service;

import com.banvien.fc.common.service.filter.Filter;
import com.banvien.fc.common.service.filter.RangeFilter;
import com.banvien.fc.common.service.filter.StringFilter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.TemporalType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.JoinType;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Collection;

public abstract class QueryService<ENTITY> {

    /**
     * Helper function to return a specification for filtering on a single field, where equality, and null/non-null
     * conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X> Specification<ENTITY> buildSpecification(Filter<X> filter, SingularAttribute<? super ENTITY, X>
            field) {
        if (filter.getEquals() != null) {
            return equalsSpecification(field, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(field, filter.getIn());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(field, filter.getSpecified());
        } else if (filter.getNotEquals() != null) {
            return notEqualsSpecification(field,filter.getNotEquals());
        }
        return null;
    }

    /**
     * Helper function to return a specification for filtering on a {@link String} field, where equality, containment,
     * and null/non-null conditions are supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @return a Specification
     */
    protected Specification<ENTITY> buildStringSpecification(StringFilter filter, SingularAttribute<? super ENTITY,
            String> field) {
        if (filter.getEquals() != null) {
            return equalsSpecification(field, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(field, filter.getIn());
        } else if (filter.getContains() != null) {
            if(filter.getLikeType() != null) {
                if(filter.getLikeType().equalsIgnoreCase("left")) {
                    return leftLikeUpperSpecification(field, filter.getContains().trim());
                }else if(filter.getLikeType().equalsIgnoreCase("right")) {
                    return rightLikeUpperSpecification(field, filter.getContains().trim());
                }
            }
            return likeUpperSpecification(field, filter.getContains().trim());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(field, filter.getSpecified());
        }
        return null;
    }

    protected <X> Specification<ENTITY> buildXSpecification(X filter, SingularAttribute<? super ENTITY, X> field) {

        if(filter != null) {
            return equalsSpecification(field, filter);
        }
        return null;
    }

    protected Specification<ENTITY> buildLongSpecification(Long filter, SingularAttribute<? super ENTITY, Long> field) {

        if (filter != null) {
            return equalsSpecification(field, filter);
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildStringReferringEntitySpecification(StringFilter filter,SingularAttribute<? super ENTITY, OTHER> reference,
                                                                            SingularAttribute<OTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeUpperReferringEntitySingleSpecification(reference,valueField,filter.getContains().trim());
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildStringReferringEntitySpecification(StringFilter filter,SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                    JoinType joinType,
                                                                                    SingularAttribute<OTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeUpperReferringEntitySingleSpecification(reference,joinType,valueField,filter.getContains().trim());
        }
        return null;
    }

    protected <OTHER, X> Specification<ENTITY> buildStringTwoReferringEntitySpecification(StringFilter filter,SingularAttribute<? super ENTITY, OTHER> firstReference,
                                                                                    JoinType firstJoinType,
                                                                                    SingularAttribute<? super OTHER, X> secondReference,
                                                                                    JoinType secondJoinType,
                                                                                    SingularAttribute<X, String> valueField) {
        if (filter.getContains() != null) {
            return likeUpperTwoReferringEntitySingleSpecification( firstReference,
                                                                firstJoinType,
                                                                secondReference,
                                                                secondJoinType,
                                                                valueField,
                                                                filter.getContains().trim());
        }
        return null;
    }

    protected <SECOND, THIRD, X> Specification<ENTITY> buildStringThreeReferringEntitySpecification(StringFilter filter,SingularAttribute<? super ENTITY, SECOND> firstReference,
                                                                                            JoinType firstJoinType,
                                                                                            SingularAttribute<? super SECOND, THIRD> secondReference,
                                                                                            JoinType secondJoinType,
                                                                                            SingularAttribute<? super THIRD, X> thirdReference,
                                                                                            JoinType thirdJoinType,
                                                                                            SingularAttribute<X, String> valueField) {
        if (filter.getContains() != null) {
            return likeUpperThreeReferringEntitySingleSpecification(   firstReference,
                                                                    firstJoinType,
                                                                    secondReference,
                                                                    secondJoinType,
                                                                    thirdReference,
                                                                    thirdJoinType,
                                                                    valueField,
                                                                    filter.getContains());
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildStringSetReferringEntitySpecification(StringFilter filter,SetAttribute<ENTITY, OTHER> reference,
                                                                                       SingularAttribute<OTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeSetSpecification(reference,valueField,filter.getContains());
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildStringSetReferringEntitySpecification(StringFilter filter,
                                                                                       SetAttribute<ENTITY, OTHER> reference,
                                                                                       JoinType joinType,
                                                                                       SingularAttribute<OTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeSetSpecification(reference, joinType, valueField,filter.getContains());
        }
        return null;
    }

    protected <OTHER,EOTHER> Specification<ENTITY> buildStringSetReferringTwoEntitySpecification(StringFilter filter,SetAttribute<ENTITY, OTHER> reference,
                                                                                                 SingularAttribute<? super OTHER, EOTHER> oreference,
                                                                                                 SingularAttribute<EOTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeSetSpecification(reference,oreference,valueField,filter.getContains());
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildStringListReferringEntitySpecification(StringFilter filter,ListAttribute<ENTITY, OTHER> reference,
                                                                                       SingularAttribute<OTHER, String> valueField) {
        if (filter.getContains() != null) {
            return likeListSpecification(reference,valueField,filter.getContains());
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildLongSetReferringEntitySpecification(Long filter, SetAttribute<ENTITY, OTHER> reference,
                                                                                      SingularAttribute<OTHER, Long> valueField) {
        if (filter != null) {
            return equalsSetSpecification(reference,valueField,filter);
        }
        return null;
    }

    protected <OTHER> Specification<ENTITY> buildLongSingleReferringEntitySpecification(Long filter, SingularAttribute<ENTITY, OTHER> reference,
                                                                                     SingularAttribute<OTHER, Long> valueField) {
        if (filter != null) {
            return equalsSpecification(reference,valueField,filter);
        }
        return null;
    }
    /**
     * Helper function to return a specification for filtering on a single {@link Comparable}, where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported.
     *
     * @param filter the individual attribute filter coming from the frontend.
     * @param field  the JPA static metamodel representing the field.
     * @param <X>    The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeSpecification(RangeFilter<X> filter,
                                                                                              SingularAttribute<? super ENTITY, X> field) {
        if (filter.getEquals() != null) {
            return equalsSpecification(field, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(field, filter.getIn());
        }

        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(field, filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(field, filter.getGreaterThan()));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(field, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(field, filter.getLessThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(field, filter.getLessOrEqualThan()));
        }
        return result;
    }
    protected <X extends Comparable<? super X>> Specification<ENTITY> buildDateEqualSpecification(Filter<X> filter,Class<X> type,
                                                                                                  SingularAttribute<? super ENTITY, X> field) {
        if (filter.getEquals() != null) {
            return equalsSetSpecification(field,type, filter.getEquals());
        }
        return null;
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> buildRangeDateSpecification(RangeFilter<X> filter,Class<X> type,
                                                                                              SingularAttribute<? super ENTITY, X> field) {
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(field,type, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(field,type, filter.getLessOrEqualThan()));
        }
        return result;
    }

    protected  <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildRangeDateReferringEntitySpecification(RangeFilter<X> filter,Class<X> type,
                                 final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> field) {
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(reference,type, field,filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(reference,type, field,filter.getLessOrEqualThan()));
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on one-to-one or many-to-one reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByProjectId = buildReferringEntitySpecification(criteria.getProjectId(),
     * Employee_.project, Project_.id);
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(criteria.getProjectName(),
     * Employee_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if nullness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                 SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                 SingularAttribute<OTHER, X> valueField) {
        if (filter.getEquals() != null) {
            return equalsSpecification(reference, valueField, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(reference, valueField, filter.getIn());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(reference, filter.getSpecified());
        }
        return null;
    }
    protected <OTHER,EOTHER, X> Specification<ENTITY> buildTwoReferringEntitySpecification(Filter<X> filter,SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                             SingularAttribute<? super OTHER, EOTHER> oReference) {
        if (filter.getSpecified() != null) {
            return byFieldSpecified(reference,oReference, filter.getSpecified());
        }
        return null;
    }
    /**
     * Helper function to return a specification for filtering on one-to-one or many-to-one reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByProjectId = buildReferringEntitySpecification(criteria.getProjectId(),
     * Employee_.project, Project_.id);
     *   Specification&lt;Employee&gt; specByProjectName = buildReferringEntitySpecification(criteria.getProjectName(),
     * Employee_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if nullness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(final RangeFilter<X> filter,
                                                                                                               final SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                               final SingularAttribute<OTHER, X> valueField) {
        if (filter.getEquals() != null) {
            return equalsSpecification(reference, valueField, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(reference, valueField, filter.getIn());
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(reference, filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(reference, valueField, filter.getGreaterThan()));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(reference, valueField, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(reference, valueField, filter.getLessThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(reference, valueField, filter.getLessOrEqualThan()));
        }
        return result;
    }

    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X> Specification<ENTITY> buildReferringEntitySpecification(Filter<X> filter,
                                                                                 SetAttribute<ENTITY, OTHER> reference,
                                                                                 SingularAttribute<OTHER, X> valueField) {
        if (filter.getEquals() != null) {
            return equalsSetSpecification(reference, valueField, filter.getEquals());
        } else if (filter.getSpecified() != null) {
            return byFieldSpecified(reference, filter.getSpecified());
        }
        return null;
    }

    protected <OTHER,TOWOTHER, X> Specification<ENTITY> buildReferringEntityTwoSpecification(Filter<X> filter,SingularAttribute<? super ENTITY, OTHER> reference,
                                                                               SingularAttribute<? super OTHER, TOWOTHER> oReference,
                                                                               SingularAttribute<TOWOTHER, X> idField) {
        if (filter.getEquals() != null) {
            return equalsSetSpecification(reference, oReference,idField, filter.getEquals());
        }
        return null;
    }

    protected <OTHER, TOWOTHER, X> Specification<ENTITY> buildSetReferringEntityTwoSpecification(X filter,
                                                                                                 SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                 SetAttribute<? super OTHER, TOWOTHER> oReference,
                                                                                                 SingularAttribute<TOWOTHER, X> idField) {
        if (filter != null) {
            return equalsSetSpecification(reference, oReference,idField, filter);
        }
        return null;
    }
    /**
     * Helper function to return a specification for filtering on one-to-many or many-to-many reference. Where equality, less
     * than, greater than and less-than-or-equal-to and greater-than-or-equal-to and null/non-null conditions are
     * supported. Usage:
     * <pre>
     *   Specification&lt;Employee&gt; specByEmployeeId = buildReferringEntitySpecification(criteria.getEmployeId(),
     * Project_.employees, Employee_.id);
     *   Specification&lt;Employee&gt; specByEmployeeName = buildReferringEntitySpecification(criteria.getEmployeName(),
     * Project_.project, Project_.name);
     * </pre>
     *
     * @param filter     the filter object which contains a value, which needs to match or a flag if emptiness is
     *                   checked.
     * @param reference  the attribute of the static metamodel for the referring entity.
     * @param valueField the attribute of the static metamodel of the referred entity, where the equality should be
     *                   checked.
     * @param <OTHER>    The type of the referenced entity.
     * @param <X>        The type of the attribute which is filtered.
     * @return a Specification
     */
    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> buildReferringEntitySpecification(final RangeFilter<X> filter,
                                                                                                               final SetAttribute<ENTITY, OTHER> reference,
                                                                                                               final SingularAttribute<OTHER, X> valueField) {
        if (filter.getEquals() != null) {
            return equalsSetSpecification(reference, valueField, filter.getEquals());
        } else if (filter.getIn() != null) {
            return valueIn(reference, valueField, filter.getIn());
        }
        Specification<ENTITY> result = Specification.where(null);
        if (filter.getSpecified() != null) {
            result = result.and(byFieldSpecified(reference, filter.getSpecified()));
        }
        if (filter.getGreaterThan() != null) {
            result = result.and(greaterThan(reference, valueField, filter.getGreaterThan()));
        }
        if (filter.getGreaterOrEqualThan() != null) {
            result = result.and(greaterThanOrEqualTo(reference, valueField, filter.getGreaterOrEqualThan()));
        }
        if (filter.getLessThan() != null) {
            result = result.and(lessThan(reference, valueField, filter.getLessThan()));
        }
        if (filter.getLessOrEqualThan() != null) {
            result = result.and(lessThanOrEqualTo(reference, valueField, filter.getLessOrEqualThan()));
        }
        return result;
    }

    protected <X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, X> field, final X value) {
        return (root, query, builder) -> builder.equal(root.get(field), value);
    }

    protected <X> Specification<ENTITY> notEqualsSpecification(SingularAttribute<? super ENTITY, X> field, final X value) {
        return (root, query, builder) -> builder.notEqual(root.get(field), value);
    }

    protected <OTHER, X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, OTHER>
                                                                           reference, SingularAttribute<OTHER, X> idField,
                                                                   X value) {
        return (root, query, builder) -> builder.equal(root.get(reference).get(idField), value);
    }

    protected <OTHER,ANOTHER,LASTOTHER, X> Specification<ENTITY> equalsSpecification(SingularAttribute<? super ENTITY, OTHER>
                                                                           first,SingularAttribute<? super OTHER, ANOTHER>
            second,SingularAttribute<? super ANOTHER, LASTOTHER>
            three, SingularAttribute<LASTOTHER, X> idField,
                                                                   X value) {
        return (root, query, builder) -> builder.equal(root.get(first).get(second).get(three).get(idField), value);
    }

    protected <OTHER,EOTHER, X> Specification<ENTITY> equalsSpecification(SingularAttribute<ENTITY, X> idFirstField,SingularAttribute<? super ENTITY, OTHER>
                                                                           reference,SingularAttribute<? super OTHER, EOTHER>
            oReference, SingularAttribute<EOTHER, X> idSecondField) {
        return (root, query, builder) -> builder.equal(root.get(idFirstField), root.get(reference).get(oReference).get(idSecondField));
    }

    protected <OTHER,ANOTHER, X> Specification<ENTITY> notEqualsSpecification(SingularAttribute<? super ENTITY, OTHER>
                                                                                             first,SingularAttribute<? super OTHER, ANOTHER>
                                                                                             second, SingularAttribute<ANOTHER, X> idField,
                                                                                     X value) {
        return (root, query, builder) -> builder.notEqual(root.get(first).get(second).get(idField), value);
    }

    protected <OTHER, X> Specification<ENTITY> notEqualsSpecification(SingularAttribute<? super ENTITY, OTHER>
                                                                           reference, SingularAttribute<OTHER, X> idField,
                                                                   X value) {
        return (root, query, builder) -> builder.notEqual(root.get(reference).get(idField), value);
    }

    protected <OTHER, X> Specification<ENTITY> equalsSetSpecification(SetAttribute<? super ENTITY, OTHER> reference,
                                                                      SingularAttribute<OTHER, X> idField,
                                                                      X value) {
        return (root, query, builder) -> builder.equal(root.join(reference).get(idField), value);
    }

    protected <OTHER> Specification<ENTITY> likeSetSpecification(SetAttribute<? super ENTITY, OTHER> reference,
                                                                 SingularAttribute<OTHER, String> idField,
                                                                 String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference).get(idField)), wrapLikeQuery(value));
    }

    protected <OTHER> Specification<ENTITY> likeSetSpecification(SetAttribute<? super ENTITY, OTHER> reference,
                                                                 JoinType joinType,
                                                                 SingularAttribute<OTHER, String> idField,
                                                                 String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference, joinType != null ? joinType : JoinType.INNER ).get(idField)), wrapLikeQuery(value));
    }

    protected <OTHER,EOTHER> Specification<ENTITY> likeSetSpecification(SetAttribute<? super ENTITY, OTHER> reference,
                                                                 SingularAttribute<? super OTHER, EOTHER>
                                                                         oreference,
                                                                 SingularAttribute<EOTHER, String> idField,
                                                                 String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference).join(oreference).get(idField)), wrapLikeQuery(value));
    }

    protected <OTHER> Specification<ENTITY> likeListSpecification(ListAttribute<? super ENTITY, OTHER> reference,
                                                                 SingularAttribute<OTHER, String> idField,
                                                                 String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference).get(idField)), wrapLikeQuery(value));
    }

    protected <OTHER,EOTHER, X> Specification<ENTITY> equalsSetSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                      SingularAttribute<? super OTHER, EOTHER> oReference,
                                                                      SingularAttribute<EOTHER, X> idField,
                                                                      X value) {
        return (root, query, builder) -> builder.equal(root.join(reference).join(oReference).get(idField), value);
    }

    protected <OTHER,EOTHER, X> Specification<ENTITY> equalsSetSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                             SetAttribute<? super OTHER, EOTHER> oReference,
                                                                             SingularAttribute<EOTHER, X> idField,
                                                                             X value) {
        return (root, query, builder) -> builder.equal(root.join(reference).join(oReference).get(idField), value);
    }

    protected <OTHER,EOTHER> Specification<ENTITY> likeUpperSetSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                               SingularAttribute<? super OTHER, EOTHER> oReference,
                                                                               SingularAttribute<EOTHER, String> idField,
                                                                                  String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference).join(oReference).get(idField)), wrapLikeQuery(value));
    }
    protected <OTHER,EOTHER> Specification<ENTITY> notLikeUpperSetSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                               SingularAttribute<? super OTHER, EOTHER> towReference,
                                                                               SingularAttribute<EOTHER, String> idField,
                                                                               String value) {
        return (root, query, builder) -> builder.notLike(builder.upper(root.join(reference).join(towReference).get(idField)), wrapLikeQuery(value));
    }

    protected <OTHER> Specification<ENTITY> likeUpperReferringEntitySingleSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                        SingularAttribute<OTHER, String> idField,
                                                                                        String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference).get(idField)), wrapLikeQuery(value));

    }

    protected <OTHER> Specification<ENTITY> likeUpperReferringEntitySingleSpecification(SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                        JoinType joinType,
                                                                                        SingularAttribute<OTHER, String> idField,
                                                                                        String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(reference, joinType != null ? joinType : JoinType.INNER).get(idField)), wrapLikeQuery(value));

    }

    protected <OTHER, X> Specification<ENTITY> likeUpperTwoReferringEntitySingleSpecification(SingularAttribute<? super ENTITY, OTHER> firstReference,
                                                                                              JoinType firstJoinType,
                                                                                              SingularAttribute<? super OTHER, X> secondReference,
                                                                                              JoinType secondJoinType,
                                                                                              SingularAttribute<X, String> idField,
                                                                                              String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(firstReference, firstJoinType != null ? firstJoinType : JoinType.INNER)
                                                                        .join(secondReference, secondJoinType != null ? secondJoinType : JoinType.INNER).get(idField)),
                                                                        wrapLikeQuery(value));

    }

    protected <SECOND, THIRD, X> Specification<ENTITY> likeUpperThreeReferringEntitySingleSpecification(SingularAttribute<? super ENTITY, SECOND> firstReference,
                                                                                                        JoinType firstJoinType,
                                                                                                        SingularAttribute<? super SECOND, THIRD> secondReference,
                                                                                                        JoinType secondJoinType,
                                                                                                        SingularAttribute<? super THIRD, X> thirdReference,
                                                                                                        JoinType thirdJoinType,
                                                                                                        SingularAttribute<X, String> idField,
                                                                                                        String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.join(firstReference, firstJoinType != null ? firstJoinType : JoinType.INNER)
                                                                        .join(secondReference, secondJoinType != null ? secondJoinType : JoinType.INNER)
                                                                        .join(thirdReference, thirdJoinType != null ? thirdJoinType : JoinType.INNER).get(idField)),
                wrapLikeQuery(value));

    }

    protected Specification<ENTITY> likeUpperSpecification(SingularAttribute<? super ENTITY, String> field, final
    String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.get(field)), wrapLikeQuery(value));
    }

    protected Specification<ENTITY> leftLikeUpperSpecification(SingularAttribute<? super ENTITY, String> field, final
    String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.get(field)), wrapLeftLikeQuery(value));
    }

    protected Specification<ENTITY> rightLikeUpperSpecification(SingularAttribute<? super ENTITY, String> field, final
    String value) {
        return (root, query, builder) -> builder.like(builder.upper(root.get(field)), wrapRightLikeQuery(value));
    }

    protected <X> Specification<ENTITY> byFieldSpecified(SingularAttribute<? super ENTITY, X> field, final boolean
            specified) {
        return specified ? (root, query, builder) -> builder.isNotNull(root.get(field)) : (root, query, builder) ->
                builder.isNull(root.get(field));
    }

    protected <ORTHER,X> Specification<ENTITY> byFieldSpecified(SingularAttribute<? super ENTITY, ORTHER> field,SingularAttribute<? super ORTHER, X> ofield, final boolean
            specified) {
        return specified ? (root, query, builder) -> builder.isNotNull(root.get(field).get(ofield)) : (root, query, builder) ->
                builder.isNull(root.get(field).get(ofield));
    }

    protected <X> Specification<ENTITY> byFieldSpecified(SetAttribute<ENTITY, X> field, final boolean specified) {
        return specified ? (root, query, builder) -> builder.isNotEmpty(root.get(field)) : (root, query, builder) ->
                builder.isEmpty(root.get(field));
    }

    protected <X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, X> field, final Collection<X>
            values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.get(field));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }
    protected <X> Specification<ENTITY> valueNotIn(SingularAttribute<? super ENTITY, X> field, final Collection<X>
            values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.get(field));
            for (X value : values) {
                in = in.value(value);
            }
            return in.not();
        };
    }

    protected <X> Specification<ENTITY> equalsSetSpecification(SingularAttribute<? super ENTITY, X> field,Class<X> type,final X value){
        return (root, query, builder)
                -> builder.equal(builder.function(TemporalType.DATE.name(),type,root.get(field)),
                value);
    }

    protected <X extends Comparable<? super X>>  Specification<ENTITY> greaterThanOrEqualTo(SingularAttribute<? super ENTITY, X> field,Class<X> type,final X value){
        return (root, query, builder)
                -> builder.greaterThanOrEqualTo(builder.function(TemporalType.DATE.name(),type,root.get(field)),
                value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SingularAttribute<? super ENTITY, X> field,Class<X> type,final X value){
        return (root, query, builder)
                -> builder.lessThanOrEqualTo(builder.function(TemporalType.DATE.name(),type,root.get(field)),
                value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                                  Class<X> type, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(builder.function(TemporalType.DATE.name(),type,root.get(reference).get(valueField)), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference,
                                                                                               Class<X> type, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(builder.function(TemporalType.DATE.name(),type,root.get(reference).get(valueField)),value);
    }

    protected <OTHER, X> Specification<ENTITY> valueIn(SingularAttribute<? super ENTITY, OTHER> reference,
                                                       SingularAttribute<OTHER, X> valueField, final Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.get(reference).get(valueField));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(SingularAttribute<? super
            ENTITY, X> field, final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(field), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> greaterThan(SingularAttribute<? super ENTITY,
            X> field, final X value) {
        return (root, query, builder) -> builder.greaterThan(root.get(field), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(SingularAttribute<? super
            ENTITY, X> field, final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(field), value);
    }

    protected <X extends Comparable<? super X>> Specification<ENTITY> lessThan(SingularAttribute<? super ENTITY, X>
                                                                                       field, final X value) {
        return (root, query, builder) -> builder.lessThan(root.get(field), value);
    }

    protected String wrapLikeQuery(String txt) {
        return "%" + txt.toUpperCase() + '%';
    }

    protected String wrapLeftLikeQuery(String txt) {
        return txt.toUpperCase() + '%';
    }

    protected String wrapRightLikeQuery(String txt) {
        return "%" + txt.toUpperCase();
    }

    protected <OTHER, X> Specification<ENTITY> valueIn(final SetAttribute<? super ENTITY, OTHER> reference,
                                                       final SingularAttribute<OTHER, X> valueField, final Collection<X> values) {
        return (root, query, builder) -> {
            CriteriaBuilder.In<X> in = builder.in(root.join(reference).get(valueField));
            for (X value : values) {
                in = in.value(value);
            }
            return in;
        };
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.greaterThan(root.get(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThan(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.greaterThan(root.join(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> greaterThanOrEqualTo(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.join(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.lessThan(root.get(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThan(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.lessThan(root.join(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(final SingularAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(reference).get(valueField), value);
    }

    protected <OTHER, X extends Comparable<? super X>> Specification<ENTITY> lessThanOrEqualTo(final SetAttribute<? super ENTITY, OTHER> reference, final SingularAttribute<OTHER, X> valueField, final X value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.join(reference).get(valueField), value);
    }
}
