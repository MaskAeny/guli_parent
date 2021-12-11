package com.atguigu.eduservice.controller;

import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-11-29
 */
@Api(description = "讲师管理")
@CrossOrigin //解决跨域问题
@RestController
@RequestMapping("/eduservice/eduteacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师
     *
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping
    public R getAllTeacher() {
        // try {
        //     int i = 10 / 0; // 模拟异常，测试统一异常处理
        // } catch (Exception e) {
        //     e.printStackTrace();
        //     throw new GuliException(20001,"自定义异常");  // 抛出 GuliException 异常
        // }

        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("list", list);
    }

    /**
     * 逻辑删除教师
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R delTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        eduTeacherService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "不带条件分页查询")
    @GetMapping("getTeacherPage/{current}/{limit}")
    public R getTeacherPage(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        eduTeacherService.page(page, null);
        //获取查询集合的列表
        List<EduTeacher> records = page.getRecords();
        long total = page.getTotal();
        Map<String, Object> map = new HashMap<>();
        map.put("list", records);
        map.put("total", total);
        return R.ok().data(map);
    }

    /**
     * 自己弄的分页
     * @param
     * @return
     */
    @ApiOperation(value = "带条件分页查询讲师列表")
    @PostMapping("getTeacherPageVo/{current}/{limit}")
    public R getTeacherPageVo(@PathVariable Long current,
                              @PathVariable Long limit,
                              @RequestBody TeacherQuery teacherQuery) {
        Page<EduTeacher> eduTeacherPage = eduTeacherService.pageListTeacherCondition(current, limit, teacherQuery);
        // return new R(ResponseResult.CodeStatus.OK,"讲师条件方法查询成功",eduTeacherPage);

        return R.ok().data("data",eduTeacherPage);
    }

//     @ApiOperation(value = "带条件分页查询讲师列表")
//     @PostMapping("getTeacherPageVo/{current}/{limit}")
//     public R getTeacherPageVo(@PathVariable Long current,
//                               @PathVariable Long limit,
//                               @RequestBody TeacherQuery teacherQuery){
//         //@RequestBody把json串转化成实体类
//         //1、取出查询条件
//         String name = teacherQuery.getName();
//         Integer level = teacherQuery.getLevel();
//         String begin = teacherQuery.getBegin();
//         String end = teacherQuery.getEnd();
//         //2、判断条件是否为空，如不为空拼写sql
//         QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
//         if(!StringUtils.isEmpty(name)){
//             wrapper.like("name",name);
//         }
//         if(!StringUtils.isEmpty(level)){
//             wrapper.eq("level",level);
//         }
//         if(!StringUtils.isEmpty(begin)){
//             wrapper.ge("gmt_create",begin);
//         }
//         if(!StringUtils.isEmpty(end)){
//             wrapper.le("gmt_create",end);
//         }
//
//         Page<EduTeacher> page = new Page<>(current,limit);
//         eduTeacherService.page(page,wrapper);
//         List<EduTeacher> records = page.getRecords();
//         long total = page.getTotal();
//         //1、存入MAP
// //        Map<String,Object> map = new HashMap<>();
// //        map.put("list",records);
// //        map.put("total",total);
// //        return R.ok().data(map);
//         //2、直接拼接
//         return R.ok().data("list",records).data("total",total);
//
//     }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = eduTeacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping("getTeacherById/{id}")
    public R getTeacherById(@PathVariable String id) {
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("eduTeacher", eduTeacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher( @RequestBody EduTeacher eduTeacher){
        boolean update = eduTeacherService.updateById(eduTeacher);
        if(update){
            return R.ok();
        }else{
            return R.error();
        }
    }

}

