package com.ruoyi.schoolJob.controller;

import java.util.List;
import java.util.Map;
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
import com.ruoyi.schoolJob.domain.MyWork;
import com.ruoyi.schoolJob.service.IMyWorkService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MyWorkController
 *
 */
@RestController
@RequestMapping("/schoolJob/MyWork")
public class MyWorkController extends BaseController
{
    @Autowired
    private IMyWorkService myWorkService;


    /**
     * 查询MyWork列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyWork myWork)
    {
        startPage();
        List<MyWork> list = myWorkService.selectMyWorkList(myWork);
        return getDataTable(list);
    }

    /**
     * 导出MyWork列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:export')")
    @Log(title = "MyWork", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyWork myWork)
    {
        List<MyWork> list = myWorkService.selectMyWorkList(myWork);
        ExcelUtil<MyWork> util = new ExcelUtil<MyWork>(MyWork.class);
        util.exportExcel(response, list, "MyWork数据");
    }

    /**
     * 获取MyWork详细信息
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:query')")
    @GetMapping(value = "/{workId}")
    public AjaxResult getInfo(@PathVariable("workId") Long workId)
    {
        return AjaxResult.success(myWorkService.selectMyWorkByWorkId(workId));
    }

    /**
     * 新增MyWork
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:add')")
    @Log(title = "MyWork", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyWork myWork)
    {
        return toAjax(myWorkService.insertMyWork(myWork));
    }

    /**
     * 修改MyWork
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:edit')")
    @Log(title = "MyWork", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyWork myWork)
    {
        return toAjax(myWorkService.updateMyWork(myWork));
    }

    /**
     * 删除MyWork
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:remove')")
    @Log(title = "MyWork", businessType = BusinessType.DELETE)
	@DeleteMapping("/{workIds}")
    public AjaxResult remove(@PathVariable Long[] workIds)
    {
        return toAjax(myWorkService.deleteMyWorkByWorkIds(workIds));
    }


    /**
     * 查询教学班题目
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:list')")
    @GetMapping("/selectTitle")
    public List<Map<String,Object>> selectTitle()
    {
        return myWorkService.selectTitle();
    }


    /**
     * 查询作品分数人数
     * @param myWork
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:list')")
    @GetMapping("/selectScore")
    public List selectScore(MyWork myWork)
    {
        return myWorkService.selectScore(myWork);
    }

    /**
     * 查询作品分数分布(0-59,60-69,70-79,80-89,90-100)
     * @param myWork
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyWork:list')")
    @GetMapping("/selectScores")
    public List selectScores(MyWork myWork)
    {
        return myWorkService.selectScores(myWork);
    }
}
