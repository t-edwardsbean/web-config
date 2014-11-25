package com.baidu.model;

import java.util.ArrayList;
import java.util.List;

public class TestConfigExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestConfigExample() {
        oredCriteria = new ArrayList<Criteria>();
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
            criteria = new ArrayList<Criterion>();
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

        public Criteria andConfigIdIsNull() {
            addCriterion("config_id is null");
            return (Criteria) this;
        }

        public Criteria andConfigIdIsNotNull() {
            addCriterion("config_id is not null");
            return (Criteria) this;
        }

        public Criteria andConfigIdEqualTo(Integer value) {
            addCriterion("config_id =", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotEqualTo(Integer value) {
            addCriterion("config_id <>", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThan(Integer value) {
            addCriterion("config_id >", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("config_id >=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThan(Integer value) {
            addCriterion("config_id <", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdLessThanOrEqualTo(Integer value) {
            addCriterion("config_id <=", value, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdIn(List<Integer> values) {
            addCriterion("config_id in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotIn(List<Integer> values) {
            addCriterion("config_id not in", values, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdBetween(Integer value1, Integer value2) {
            addCriterion("config_id between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andConfigIdNotBetween(Integer value1, Integer value2) {
            addCriterion("config_id not between", value1, value2, "configId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNull() {
            addCriterion("service_id is null");
            return (Criteria) this;
        }

        public Criteria andServiceIdIsNotNull() {
            addCriterion("service_id is not null");
            return (Criteria) this;
        }

        public Criteria andServiceIdEqualTo(String value) {
            addCriterion("service_id =", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotEqualTo(String value) {
            addCriterion("service_id <>", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThan(String value) {
            addCriterion("service_id >", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdGreaterThanOrEqualTo(String value) {
            addCriterion("service_id >=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThan(String value) {
            addCriterion("service_id <", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLessThanOrEqualTo(String value) {
            addCriterion("service_id <=", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdLike(String value) {
            addCriterion("service_id like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotLike(String value) {
            addCriterion("service_id not like", value, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdIn(List<String> values) {
            addCriterion("service_id in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotIn(List<String> values) {
            addCriterion("service_id not in", values, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdBetween(String value1, String value2) {
            addCriterion("service_id between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceIdNotBetween(String value1, String value2) {
            addCriterion("service_id not between", value1, value2, "serviceId");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNull() {
            addCriterion("service_name is null");
            return (Criteria) this;
        }

        public Criteria andServiceNameIsNotNull() {
            addCriterion("service_name is not null");
            return (Criteria) this;
        }

        public Criteria andServiceNameEqualTo(String value) {
            addCriterion("service_name =", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotEqualTo(String value) {
            addCriterion("service_name <>", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThan(String value) {
            addCriterion("service_name >", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameGreaterThanOrEqualTo(String value) {
            addCriterion("service_name >=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThan(String value) {
            addCriterion("service_name <", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLessThanOrEqualTo(String value) {
            addCriterion("service_name <=", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameLike(String value) {
            addCriterion("service_name like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotLike(String value) {
            addCriterion("service_name not like", value, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameIn(List<String> values) {
            addCriterion("service_name in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotIn(List<String> values) {
            addCriterion("service_name not in", values, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameBetween(String value1, String value2) {
            addCriterion("service_name between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andServiceNameNotBetween(String value1, String value2) {
            addCriterion("service_name not between", value1, value2, "serviceName");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNull() {
            addCriterion("group_name is null");
            return (Criteria) this;
        }

        public Criteria andGroupNameIsNotNull() {
            addCriterion("group_name is not null");
            return (Criteria) this;
        }

        public Criteria andGroupNameEqualTo(String value) {
            addCriterion("group_name =", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotEqualTo(String value) {
            addCriterion("group_name <>", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThan(String value) {
            addCriterion("group_name >", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameGreaterThanOrEqualTo(String value) {
            addCriterion("group_name >=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThan(String value) {
            addCriterion("group_name <", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLessThanOrEqualTo(String value) {
            addCriterion("group_name <=", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameLike(String value) {
            addCriterion("group_name like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotLike(String value) {
            addCriterion("group_name not like", value, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameIn(List<String> values) {
            addCriterion("group_name in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotIn(List<String> values) {
            addCriterion("group_name not in", values, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameBetween(String value1, String value2) {
            addCriterion("group_name between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andGroupNameNotBetween(String value1, String value2) {
            addCriterion("group_name not between", value1, value2, "groupName");
            return (Criteria) this;
        }

        public Criteria andRequestDelayIsNull() {
            addCriterion("request_delay is null");
            return (Criteria) this;
        }

        public Criteria andRequestDelayIsNotNull() {
            addCriterion("request_delay is not null");
            return (Criteria) this;
        }

        public Criteria andRequestDelayEqualTo(Integer value) {
            addCriterion("request_delay =", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayNotEqualTo(Integer value) {
            addCriterion("request_delay <>", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayGreaterThan(Integer value) {
            addCriterion("request_delay >", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayGreaterThanOrEqualTo(Integer value) {
            addCriterion("request_delay >=", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayLessThan(Integer value) {
            addCriterion("request_delay <", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayLessThanOrEqualTo(Integer value) {
            addCriterion("request_delay <=", value, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayIn(List<Integer> values) {
            addCriterion("request_delay in", values, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayNotIn(List<Integer> values) {
            addCriterion("request_delay not in", values, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayBetween(Integer value1, Integer value2) {
            addCriterion("request_delay between", value1, value2, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andRequestDelayNotBetween(Integer value1, Integer value2) {
            addCriterion("request_delay not between", value1, value2, "requestDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayIsNull() {
            addCriterion("fail_delay is null");
            return (Criteria) this;
        }

        public Criteria andFailDelayIsNotNull() {
            addCriterion("fail_delay is not null");
            return (Criteria) this;
        }

        public Criteria andFailDelayEqualTo(Integer value) {
            addCriterion("fail_delay =", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayNotEqualTo(Integer value) {
            addCriterion("fail_delay <>", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayGreaterThan(Integer value) {
            addCriterion("fail_delay >", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayGreaterThanOrEqualTo(Integer value) {
            addCriterion("fail_delay >=", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayLessThan(Integer value) {
            addCriterion("fail_delay <", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayLessThanOrEqualTo(Integer value) {
            addCriterion("fail_delay <=", value, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayIn(List<Integer> values) {
            addCriterion("fail_delay in", values, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayNotIn(List<Integer> values) {
            addCriterion("fail_delay not in", values, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayBetween(Integer value1, Integer value2) {
            addCriterion("fail_delay between", value1, value2, "failDelay");
            return (Criteria) this;
        }

        public Criteria andFailDelayNotBetween(Integer value1, Integer value2) {
            addCriterion("fail_delay not between", value1, value2, "failDelay");
            return (Criteria) this;
        }

        public Criteria andServerNumIsNull() {
            addCriterion("server_num is null");
            return (Criteria) this;
        }

        public Criteria andServerNumIsNotNull() {
            addCriterion("server_num is not null");
            return (Criteria) this;
        }

        public Criteria andServerNumEqualTo(Integer value) {
            addCriterion("server_num =", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumNotEqualTo(Integer value) {
            addCriterion("server_num <>", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumGreaterThan(Integer value) {
            addCriterion("server_num >", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("server_num >=", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumLessThan(Integer value) {
            addCriterion("server_num <", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumLessThanOrEqualTo(Integer value) {
            addCriterion("server_num <=", value, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumIn(List<Integer> values) {
            addCriterion("server_num in", values, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumNotIn(List<Integer> values) {
            addCriterion("server_num not in", values, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumBetween(Integer value1, Integer value2) {
            addCriterion("server_num between", value1, value2, "serverNum");
            return (Criteria) this;
        }

        public Criteria andServerNumNotBetween(Integer value1, Integer value2) {
            addCriterion("server_num not between", value1, value2, "serverNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumIsNull() {
            addCriterion("thread_num is null");
            return (Criteria) this;
        }

        public Criteria andThreadNumIsNotNull() {
            addCriterion("thread_num is not null");
            return (Criteria) this;
        }

        public Criteria andThreadNumEqualTo(Integer value) {
            addCriterion("thread_num =", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumNotEqualTo(Integer value) {
            addCriterion("thread_num <>", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumGreaterThan(Integer value) {
            addCriterion("thread_num >", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("thread_num >=", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumLessThan(Integer value) {
            addCriterion("thread_num <", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumLessThanOrEqualTo(Integer value) {
            addCriterion("thread_num <=", value, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumIn(List<Integer> values) {
            addCriterion("thread_num in", values, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumNotIn(List<Integer> values) {
            addCriterion("thread_num not in", values, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumBetween(Integer value1, Integer value2) {
            addCriterion("thread_num between", value1, value2, "threadNum");
            return (Criteria) this;
        }

        public Criteria andThreadNumNotBetween(Integer value1, Integer value2) {
            addCriterion("thread_num not between", value1, value2, "threadNum");
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