package com.jzwy.zkx.core.service.contract;

import com.jzwy.zkx.core.support.query.PageRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 抽象的查询请求
 */
public abstract class BaseQuery extends PageRequest implements Serializable {

    private static final long serialVersionUID = -6498929763781667617L;

    private Long id;
    private long creatorId;
    private String creatorName;
    private Date createdTime;
    private long lastModifierId;
    private String lastModifierName;
    private Date lastModifiedTime;
    private String lastModifiedIP;
    private Integer isDeleted;

    private List<OrderByEntry> orderByFields;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public String getLastModifierName() {
        return lastModifierName;
    }

    public void setLastModifierName(String lastModifierName) {
        this.lastModifierName = lastModifierName;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedIP() {
        return lastModifiedIP;
    }

    public void setLastModifiedIP(String lastModifiedIP) {
        this.lastModifiedIP = lastModifiedIP;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<OrderByEntry> getOrderByFields() {
        return orderByFields;
    }

    public <T extends BaseQuery> T orderBy(String field, OrderType orderType) {
        if (StringUtils.isEmpty(field) || orderType == null) {
            return (T) this;
        }

        if (orderByFields == null) {
            orderByFields = new ArrayList<>();
        }
        orderByFields.add(new OrderByEntry(field, orderType));
        return (T) this;
    }

    public enum OrderType {

        Asc(10, "asc", "升序"),
        Desc(20, "desc", "降序");

        private Integer index;
        private String code;
        private String name;

        OrderType(Integer index, String code, String name) {
            this.index = index;
            this.code = code;
            this.name = name;
        }

        public Integer getIndex() {
            return this.index;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }

        public boolean equals(String code) {
            return null == code ? false : code.equals(this.code);
        }

        public boolean equals(Integer index) {
            return null == index ? false : index.equals(this.index);
        }

        public static OrderType getType(String code) {
            for (OrderType type : OrderType.values()) {
                if (type.code.equals(code)) {
                    return type;
                }
            }
            return null;
        }

        public static OrderType getType(Integer index) {
            for (OrderType type : OrderType.values()) {
                if (type.index.equals(index)) {
                    return type;
                }
            }
            return null;
        }
    }

    private static class OrderByEntry {
        private String field;
        private OrderType orderType;

        public OrderByEntry(String field, OrderType orderType) {
            this.field = field;
            this.orderType = orderType;
        }

        public String getField() {
            return field;
        }

        public OrderType getOrderType() {
            return orderType;
        }
    }
}
