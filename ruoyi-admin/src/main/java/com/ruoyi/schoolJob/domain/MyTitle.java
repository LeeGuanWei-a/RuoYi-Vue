package com.ruoyi.schoolJob.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * MyTitle对象 my_title
 *
 */
public class MyTitle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题目Id */
    private Long titleId;

    /** 题目名称 */
    @Excel(name = "题目名称")
    private String titleName;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private int fileId;

    /** 教学班Id */
    @Excel(name = "教学班Id")
    private Long classId;

    private String classCode;

    private Long userId;

    private String userName;

    /** MyClass信息 */
    private List<MyClass> myClassList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public void setTitleId(Long titleId) 
    {
        this.titleId = titleId;
    }

    public Long getTitleId() 
    {
        return titleId;
    }
    public void setTitleName(String titleName) 
    {
        this.titleName = titleName;
    }

    public String getTitleName() 
    {
        return titleName;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public void setClassId(Long classId)
    {
        this.classId = classId;
    }

    public Long getClassId() 
    {
        return classId;
    }

    public List<MyClass> getMyClassList()
    {
        return myClassList;
    }

    public void setMyClassList(List<MyClass> myClassList)
    {
        this.myClassList = myClassList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("titleId", getTitleId())
            .append("titleName", getTitleName())
            .append("description", getDescription())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("fileId", getFileId())
            .append("classId", getClassId())
            .append("myClassList", getMyClassList())
            .toString();
    }
}
