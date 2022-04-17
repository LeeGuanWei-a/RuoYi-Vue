package com.ruoyi.schoolJob.mapper;

import java.util.List;
import com.ruoyi.schoolJob.domain.MyWork;
import com.ruoyi.schoolJob.domain.MyTitle;

/**
 * MyWorkMapper接口
 *
 */
public interface MyWorkMapper 
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
     * 删除MyWork
     * 
     * @param workId MyWork主键
     * @return 结果
     */
    public int deleteMyWorkByWorkId(Long workId);

    /**
     * 批量删除MyWork
     * 
     * @param workIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyWorkByWorkIds(Long[] workIds);

    /**
     * 批量删除MyTitle
     * 
     * @param workIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyTitleByTitleIds(Long[] workIds);
    
    /**
     * 批量新增MyTitle
     * 
     * @param myTitleList MyTitle列表
     * @return 结果
     */
    public int batchMyTitle(List<MyTitle> myTitleList);
    

    /**
     * 通过MyWork主键删除MyTitle信息
     * 
     * @param workId MyWorkID
     * @return 结果
     */
    public int deleteMyTitleByTitleId(Long workId);

    /**
     * 查询及格人数
     * @param myWork
     * @return
     */
    public int selectPass(MyWork myWork);

    /**
     * 查询不及格人数
     * @param myWork
     * @return
     */
    public int selectFailed(MyWork myWork);
}
