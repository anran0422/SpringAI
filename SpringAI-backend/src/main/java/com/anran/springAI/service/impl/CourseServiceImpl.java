package com.anran.springAI.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.anran.springAI.model.entity.Course;
import com.anran.springAI.service.CourseService;
import com.anran.springAI.mapper.CourseMapper;
import org.springframework.stereotype.Service;

/**
* @author macbook
* @description 针对表【course(学科表)】的数据库操作Service实现
* @createDate 2025-04-09 19:49:06
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{

}




