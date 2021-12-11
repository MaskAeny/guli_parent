package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.baseservice.handler.GuliException;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.vo.ExcelSubjectData;
import com.atguigu.eduservice.entity.vo.OneSubjectVo;
import com.atguigu.eduservice.entity.vo.TwoSubjectVo;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //批量导入课程分类
    @Override
    public void addSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class,
                    new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "导入课程分类失败");
        }
    }

    //查询所有课程分类
    @Override
    public List<OneSubjectVo> getAllSubject() {
        //1查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        //parent_id为0是一级分类
        wrapperOne.eq("parent_id", "0");
        //selectList()方法：根据查询条件查询全部记录
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //2查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        //parent_id不为0就是二级分类，老铁没毛病
        wrapperTwo.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);
        //3封装一级分类
        List<OneSubjectVo> allSubjectList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //3.1 取出每一个一级分类
            EduSubject oneSubject = oneSubjectList.get(i);
            //3.2 EduSubject转化成OneSubjectVo
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            //把EduSubject的属性赋值给OneSubjectVo的属性
            oneSubjectVo.setId(oneSubject.getId());
            oneSubjectVo.setTitle(oneSubject.getTitle());
            //BeanUtils.copyProperties(复制对象属性方法)
            // BeanUtils.copyProperties(oneSubject, oneSubjectVo);
            //将结果添加到一级分类里面
            allSubjectList.add(oneSubjectVo);
            //4找到根一级有关的二级进行封装
            List<TwoSubjectVo> twoSubjectVos = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //4.1取出每一个二级分类
                EduSubject twoSubject = twoSubjectList.get(m);
                //4.2 判断是否归属此一级
                //注意parentId字段是String类型，所以比较用equals方法
                //如果parentId等于id就是二级菜单
                if (twoSubject.getParentId().equals(oneSubject.getId())) {
                    //4.3EduSubject转化TwoSubjectVo
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    // twoSubjectVo.setId(twoSubject.getId());
                    // twoSubjectVo.setTitle(twoSubject.getTitle());
                    BeanUtils.copyProperties(twoSubject, twoSubjectVo);
                    twoSubjectVos.add(twoSubjectVo);
                }
            }
            //把二级菜单的数据赋值到一级菜单的子节点上
            oneSubjectVo.setChildren(twoSubjectVos);
        }
        //返回的是封装好的一级菜单的集合，这个集合只包含id和title属性，一级菜单已经包含二级菜单了
        return allSubjectList;
    }
}

