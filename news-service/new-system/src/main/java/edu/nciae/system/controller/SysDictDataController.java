package edu.nciae.system.controller;

import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.system.domain.SysDictData;
import edu.nciae.system.service.SysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据
 */
@RestController
@RequestMapping("dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private SysDictDataService sysDictDataService;

    /**
     * 查询字典数据
     */
    @GetMapping("get/{dictCode}")
    public SysDictData get(@PathVariable("dictCode") Integer dictCode) {
        return sysDictDataService.selectDictDataById(dictCode);

    }

    /**
     * 查询字典数据列表
     */
    @GetMapping("list")
    @HasPermissions("system:dict:list")
    public R list(SysDictData sysDictData) {
        startPage();
        return result(sysDictDataService.selectDictDataList(sysDictData));
    }

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    @GetMapping("type")
    public List<SysDictData> getType(String dictType) {
        return sysDictDataService.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @GetMapping("label")
    public String getLabel(String dictType, String dictValue) {
        return sysDictDataService.selectDictLabel(dictType, dictValue);
    }


    /**
     * 新增保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.INSERT)
    @HasPermissions("system:dict:add")
    @PostMapping("save")
    public R addSave(@RequestBody SysDictData sysDictData) {
        int i = sysDictDataService.insertDictData(sysDictData);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 修改保存字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.UPDATE)
    @HasPermissions("system:dict:edit")
    @PostMapping("update")
    public R editSave(@RequestBody SysDictData sysDictData) {
        int i = sysDictDataService.updateDictData(sysDictData);
        return i > 0 ? R.ok() : R.error();
    }

    /**
     * 删除字典数据
     */
    @OperLog(title = "字典数据", businessType = BusinessType.DELETE)
    @HasPermissions("system:dict:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        int i = sysDictDataService.deleteDictDataByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }
}
