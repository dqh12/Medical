package com.henu.model.models.acl;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henu.model.models.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色
 */
@Data
@ApiModel(description = "用户角色")
@TableName("acl_user_role")
public class UserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "角色id")
	@TableField("role_id")
	private Long roleId;

	@ApiModelProperty(value = "用户id")
	@TableField("user_id")
	private Long userId;

}

