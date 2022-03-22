package com.ruoyi.schoolJob.service;

import java.util.List;
import com.ruoyi.schoolJob.domain.MyTitle;

/**
 * MyTitleService接口
 *
 */
public interface IMyTitleService 
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
     * 批量删除MyTitle
     * 
     * @param titleIds 需要删除的MyTitle主键集合
     * @return 结果
     */
    public int deleteMyTitleByTitleIds(Long[] titleIds);

    /**
     * 删除MyTitle信息
     * 
     * @param titleId MyTitle主键
     * @return 结果
     */
    public int deleteMyTitleByTitleId(Long titleId);
}
