package edu.nciae.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SysOperLog implements Serializable {
    @ApiModelProperty(value = "日志主键")
    private Integer operId;

    @ApiModelProperty(value = "模块标题")
    private String title;

    @ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
    private Integer businessType;

    @ApiModelProperty(value = "方法名称")
    private String method;

    @ApiModelProperty(value = "请求方式")
    private String requestMethod;

    @ApiModelProperty(value = "操作类别（0其它 1后台用户 2手机端用户）")
    private Integer operatorType;

    @ApiModelProperty(value = "操作人员")
    private String operName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "请求URL")
    private String operUrl;

    @ApiModelProperty(value = "主机地址")
    private String operIp;

    @ApiModelProperty(value = "操作地点")
    private String operLocation;

    @ApiModelProperty(value = "请求参数")
    private String operParam;

    @ApiModelProperty(value = "操作状态（0正常 1异常）")
    private Integer status;

    @ApiModelProperty(value = "错误消息")
    private String errorMsg;

    @ApiModelProperty(value = "操作时间")
    private Date operTime;

    private String beginTime;

    private String endTime;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", operId=").append(operId);
        sb.append(", title=").append(title);
        sb.append(", businessType=").append(businessType);
        sb.append(", method=").append(method);
        sb.append(", requestMethod=").append(requestMethod);
        sb.append(", operatorType=").append(operatorType);
        sb.append(", operName=").append(operName);
        sb.append(", deptName=").append(deptName);
        sb.append(", operUrl=").append(operUrl);
        sb.append(", operIp=").append(operIp);
        sb.append(", operLocation=").append(operLocation);
        sb.append(", operParam=").append(operParam);
        sb.append(", status=").append(status);
        sb.append(", errorMsg=").append(errorMsg);
        sb.append(", operTime=").append(operTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}