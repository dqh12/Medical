package com.henu.model.models.cms;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.henu.model.models.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 首页Banner实体
 */
@Data
@ApiModel(description = "首页Banner实体")
@TableName("banner")
public class Banner extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "标题")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "图片地址")
	@TableField("image_url")
	private String imageUrl;

	@ApiModelProperty(value = "链接地址")
	@TableField("link_url")
	private String linkUrl;

	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

}

