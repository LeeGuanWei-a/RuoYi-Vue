package com.ruoyi.schoolJob.service;

import java.util.List;
import com.ruoyi.schoolJob.domain.MyClass;

/**
 * MyClassService接口
 *
 */
public interface IMyClassService 
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
     * 批量删除MyClass
     * 
     * @param classIds 需要删除的MyClass主键集合
     * @return 结果
     */
    public int deleteMyClassByClassIds(Long[] classIds);

    /**
     * 删除MyClass信息
     * 
     * @param classId MyClass主键
     * @return 结果
     */
    public int deleteMyClassByClassId(Long classId);
}
