package com.ruoyi.schoolJob.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * course对象 my_course
 */
public class MyCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教学班id */
    @Excel(name = "教学班id")
    private Long classId;

    /** 学生id */
    @Excel(name = "学生id")
    private Long userId;

    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("userId", getUserId())
            .toString();
    }
}
