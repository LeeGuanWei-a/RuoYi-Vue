package com.ruoyi.schoolJob.mapper;

import java.util.List;
import com.ruoyi.schoolJob.domain.MyTitle;
import com.ruoyi.schoolJob.domain.MyClass;

/**
 * MyTitleMapper接口
 *
 */
public interface MyTitleMapper 
{
    /**
     * 查询MyTitle
     * 
     * @param titleId MyTitle主键
     * @return MyTitle
     */
    public MyTitle selectMyTitleByTitleId(Long titleId);

    /**
     * 查询MyTitle列表
     * 
     * @param myTitle MyTitle
     * @return MyTitle集合
     */
    public List<MyTitle> selectMyTitleList(MyTitle myTitle);

    /**
     * 新增MyTitle
     * 
     * @param myTitle MyTitle
     * @return 结果
     */
    public int insertMyTitle(MyTitle myTitle);

    /**
     * 修改MyTitle
     * 
     * @param myTitle MyTitle
     * @return 结果
     */
    public int updateMyTitle(MyTitle myTitle);

    /**
     * 删除MyTitle
     * 
     * @param titleId MyTitle主键
     * @return 结果
     */
    public int deleteMyTitleByTitleId(Long titleId);

    /**
     * 批量删除MyTitle
     * 
     * @param titleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyTitleByTitleIds(Long[] titleIds);

    /**
     * 批量删除MyClass
     * 
     * @param titleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMyClassByClassIds(Long[] titleIds);
    
    /**
     * 批量新增MyClass
     * 
     * @param myClassList MyClass列表
     * @return 结果
     */
    public int batchMyClass(List<MyClass> myClassList);
    

    /**
     * 通过MyTitle主键删除MyClass信息
     * 
     * @param titleId MyTitleID
     * @return 结果
     */
    public int deleteMyClassByClassId(Long titleId);
}
