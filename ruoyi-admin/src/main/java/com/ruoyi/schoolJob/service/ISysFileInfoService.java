package com.ruoyi.schoolJob.service;

import com.ruoyi.schoolJob.domain.SysFileInfo;

import java.util.List;

/**
 * 文件信息Service接口
 *
 * @author ruoyi
 * @date 2022-04-17
 */
public interface ISysFileInfoService
{

    /**
     * 查询文件信息
     *
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    public SysFileInfo selectSysFileInfoByFileId(Long fileId);

    /**
     * 新增文件信息
     *
     * @param sysFileInfo 文件信息
     * @return 结果
     */
    public int insertSysFileInfo(SysFileInfo sysFileInfo);


    /**
     * 批量删除文件信息
     *
     * @param fileIds 需要删除的文件信息主键集合
     * @return 结果
     */
    public int deleteSysFileInfoByFileIds(Long[] fileIds);

    /**
     * 删除文件信息信息
     *
     * @param fileId 文件信息主键
     * @return 结果
     */
    public int deleteSysFileInfoByFileId(Long fileId);
}
