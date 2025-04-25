package com.anran.springAI.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @TableName course_reservation
 */
@TableName(value ="course_reservation")
@Accessors
@EqualsAndHashCode(callSuper = false)
@Data
public class CourseReservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String course;

    private String studentName;

    private String contactInfo;

    private String school;



    private String remark;

}