package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-11-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {
    @Override
    public Page<EduTeacher> pageListTeacherCondition(Long current, Long limit, TeacherQuery teacherQuery) {
        //创建Page对象
        Page<EduTeacher> eduTeacherPage = new Page<>(current,limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //获取传入讲师的条件是否为空
        //讲师名
        String name = teacherQuery.getName();
        //讲师级别
        Integer level = teacherQuery.getLevel();
        //开始时间
        String begin = teacherQuery.getBegin();
        //结束时间
        String end = teacherQuery.getEnd();

        //多条件组合查询
        //判断条件值是否为空,如果不为空拼接条件
        if (!StringUtils.isEmpty(name)){
            //构建条件 模糊查询
            wrapper.like("name",name);
        }

        if (!StringUtils.isEmpty(String.valueOf(level))){
            //等于
            wrapper.eq("level",level);
        }

        if (!StringUtils.isEmpty(begin)){
            //大于等于
            wrapper.ge("gmt_create",begin);
        }

        if (!StringUtils.isEmpty(end)){
            //小于等于
            wrapper.le("gmt_modified",end);
        }

        //调用mybatis plus分页方法进行查询
        baseMapper.selectPage(eduTeacherPage,wrapper);

        //通过Page对象获取分页信息
        //long current = page.getCurrent();//当前页
        List<EduTeacher> records = eduTeacherPage.getRecords(); //每页的数据 list集合
        //long size = eduTeacherPage.getSize(); //每页显示的条数
        long total = eduTeacherPage.getTotal(); //总记录数
        long pages = eduTeacherPage.getPages(); //总页数

        boolean pagehasNext = eduTeacherPage.hasNext(); //下一页
        boolean pagehasPrevious = eduTeacherPage.hasPrevious(); //上一页

        return eduTeacherPage;
    }
}

