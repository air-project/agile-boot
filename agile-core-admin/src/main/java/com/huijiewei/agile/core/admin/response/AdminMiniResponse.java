package com.huijiewei.agile.core.admin.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class AdminMiniResponse extends AdminBaseResponse {
    @Schema(description = "所属管理组")
    private AdminGroupResponse adminGroup;
}
