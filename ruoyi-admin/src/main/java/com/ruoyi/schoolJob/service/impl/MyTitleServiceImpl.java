package com.ruoyi.schoolJob.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.schoolJob.domain.MyClass;
import com.ruoyi.schoolJob.mapper.MyTitleMapper;
import com.ruoyi.schoolJob.domain.MyTitle;
import com.ruoyi.schoolJob.service.IMyTitleService;

/**
 * MyTitleService业务层处理
 *
 */
@Service
public class MyTitleServiceImpl implements IMyTitleService 
{
    @Autowired
    private MyTitleMapper myTitleMapper;

    /**
     * 查询MyTitle
     * 
     * @param titleId MyTitle主键
     * @return MyTitle
     */
    @Override
    public MyTitle selectMyTitleByTitleId(Long titleId)
    {
        return myTitleMapper.selectMyTitleByTitleId(titleId);
    }

    /**
     * 查询MyTitle列表
     * 
     * @param myTitle MyTitle
     * @return MyTitle
     */
    @Override
    public List<MyTitle> selectMyTitleList(MyTitle myTitle)
    {
        return myTitleMapper.selectMyTitleList(myTitle);
    }

    /**
     * 新增MyTitle
     * 
     * @param myTitle MyTitle
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMyTitle(MyTitle myTitle)
    {
        int rows = myTitleMapper.insertMyTitle(myTitle);
        insertMyClass(myTitle);
        return rows;
    }

    /**
     * 修改MyTitle
     * 
     * @param myTitle MyTitle
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMyTitle(MyTitle myTitle)
    {
        myTitleMapper.deleteMyClassByClassId(myTitle.getTitleId());
        insertMyClass(myTitle);
        return myTitleMapper.updateMyTitle(myTitle);
    }

    /**
     * 批量删除MyTitle
     * 
     * @param titleIds 需要删除的MyTitle主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyTitleByTitleIds(Long[] titleIds)
    {
        myTitleMapper.deleteMyClassByClassIds(titleIds);
        return myTitleMapper.deleteMyTitleByTitleIds(titleIds);
    }

    /**
     * 删除MyTitle信息
     * 
     * @param titleId MyTitle主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyTitleByTitleId(Long titleId)
    {
        myTitleMapper.deleteMyClassByClassId(titleId);
        return myTitleMapper.deleteMyTitleByTitleId(titleId);
    }

    /**
     * 新增MyClass信息
     * 
     * @param myTitle MyTitle对象
     */
    public void insertMyClass(MyTitle myTitle)
    {
        List<MyClass> myClassList = myTitle.getMyClassList();
        Long titleId = myTitle.getTitleId();
        if (StringUtils.isNotNull(myClassList))
        {
            List<MyClass> list = new ArrayList<MyClass>();
            for (MyClass myClass : myClassList)
            {
                myClass.setClassId(titleId);
                list.add(myClass);
            }
            if (list.size() > 0)
            {
                myTitleMapper.batchMyClass(list);
            }
        }
    }
}
