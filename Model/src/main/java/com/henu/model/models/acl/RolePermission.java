package com.henu.model.models.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henu.model.models.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色权限
 */
@Data
@ApiModel(description = "角色权限")
@TableName("acl_role_permission")
public class RolePermission extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "roleid")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "permissionId")
	@TableField("permission_id")
	private Long permissionId;

}

