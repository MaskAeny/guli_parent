package com.atguigu.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
//一级菜单
public class OneSubjectVo {
    @ApiModelProperty(value = "课程类别ID")
    private String id;
    @ApiModelProperty(value = "类别名称")
    private String title;
    //把二级菜单放到一级菜单里面
    private List<TwoSubjectVo> children = new ArrayList<>();
}
