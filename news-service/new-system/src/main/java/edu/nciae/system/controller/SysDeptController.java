package edu.nciae.system.controller;

import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.system.domain.SysDept;
import edu.nciae.system.service.SysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * 部门 提供者
 */
@RestController
@RequestMapping("dept")
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 查询部门列表
     */
    @GetMapping("list")
    public R list(SysDept sysDept) {
        startPage();
        return result(sysDeptService.selectDeptList(sysDept));
    }

    /**
     * 新增保存部门
     */
    @PostMapping("save")
    public R addSave(@RequestBody SysDept sysDept) {
        int i = sysDeptService.insertDept(sysDept);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存部门
     */
    @PostMapping("update")
    public R editSave(@RequestBody SysDept sysDept) {
        int i = sysDeptService.updateDept(sysDept);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除部门
     */
    @PostMapping("remove/{deptId}")
    public R remove(@PathVariable("deptId") Integer deptId) {
        int i = sysDeptService.deleteDeptById(deptId);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 加载角色部门（数据权限）列表树
     */
    @GetMapping("/role/{roleId}")
    public Set<String> deptTreeData(@PathVariable("roleId") Integer roleId) {
        if (null == roleId || roleId <= 0) return null;
        return sysDeptService.roleDeptIds(roleId);
    }

}
