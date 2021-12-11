package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2021-11-29
 */
public interface EduTeacherService extends IService<EduTeacher> {
    /**
     * 自己弄的分页
     */
    Page<EduTeacher> pageListTeacherCondition(Long current, Long limit, TeacherQuery teacherQuery);

}
