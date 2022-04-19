package com.ruoyi.schoolJob.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.schoolJob.domain.MyCourse;
import com.ruoyi.schoolJob.service.IMyCourseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.schoolJob.domain.MyClass;
import com.ruoyi.schoolJob.service.IMyClassService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * MyClassController
 *
 */
@RestController
@RequestMapping("/schoolJob/MyClass")
public class MyClassController extends BaseController
{
    @Autowired
    private IMyClassService myClassService;
    @Autowired
    private IMyCourseService myCourseService;

    /**
     * 查询MyClass列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:list')")
    @GetMapping("/list")
    public TableDataInfo list(MyClass myClass)
    {
        startPage();
        List<MyClass> list = myClassService.selectMyClassList(myClass);
        return getDataTable(list);
    }

    /**
     * 导出MyClass列表
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:export')")
    @Log(title = "MyClass", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MyClass myClass)
    {
        List<MyClass> list = myClassService.selectMyClassList(myClass);
        ExcelUtil<MyClass> util = new ExcelUtil<MyClass>(MyClass.class);
        util.exportExcel(response, list, "MyClass数据");
    }

    /**
     * 获取MyClass详细信息
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId)
    {
        return AjaxResult.success(myClassService.selectMyClassByClassId(classId));
    }

    /**
     * 新增MyClass
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:add')")
    @Log(title = "MyClass", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MyClass myClass)
    {
        return toAjax(myClassService.insertMyClass(myClass));
    }

    /**
     * 修改MyClass
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:edit')")
    @Log(title = "MyClass", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MyClass myClass)
    {
        return toAjax(myClassService.updateMyClass(myClass));
    }

    /**
     * 删除MyClass
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:remove')")
    @Log(title = "MyClass", businessType = BusinessType.DELETE)
	@DeleteMapping("/{classIds}")
    public AjaxResult remove(@PathVariable Long[] classIds)
    {
        return toAjax(myClassService.deleteMyClassByClassIds(classIds));
    }

    /**
     * 查询所有教师
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:teachers')")
    @GetMapping("/teachers")
    public List<SysUser> getTeacher()
    {
        List<SysUser> list = myClassService.getTeachers();
        return list;
    }

    /**
     * 查询所有学生
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:teachers')")
    @GetMapping("/students")
    public List<SysUser> getStudent()
    {
        List<SysUser> list = myClassService.getStudents();
        return list;
    }

    /**
     * 查询教学班关联的学生
     * @return
     */
    @PreAuthorize("@ss.hasPermi('schoolJob:MyClass:teachers')")
    @GetMapping("/getStudentByClassId/{classId}")
    public List<SysUser> getStudentByClassId(@PathVariable("classId") Long classId)
    {
        List<SysUser> list = myClassService.getStudentsByClassId(classId);
        return list;
    }

    /**
     * 修改教学班关联学生情况
     */
    @PostMapping("/changeUsers")
    public void changeUsers(@RequestParam String classId, @RequestParam String userList)
    {
        System.out.println(classId);
        Long classID = Long.parseLong(classId);
        List UserList = Arrays.asList(userList.split(","));
        for (int i = 0; i < UserList.size(); i++) {
            MyCourse myCourse = new MyCourse();
            myCourse.setClassId(classID);
            myCourse.setUserId(Long.parseLong((String) UserList.get(i)));
            myCourseService.insertMyCourse(myCourse);
        }
    }
}
