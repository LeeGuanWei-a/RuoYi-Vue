package com.ruoyi.schoolJob.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.schoolJob.domain.MyClass;
import com.ruoyi.schoolJob.service.IMyClassService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.schoolJob.domain.MyTitle;
import com.ruoyi.schoolJob.service.IMyTitleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MyTitleController
 *
 */
@RestController
@RequestMapping("/schoolJob/MyTitle")
public class MyTitleController extends BaseController
{
    @Autowired
    private IMyTitleService myTitleService;
    @Autowired
    private IMyClassService myClassService;

    /**
     * 查询MyTitle列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyTitle myTitle)
    {
        startPage();
        List<MyTitle> list = myTitleService.selectMyTitleList(myTitle);
        return getDataTable(list);
    }

    /**
     * 导出MyTitle列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:export')")
    @Log(title = "MyTitle", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyTitle myTitle)
    {
        List<MyTitle> list = myTitleService.selectMyTitleList(myTitle);
        ExcelUtil<MyTitle> util = new ExcelUtil<MyTitle>(MyTitle.class);
        util.exportExcel(response, list, "MyTitle数据");
    }

    /**
     * 获取MyTitle详细信息
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:query')")
    @GetMapping(value = "/{titleId}")
    public AjaxResult getInfo(@PathVariable("titleId") Long titleId)
    {
        return AjaxResult.success(myTitleService.selectMyTitleByTitleId(titleId));
    }

    /**
     * 新增MyTitle
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:add')")
    @Log(title = "MyTitle", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyTitle myTitle)
    {
        return toAjax(myTitleService.insertMyTitle(myTitle));
    }

    /**
     * 修改MyTitle
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:edit')")
    @Log(title = "MyTitle", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyTitle myTitle)
    {
        return toAjax(myTitleService.updateMyTitle(myTitle));
    }

    /**
     * 删除MyTitle
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyTitle:remove')")
    @Log(title = "MyTitle", businessType = BusinessType.DELETE)
	@DeleteMapping("/{titleIds}")
    public AjaxResult remove(@PathVariable Long[] titleIds)
    {
        return toAjax(myTitleService.deleteMyTitleByTitleIds(titleIds));
    }

    /**
     * 查询MyClass列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:list')")
    @GetMapping("/classList")
    public List<MyClass> list()
    {
        List<MyClass> list = myClassService.selectAllClass();
        return list;
    }
}
