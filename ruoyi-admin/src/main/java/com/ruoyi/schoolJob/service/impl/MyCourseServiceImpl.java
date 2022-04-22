package com.ruoyi.schoolJob.service.impl;

import java.util.List;

import com.ruoyi.schoolJob.domain.MyCourse;
import com.ruoyi.schoolJob.mapper.MyCourseMapper;
import com.ruoyi.schoolJob.service.IMyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * courseService业务层处理
 *
 */
@Service
public class MyCourseServiceImpl implements IMyCourseService
{
    @Autowired
    private MyCourseMapper myCourseMapper;

    /**
     * 查询course
     * 
     * @param classId course主键
     * @return course
     */
    @Override
    public MyCourse selectMyCourseByClassId(Long classId)
    {
        return myCourseMapper.selectMyCourseByClassId(classId);
    }

    /**
     * 查询course列表
     * 
     * @param myCourse course
     * @return course
     */
    @Override
    public List<MyCourse> selectMyCourseList(MyCourse myCourse)
    {
        return myCourseMapper.selectMyCourseList(myCourse);
    }

    /**
     * 新增course
     * 
     * @param myCourse course
     * @return 结果
     */
    @Override
    public int insertMyCourse(MyCourse myCourse)
    {
        return myCourseMapper.insertMyCourse(myCourse);
    }

    /**
     * 修改course
     * 
     * @param myCourse course
     * @return 结果
     */
    @Override
    public int updateMyCourse(MyCourse myCourse)
    {
        return myCourseMapper.updateMyCourse(myCourse);
    }

    /**
     * 批量删除course
     * 
     * @param classIds 需要删除的course主键
     * @return 结果
     */
    @Override
    public int deleteMyCourseByClassIds(Long[] classIds)
    {
        return myCourseMapper.deleteMyCourseByClassIds(classIds);
    }

    /**
     * 删除course信息
     * 
     * @param classId course主键
     * @return 结果
     */
    @Override
    public int deleteMyCourseByClassId(Long classId)
    {
        return myCourseMapper.deleteMyCourseByClassId(classId);
    }
}
