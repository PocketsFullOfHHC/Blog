package com.hhc.blogs.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FollowExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FollowExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLikeIdIsNull() {
            addCriterion("like_id is null");
            return (Criteria) this;
        }

        public Criteria andLikeIdIsNotNull() {
            addCriterion("like_id is not null");
            return (Criteria) this;
        }

        public Criteria andLikeIdEqualTo(Long value) {
            addCriterion("like_id =", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdNotEqualTo(Long value) {
            addCriterion("like_id <>", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdGreaterThan(Long value) {
            addCriterion("like_id >", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("like_id >=", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdLessThan(Long value) {
            addCriterion("like_id <", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdLessThanOrEqualTo(Long value) {
            addCriterion("like_id <=", value, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdIn(List<Long> values) {
            addCriterion("like_id in", values, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdNotIn(List<Long> values) {
            addCriterion("like_id not in", values, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdBetween(Long value1, Long value2) {
            addCriterion("like_id between", value1, value2, "likeId");
            return (Criteria) this;
        }

        public Criteria andLikeIdNotBetween(Long value1, Long value2) {
            addCriterion("like_id not between", value1, value2, "likeId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdIsNull() {
            addCriterion("beliked_id is null");
            return (Criteria) this;
        }

        public Criteria andBelikedIdIsNotNull() {
            addCriterion("beliked_id is not null");
            return (Criteria) this;
        }

        public Criteria andBelikedIdEqualTo(Long value) {
            addCriterion("beliked_id =", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdNotEqualTo(Long value) {
            addCriterion("beliked_id <>", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdGreaterThan(Long value) {
            addCriterion("beliked_id >", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdGreaterThanOrEqualTo(Long value) {
            addCriterion("beliked_id >=", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdLessThan(Long value) {
            addCriterion("beliked_id <", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdLessThanOrEqualTo(Long value) {
            addCriterion("beliked_id <=", value, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdIn(List<Long> values) {
            addCriterion("beliked_id in", values, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdNotIn(List<Long> values) {
            addCriterion("beliked_id not in", values, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdBetween(Long value1, Long value2) {
            addCriterion("beliked_id between", value1, value2, "belikedId");
            return (Criteria) this;
        }

        public Criteria andBelikedIdNotBetween(Long value1, Long value2) {
            addCriterion("beliked_id not between", value1, value2, "belikedId");
            return (Criteria) this;
        }

        public Criteria andFollowTimeIsNull() {
            addCriterion("`follow _time` is null");
            return (Criteria) this;
        }

        public Criteria andFollowTimeIsNotNull() {
            addCriterion("`follow _time` is not null");
            return (Criteria) this;
        }

        public Criteria andFollowTimeEqualTo(Date value) {
            addCriterion("`follow _time` =", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeNotEqualTo(Date value) {
            addCriterion("`follow _time` <>", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeGreaterThan(Date value) {
            addCriterion("`follow _time` >", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("`follow _time` >=", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeLessThan(Date value) {
            addCriterion("`follow _time` <", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeLessThanOrEqualTo(Date value) {
            addCriterion("`follow _time` <=", value, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeIn(List<Date> values) {
            addCriterion("`follow _time` in", values, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeNotIn(List<Date> values) {
            addCriterion("`follow _time` not in", values, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeBetween(Date value1, Date value2) {
            addCriterion("`follow _time` between", value1, value2, "followTime");
            return (Criteria) this;
        }

        public Criteria andFollowTimeNotBetween(Date value1, Date value2) {
            addCriterion("`follow _time` not between", value1, value2, "followTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}