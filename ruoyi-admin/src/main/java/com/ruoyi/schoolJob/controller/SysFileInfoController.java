package com.ruoyi.schoolJob.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.schoolJob.domain.SysFileInfo;
import com.ruoyi.schoolJob.service.ISysFileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文件信息Controller
 *
 * @author ruoyi
 * @date 2022-04-17
 */
@RestController
@RequestMapping("/system/info")
public class SysFileInfoController extends BaseController
{
    @Autowired
    private ISysFileInfoService sysFileInfoService;

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{fileId}")
    public SysFileInfo getInfo(@PathVariable("fileId") Long fileId)
    {
        return sysFileInfoService.selectSysFileInfoByFileId(fileId);
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public int add(@RequestBody SysFileInfo sysFileInfo)
    {
        sysFileInfoService.insertSysFileInfo(sysFileInfo);
        return sysFileInfo.getFileId();
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(sysFileInfoService.deleteSysFileInfoByFileIds(fileIds));
    }
}
