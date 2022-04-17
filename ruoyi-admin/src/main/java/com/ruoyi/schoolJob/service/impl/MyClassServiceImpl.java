package com.ruoyi.schoolJob.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.schoolJob.mapper.MyClassMapper;
import com.ruoyi.schoolJob.domain.MyClass;
import com.ruoyi.schoolJob.service.IMyClassService;

/**
 * MyClassService业务层处理
 *
 */
@Service
public class MyClassServiceImpl implements IMyClassService 
{
    @Autowired
    private MyClassMapper myClassMapper;

    /**
     * 查询MyClass
     * 
     * @param classId MyClass主键
     * @return MyClass
     */
    @Override
    public MyClass selectMyClassByClassId(Long classId)
    {
        return myClassMapper.selectMyClassByClassId(classId);
    }

    /**
     * 查询MyClass列表
     * 
     * @param myClass MyClass
     * @return MyClass
     */
    @Override
    public List<MyClass> selectMyClassList(MyClass myClass)
    {
        return myClassMapper.selectMyClassList(myClass);
    }

    /**
     * 新增MyClass
     * 
     * @param myClass MyClass
     * @return 结果
     */
    @Transactional
    @Override
    public int insertMyClass(MyClass myClass)
    {
        int rows = myClassMapper.insertMyClass(myClass);
        insertSysUser(myClass);
        return rows;
    }

    /**
     * 修改MyClass
     * 
     * @param myClass MyClass
     * @return 结果
     */
    @Transactional
    @Override
    public int updateMyClass(MyClass myClass)
    {
        myClassMapper.deleteSysUserByUserId(myClass.getClassId());
        insertSysUser(myClass);
        return myClassMapper.updateMyClass(myClass);
    }

    /**
     * 批量删除MyClass
     * 
     * @param classIds 需要删除的MyClass主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyClassByClassIds(Long[] classIds)
    {
        myClassMapper.deleteSysUserByUserIds(classIds);
        return myClassMapper.deleteMyClassByClassIds(classIds);
    }

    /**
     * 删除MyClass信息
     * 
     * @param classId MyClass主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteMyClassByClassId(Long classId)
    {
        myClassMapper.deleteSysUserByUserId(classId);
        return myClassMapper.deleteMyClassByClassId(classId);
    }

    @Override
    public List<SysUser> getTeachers() {
        return myClassMapper.getTeachers();
    }

    @Override
    public List<MyClass> selectAllClass() {
        return this.myClassMapper.selectAllClass();
    }

    /**
     * 新增用户信息信息
     * 
     * @param myClass MyClass对象
     */
    public void insertSysUser(MyClass myClass)
    {
        List<SysUser> sysUserList = myClass.getSysUserList();
        Long classId = myClass.getClassId();
        if (StringUtils.isNotNull(sysUserList))
        {
            List<SysUser> list = new ArrayList<SysUser>();
            for (SysUser sysUser : sysUserList)
            {
                sysUser.setUserId(classId);
                list.add(sysUser);
            }
            if (list.size() > 0)
            {
                myClassMapper.batchSysUser(list);
            }
        }
    }
}
