package edu.nciae.system.controller;

import edu.nciae.common.auth.annotation.HasPermissions;
import edu.nciae.common.controller.BaseController;
import edu.nciae.common.domain.R;
import edu.nciae.common.log.annotation.OperLog;
import edu.nciae.common.log.enums.BusinessType;
import edu.nciae.system.domain.SysLogininfor;
import edu.nciae.system.service.SysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 系统访问记录
 */
@RestController
@RequestMapping("logininfor")
public class SysLogininforController extends BaseController {
    @Autowired
    private SysLogininforService sysLogininforService;

    /**
     * 查询系统访问记录列表
     */
    @GetMapping("list")
    public R list(SysLogininfor sysLogininfor) {
        startPage();
        return result(sysLogininforService.selectLogininforList(sysLogininfor));
    }

    /**
     * 新增保存系统访问记录
     */
    @PostMapping("save")
    public void addSave(@RequestBody SysLogininfor sysLogininfor) {
        sysLogininforService.insertLogininfor(sysLogininfor);
    }


    /**
     * 删除系统访问记录
     */
    @OperLog(title = "访问日志", businessType = BusinessType.DELETE)
    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("remove")
    public R remove(String ids) {
        int i = sysLogininforService.deleteLogininforByIds(ids);
        return i > 0 ? R.ok() : R.error();
    }

    @OperLog(title = "访问日志", businessType = BusinessType.CLEAN)
    @HasPermissions("monitor:loginlog:remove")
    @PostMapping("/clean")
    public R clean() {
        sysLogininforService.cleanLogininfor();
        return R.ok();
    }
}
