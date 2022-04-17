package com.ruoyi.schoolJob.service.impl;

import java.util.HashMap;
import java.util.List;

import com.ruoyi.schoolJob.mapper.MyClassMapper;
import com.ruoyi.schoolJob.mapper.MyTitleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Map;

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
    @Autowired
    private MyClassMapper myClassMapper;
    @Autowired
    private MyTitleMapper myTitleMapper;

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
     * 查询教学班题目
     * @return
     */
    @Override
    public List<Map<String, Object>> selectTitle() {
        List<Map<String,Object>> myClass = myClassMapper.selectClass();
        List<Map<String,Object>> childClass = new ArrayList<>();
        System.out.println("myclass=" + myClass.toString());
        for (int i = 0; i < myClass.size(); i++){
            Map<String,Object> mapClass = new HashMap<>();
            List<Map<String,Object>> childTitle = new ArrayList<>();
            mapClass.put("id",myClass.get(i).get("classId").toString());
            mapClass.put("name",myClass.get(i).get("classCode"));

            Long classId = (Long) myClass.get(i).get("classId");
            List<Map<String,Object>> myTitle = myTitleMapper.selectTitleByClassId(classId);

            for (int j = 0; j < myTitle.size(); j++){
                String title_id = myTitle.get(j).get("title_id").toString();
                String title_name = myTitle.get(j).get("title_name").toString();
                Map<String,Object> map = new HashMap<>();
                map.put("id", title_id);
                map.put("name", title_name);
                childTitle.add(map);
                mapClass.put("children",childTitle);
            }
            childClass.add(mapClass);
        }
        System.out.println(childClass.toString());
        return childClass;
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
