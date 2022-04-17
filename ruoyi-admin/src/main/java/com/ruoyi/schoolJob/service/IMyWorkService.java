package com.ruoyi.schoolJob.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.schoolJob.domain.MyWork;

/**
 * MyWorkService接口
 *
 */
public interface IMyWorkService 
{
    /**
     * 查询MyWork
     * 
     * @param workId MyWork主键
     * @return MyWork
     */
    public MyWork selectMyWorkByWorkId(Long workId);

    /**
     * 查询MyWork列表
     * 
     * @param myWork MyWork
     * @return MyWork集合
     */
    public List<MyWork> selectMyWorkList(MyWork myWork);

    /**
     * 新增MyWork
     * 
     * @param myWork MyWork
     * @return 结果
     */
    public int insertMyWork(MyWork myWork);

    /**
     * 修改MyWork
     * 
     * @param myWork MyWork
     * @return 结果
     */
    public int updateMyWork(MyWork myWork);

    /**
     * 批量删除MyWork
     * 
     * @param workIds 需要删除的MyWork主键集合
     * @return 结果
     */
    public int deleteMyWorkByWorkIds(Long[] workIds);

    /**
     * 删除MyWork信息
     * 
     * @param workId MyWork主键
     * @return 结果
     */
    public int deleteMyWorkByWorkId(Long workId);

    /**
     * 查询教学班题目
     * @return
     */
    public List<Map<String,Object>> selectTitle();

    /**
     * 查询及格人数
     * @param myWork
     * @return
     */
    public List selectScore(MyWork myWork);

}
