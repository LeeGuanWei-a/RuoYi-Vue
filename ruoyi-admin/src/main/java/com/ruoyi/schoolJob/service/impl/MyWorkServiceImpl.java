package com.ruoyi.schoolJob.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.schoolJob.domain.MyTitle;
import com.ruoyi.schoolJob.mapper.MyWorkMapper;
import com.ruoyi.schoolJob.domain.MyWork;
import com.ruoyi.schoolJob.service.IMyWorkService;

/**
 * MyWorkService业务层处理
 *
 */
@Service
public class MyWorkServiceImpl implements IMyWorkService 
{
    @Autowired
    private MyWorkMapper myWorkMapper;

    /**
     * 查询MyWork
     * 
     * @param workId MyWork主键
     * @return MyWork
     */
    @Override
    public MyWork selectMyWorkByWorkId(Long workId)
    {
        return myWorkMapper.selectMyWorkByWorkId(workId);
    }

    /**
     * 查询MyWork列表
     * 
     * @param myWork MyWork
     * @return MyWork
     */
    @Override
    public List<MyWork> selectMyWorkList(MyWork myWork)
    {
        return myWorkMapper.selectMyWorkList(myWork);
    }

    /**
     * 新增MyWork
     * 
     * @param myWork MyWork
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMyWork(MyWork myWork)
    {
        int rows = myWorkMapper.insertMyWork(myWork);
        insertMyTitle(myWork);
        return rows;
    }

    /**
     * 修改MyWork
     * 
     * @param myWork MyWork
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMyWork(MyWork myWork)
    {
        myWorkMapper.deleteMyTitleByTitleId(myWork.getWorkId());
        insertMyTitle(myWork);
        return myWorkMapper.updateMyWork(myWork);
    }

    /**
     * 批量删除MyWork
     * 
     * @param workIds 需要删除的MyWork主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyWorkByWorkIds(Long[] workIds)
    {
        myWorkMapper.deleteMyTitleByTitleIds(workIds);
        return myWorkMapper.deleteMyWorkByWorkIds(workIds);
    }

    /**
     * 删除MyWork信息
     * 
     * @param workId MyWork主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyWorkByWorkId(Long workId)
    {
        myWorkMapper.deleteMyTitleByTitleId(workId);
        return myWorkMapper.deleteMyWorkByWorkId(workId);
    }

    /**
     * 新增MyTitle信息
     * 
     * @param myWork MyWork对象
     */
    public void insertMyTitle(MyWork myWork)
    {
        List<MyTitle> myTitleList = myWork.getMyTitleList();
        Long workId = myWork.getWorkId();
        if (StringUtils.isNotNull(myTitleList))
        {
            List<MyTitle> list = new ArrayList<MyTitle>();
            for (MyTitle myTitle : myTitleList)
            {
                myTitle.setTitleId(workId);
                list.add(myTitle);
            }
            if (list.size() > 0)
            {
                myWorkMapper.batchMyTitle(list);
            }
        }
    }
}
