package com.ruoyi.schoolJob.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.schoolJob.domain.MyClass;

/**
 * MyClassMapper接口
 *
 */
public interface MyClassMapper 
{
    /**
     * 查询MyClass
     * 
     * @param classId MyClass主键
     * @return MyClass
     */
    public MyClass selectMyClassByClassId(Long classId);

    /**
     * 查询MyClass列表
     * 
     * @param myClass MyClass
     * @return MyClass集合
     */
    public List<MyClass> selectMyClassList(MyClass myClass);

    /**
     * 新增MyClass
     * 
     * @param myClass MyClass
     * @return 结果
     */
    public int insertMyClass(MyClass myClass);

    /**
     * 修改MyClass
     * 
     * @param myClass MyClass
     * @return 结果
     */
    public int updateMyClass(MyClass myClass);

    /**
     * 删除MyClass
     * 
     * @param classId MyClass主键
     * @return 结果
     */
    public int deleteMyClassByClassId(Long classId);

    /**
     * 批量删除MyClass
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyClassByClassIds(Long[] classIds);

    /**
     * 批量删除用户信息
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysUserByUserIds(Long[] classIds);
    
    /**
     * 批量新增用户信息
     * 
     * @param sysUserList 用户信息列表
     * @return 结果
     */
    public int batchSysUser(List<SysUser> sysUserList);
    

    /**
     * 通过MyClass主键删除用户信息信息
     * 
     * @param classId MyClassID
     * @return 结果
     */
    public int deleteSysUserByUserId(Long classId);

    /**
     * 查询教师
     * @return
     */
    public List<SysUser> getTeachers();
}
