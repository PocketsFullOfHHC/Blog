package com.hhc.blogs.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BlogSnapshotExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BlogSnapshotExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andBlogIdIsNull() {
            addCriterion("blog_id is null");
            return (Criteria) this;
        }

        public Criteria andBlogIdIsNotNull() {
            addCriterion("blog_id is not null");
            return (Criteria) this;
        }

        public Criteria andBlogIdEqualTo(Long value) {
            addCriterion("blog_id =", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotEqualTo(Long value) {
            addCriterion("blog_id <>", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdGreaterThan(Long value) {
            addCriterion("blog_id >", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdGreaterThanOrEqualTo(Long value) {
            addCriterion("blog_id >=", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdLessThan(Long value) {
            addCriterion("blog_id <", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdLessThanOrEqualTo(Long value) {
            addCriterion("blog_id <=", value, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdIn(List<Long> values) {
            addCriterion("blog_id in", values, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotIn(List<Long> values) {
            addCriterion("blog_id not in", values, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdBetween(Long value1, Long value2) {
            addCriterion("blog_id between", value1, value2, "blogId");
            return (Criteria) this;
        }

        public Criteria andBlogIdNotBetween(Long value1, Long value2) {
            addCriterion("blog_id not between", value1, value2, "blogId");
            return (Criteria) this;
        }

        public Criteria andDateIsNull() {
            addCriterion("`date` is null");
            return (Criteria) this;
        }

        public Criteria andDateIsNotNull() {
            addCriterion("`date` is not null");
            return (Criteria) this;
        }

        public Criteria andDateEqualTo(Date value) {
            addCriterionForJDBCDate("`date` =", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("`date` <>", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThan(Date value) {
            addCriterionForJDBCDate("`date` >", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("`date` >=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThan(Date value) {
            addCriterionForJDBCDate("`date` <", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("`date` <=", value, "date");
            return (Criteria) this;
        }

        public Criteria andDateIn(List<Date> values) {
            addCriterionForJDBCDate("`date` in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("`date` not in", values, "date");
            return (Criteria) this;
        }

        public Criteria andDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("`date` between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("`date` not between", value1, value2, "date");
            return (Criteria) this;
        }

        public Criteria andVoteNumIsNull() {
            addCriterion("vote_num is null");
            return (Criteria) this;
        }

        public Criteria andVoteNumIsNotNull() {
            addCriterion("vote_num is not null");
            return (Criteria) this;
        }

        public Criteria andVoteNumEqualTo(Integer value) {
            addCriterion("vote_num =", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotEqualTo(Integer value) {
            addCriterion("vote_num <>", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumGreaterThan(Integer value) {
            addCriterion("vote_num >", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_num >=", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumLessThan(Integer value) {
            addCriterion("vote_num <", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumLessThanOrEqualTo(Integer value) {
            addCriterion("vote_num <=", value, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumIn(List<Integer> values) {
            addCriterion("vote_num in", values, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotIn(List<Integer> values) {
            addCriterion("vote_num not in", values, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumBetween(Integer value1, Integer value2) {
            addCriterion("vote_num between", value1, value2, "voteNum");
            return (Criteria) this;
        }

        public Criteria andVoteNumNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_num not between", value1, value2, "voteNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumIsNull() {
            addCriterion("comment_num is null");
            return (Criteria) this;
        }

        public Criteria andCommentNumIsNotNull() {
            addCriterion("comment_num is not null");
            return (Criteria) this;
        }

        public Criteria andCommentNumEqualTo(Integer value) {
            addCriterion("comment_num =", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumNotEqualTo(Integer value) {
            addCriterion("comment_num <>", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumGreaterThan(Integer value) {
            addCriterion("comment_num >", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_num >=", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumLessThan(Integer value) {
            addCriterion("comment_num <", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumLessThanOrEqualTo(Integer value) {
            addCriterion("comment_num <=", value, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumIn(List<Integer> values) {
            addCriterion("comment_num in", values, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumNotIn(List<Integer> values) {
            addCriterion("comment_num not in", values, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumBetween(Integer value1, Integer value2) {
            addCriterion("comment_num between", value1, value2, "commentNum");
            return (Criteria) this;
        }

        public Criteria andCommentNumNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_num not between", value1, value2, "commentNum");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseIsNull() {
            addCriterion("vote_increase is null");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseIsNotNull() {
            addCriterion("vote_increase is not null");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseEqualTo(Integer value) {
            addCriterion("vote_increase =", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseNotEqualTo(Integer value) {
            addCriterion("vote_increase <>", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseGreaterThan(Integer value) {
            addCriterion("vote_increase >", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseGreaterThanOrEqualTo(Integer value) {
            addCriterion("vote_increase >=", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseLessThan(Integer value) {
            addCriterion("vote_increase <", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseLessThanOrEqualTo(Integer value) {
            addCriterion("vote_increase <=", value, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseIn(List<Integer> values) {
            addCriterion("vote_increase in", values, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseNotIn(List<Integer> values) {
            addCriterion("vote_increase not in", values, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseBetween(Integer value1, Integer value2) {
            addCriterion("vote_increase between", value1, value2, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andVoteIncreaseNotBetween(Integer value1, Integer value2) {
            addCriterion("vote_increase not between", value1, value2, "voteIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseIsNull() {
            addCriterion("comment_increase is null");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseIsNotNull() {
            addCriterion("comment_increase is not null");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseEqualTo(Integer value) {
            addCriterion("comment_increase =", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseNotEqualTo(Integer value) {
            addCriterion("comment_increase <>", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseGreaterThan(Integer value) {
            addCriterion("comment_increase >", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_increase >=", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseLessThan(Integer value) {
            addCriterion("comment_increase <", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseLessThanOrEqualTo(Integer value) {
            addCriterion("comment_increase <=", value, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseIn(List<Integer> values) {
            addCriterion("comment_increase in", values, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseNotIn(List<Integer> values) {
            addCriterion("comment_increase not in", values, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseBetween(Integer value1, Integer value2) {
            addCriterion("comment_increase between", value1, value2, "commentIncrease");
            return (Criteria) this;
        }

        public Criteria andCommentIncreaseNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_increase not between", value1, value2, "commentIncrease");
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