package com.anran.springAI.tools;

import com.anran.springAI.model.dto.query.CourseQuery;
import com.anran.springAI.model.entity.Course;
import com.anran.springAI.model.entity.CourseReservation;
import com.anran.springAI.model.entity.School;
import com.anran.springAI.service.CourseReservationService;
import com.anran.springAI.service.CourseService;
import com.anran.springAI.service.SchoolService;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

// todo 这个注解有啥用？
@RequiredArgsConstructor
@Component
public class CourseTools {

    // todo 这里视频是这样的，我注释掉了
    // 可能是不适用的，毕竟不一样？！private final ICourseService courseService;

    @Resource
    private CourseService courseService;

    @Resource
    private SchoolService schoolService;

    @Resource
    private CourseReservationService courseReservationService;

    /**
     * Tool 注解给 springAI 去看的，表示工具
     */
    @Tool(description = "根据条件查询课程")
    public List<Course> queryCourse(@ToolParam(description = "查询的条件", required = false) CourseQuery query) {
        // 没有条件直接全部返回
        if(query == null) {
            return courseService.list();
        }

        String type = query.getType();
        Integer edu = query.getEdu();
        List<CourseQuery.Sort> sorts = query.getSorts();

        // todo 为什么用不了 Hutools 工具
        //      可以去看看转换成 queryWrapper 之后的写法，优化时
        QueryChainWrapper<Course> wrapper = courseService.query()
                .eq(type != null, "type", type)
                .le(edu != null, "edu", edu);
        if(sorts != null && !sorts.isEmpty()) {
            for (CourseQuery.Sort sort : sorts) {
                wrapper.orderBy(true, sort.getAsc(), sort.getField());
            }
        }
        return wrapper.list();
    }

    // todo 修改school表，可以增加学科字段，根据学科再去查校区
    @Tool(description = "查询所有校区")
    public List<School> querySchool() {
        return schoolService.list();
    }

    /**
     * 创建预约单
     */
    @Tool(description = "生成预约单，返回预约单号")
    public Integer createCourseReservation(@ToolParam(description = "预约课程") String course,
                                           @ToolParam(description = "预约校区") String school,
                                           @ToolParam(description = "学生姓名") String studentName,
                                           @ToolParam(description = "联系电话") String contactInfo,
                                           @ToolParam(description = "备注", required = false) String remark) {
        CourseReservation courseReservation = new CourseReservation();
        courseReservation.setCourse(course);
        courseReservation.setStudentName(school);
        courseReservation.setContactInfo(studentName);
        courseReservation.setSchool(contactInfo);
        courseReservation.setRemark(remark);
        courseReservationService.save(courseReservation);
        return courseReservation.getId();
    }
}
