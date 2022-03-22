package com.ruoyi.schoolJob.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * MyWork对象 my_work
 *
 */
public class MyWork extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 作品Id */
    private Long workId;

    /** 作品名称 */
    @Excel(name = "作品名称")
    private String workName;

    /** 学生Id */
    @Excel(name = "学生Id")
    private Long userId;

    /** 题目Id */
    @Excel(name = "题目Id")
    private Long titleId;

    /** 作品分数 */
    @Excel(name = "作品分数")
    private Long score;

    /** 文件上传 */
    @Excel(name = "文件上传")
    private String upload;

    /** MyTitle信息 */
    private List<MyTitle> myTitleList;

    public void setWorkId(Long workId) 
    {
        this.workId = workId;
    }

    public Long getWorkId() 
    {
        return workId;
    }
    public void setWorkName(String workName) 
    {
        this.workName = workName;
    }

    public String getWorkName() 
    {
        return workName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTitleId(Long titleId) 
    {
        this.titleId = titleId;
    }

    public Long getTitleId() 
    {
        return titleId;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }
    public void setUpload(String upload) 
    {
        this.upload = upload;
    }

    public String getUpload() 
    {
        return upload;
    }

    public List<MyTitle> getMyTitleList()
    {
        return myTitleList;
    }

    public void setMyTitleList(List<MyTitle> myTitleList)
    {
        this.myTitleList = myTitleList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("workId", getWorkId())
            .append("workName", getWorkName())
            .append("userId", getUserId())
            .append("titleId", getTitleId())
            .append("score", getScore())
            .append("upload", getUpload())
            .append("myTitleList", getMyTitleList())
            .toString();
    }
}
