package com.ruoyi.schoolJob.mapper;

import com.ruoyi.schoolJob.domain.MyCourse;

import java.util.List;

/**
 * courseMapper接口
 * 
 * @author ruoyi
 * @date 2022-04-19
 */
public interface MyCourseMapper 
{
    /**
     * 查询course
     * 
     * @param classId course主键
     * @return course
     */
    public MyCourse selectMyCourseByClassId(Long classId);

    /**
     * 查询course列表
     * 
     * @param myCourse course
     * @return course集合
     */
    public List<MyCourse> selectMyCourseList(MyCourse myCourse);

    /**
     * 新增course
     * 
     * @param myCourse course
     * @return 结果
     */
    public int insertMyCourse(MyCourse myCourse);

    /**
     * 修改course
     * 
     * @param myCourse course
     * @return 结果
     */
    public int updateMyCourse(MyCourse myCourse);

    /**
     * 删除course
     * 
     * @param classId course主键
     * @return 结果
     */
    public int deleteMyCourseByClassId(Long classId);

    /**
     * 批量删除course
     * 
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyCourseByClassIds(Long[] classIds);
}
