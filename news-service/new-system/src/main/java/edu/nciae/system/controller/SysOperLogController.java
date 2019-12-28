package edu.nciae.system.controller;

import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.system.domain.SysOperLog;
import edu.nciae.system.service.SysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录 提供者
 */
@RestController
@RequestMapping("operLog")
public class SysOperLogController extends BaseController {
    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 查询操作日志记录
     */
    @GetMapping("get/{operId}")
    public SysOperLog get(@PathVariable("operId") Integer operId) {
        return sysOperLogService.selectOperLogById(operId);
    }

    /**
     * 查询操作日志记录列表
     */
    @HasPermissions("monitor:operlog:list")
    @RequestMapping("list")
    public R list(SysOperLog sysOperLog) {
        startPage();
        return result(sysOperLogService.selectOperLogList(sysOperLog));
    }

    /**
     * 新增保存操作日志记录
     */
    @PostMapping("save")
    public void addSave(@RequestBody SysOperLog sysOperLog) {
        sysOperLogService.insertOperlog(sysOperLog);
    }

    /**
     * 删除操作日志记录
     */
    @HasPermissions("monitor:operlog:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        int i = sysOperLogService.deleteOperLogByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }

    @OperLog(title = "操作日志", businessType = BusinessType.CLEAN)
    @HasPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    public R clean() {
        sysOperLogService.cleanOperLog();
        return R.ok();
    }
}
