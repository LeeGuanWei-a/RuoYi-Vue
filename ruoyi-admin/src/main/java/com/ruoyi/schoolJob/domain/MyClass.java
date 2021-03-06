package com.ruoyi.schoolJob.domain;

import java.util.List;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * MyClass对象 my_class
 *
 */
public class MyClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教学班Id */
    private Long classId;

    private String classCode;

    /** 教学班名称 */
    @Excel(name = "教学班名称")
    private String className;

    /** 教师Id */
    @Excel(name = "教师Id")
    private Long userId;

    private String nickName;

    /** 用户信息信息 */
    private List<SysUser> sysUserList;

    public void setClassId(Long classId) 
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }
    public void setClassName(String className) 
    {
        this.className = className;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName()
    {
        return className;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<SysUser> getSysUserList()
    {
        return sysUserList;
    }

    public void setSysUserList(List<SysUser> sysUserList)
    {
        this.sysUserList = sysUserList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("classId", getClassId())
            .append("classCode", getClassCode())
            .append("className", getClassName())
            .append("userId", getUserId())
            .append("nickName", getNickName())
            .append("sysUserList", getSysUserList())
            .toString();
    }
}
