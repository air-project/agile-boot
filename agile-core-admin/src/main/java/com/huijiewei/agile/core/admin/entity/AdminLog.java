package com.huijiewei.agile.core.admin.entity;

import com.huijiewei.agile.core.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DynamicInsert
@DynamicUpdate
public class AdminLog extends BaseEntity {
    public static final String TYPE_LOGIN = "LOGIN";
    public static final String TYPE_VISIT = "VISIT";
    public static final String TYPE_OPERATE = "OPERATE";

    public static final Integer STATUS_FAIL = 0;
    public static final Integer STATUS_SUCCESS = 1;

    private String type;
    private Integer status;
    private String method;
    private String action;
    private String params;
    private String userAgent;
    private String remoteAddr;
    private String exception;
    private LocalDateTime createdAt;

    private Integer adminId;

    @ManyToOne
    @JoinColumn(name = "adminId", insertable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private Admin admin;

    public static List<Status> statusList() {
        List<Status> statuses = new ArrayList<>();

        statuses.add(new Status(STATUS_FAIL, "失败"));
        statuses.add(new Status(STATUS_SUCCESS, "成功"));

        return statuses;
    }

    public static Status getStatus(Integer status) {
        List<Status> statuses = AdminLog.statusList();

        for (Status everyStatus : statuses) {
            if (everyStatus.value.equals(status)) {
                return everyStatus;
            }
        }

        return new Status(status, status.toString());
    }

    public static List<Type> typeList() {
        List<Type> types = new ArrayList<>();

        types.add(new Type(TYPE_LOGIN, "登录"));
        types.add(new Type(TYPE_VISIT, "访问"));
        types.add(new Type(TYPE_OPERATE, "操作"));

        return types;
    }

    public static Type getType(String type) {
        List<Type> types = AdminLog.typeList();

        for (Type everyType : types) {
            if (everyType.value.equals(type)) {
                return everyType;
            }
        }

        return new Type(type, type);
    }

    @Data
    public static class Type {
        private String value;
        private String description;

        public Type(String value, String label) {
            this.value = value;
            this.description = label;
        }
    }

    @Data
    public static class Status {
        private Integer value;
        private String description;

        public Status(Integer status, String label) {
            this.value = status;
            this.description = label;
        }
    }
}
