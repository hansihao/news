package edu.nciae.system.controller;

import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.common.page.PageDomain;
import edu.nciae.system.domain.SysDictType;
import edu.nciae.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型 提供者
 */
@RestController
@RequestMapping("dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询字典类型
     */
    @GetMapping("get/{dictId}")
    public SysDictType get(@PathVariable("dictId") Integer dictId) {
        return sysDictTypeService.selectDictTypeById(dictId);

    }

    /**
     * 查询字典类型列表
     */
    @GetMapping("list")
    @HasPermissions("system:dict:list")
    public R list(SysDictType sysDictType, PageDomain page) {
        startPage();
        return result(sysDictTypeService.selectDictTypeList(sysDictType));
    }


    /**
     * 新增保存字典类型
     */
    @OperLog(title = "字典类型", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
    @PostMapping("save")
    public R addSave(@RequestBody SysDictType sysDictType) {
        int i = sysDictTypeService.insertDictType(sysDictType);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存字典类型
     */
    @OperLog(title = "字典类型", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:edit")
    @PostMapping("update")
    public R editSave(@RequestBody SysDictType sysDictType) {
        int i = sysDictTypeService.updateDictType(sysDictType);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除字典类型
     *
     * @throws Exception
     */
    @OperLog(title = "字典类型", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
    @PostMapping("remove")
    public R remove(String ids) throws Exception {
        int i = sysDictTypeService.deleteDictTypeByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }

}