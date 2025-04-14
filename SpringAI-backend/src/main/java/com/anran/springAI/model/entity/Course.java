package com.anran.springAI.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @TableName course
 */
@TableName(value ="course")
@Accessors
@EqualsAndHashCode(callSuper = false)
@Data
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    private String name;

    private Integer edu;

    private String type;

    private Long price;

    private Integer duration;


}