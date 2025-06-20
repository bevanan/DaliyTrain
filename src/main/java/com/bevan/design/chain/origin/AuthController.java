package com.bevan.design.chain.origin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AuthController {
    // 审核接口
    public AuthInfo doAuth(String name, String orderId, Date authDate) throws ParseException {
        // 三级审批
        Date date = null;
        // 查询是否存在审核信息,查询条件: 审核人ID+订单ID,返回Map集合中的Date
        date = AuthService.queryAuthInfo("1000013", orderId);

        // 如果为空,封装AuthInfo信息(待审核)返回
        if (date == null) {
            return new AuthInfo("0001", "单号: " + orderId, "状态: 等待三级审批负责人进行审批");
        }

        // 二级审批
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 时间格式化
        // 二级审核人主要审核双十一之前, 11-01 ~ 11-10号的请求,所以要对传入的审核时间进行判断
        // 审核时间 大于 2022-11-01 并且  小于 2022-11-10,Date1.after(Date2),当Date1大于Date2时，返回TRUE,Date1.before(Date2)，当Date1小于Date2时，返回TRUE
        if (authDate.after(f.parse("2022-11-01 00:00:00")) && authDate.before(f.parse("2022-11-10 00:00:00"))) {
            // 条件成立,查询二级审核的审核信息
            date = AuthService.queryAuthInfo("1000012", orderId);

            // 如果为空,还是待二级审核人审核状态
            if (date == null) {
                return new AuthInfo("0001", "单号: " + orderId, "状态: 等待二级审批负责人进行审批");
            }
        }

        // 一级审批
        // 审核范围是在11-11日 ~ 11-31日
        if (authDate.after(f.parse("2022-11-11 00:00:00")) && authDate.before(f.parse("2022-11-31 00:00:00"))) {
            date = AuthService.queryAuthInfo("1000011", orderId);
            if (date == null) {
                return new AuthInfo("0001", "单号: " + orderId, "状态: 等待一级审批负责人进行审批");
            }
        }

        return new AuthInfo("0001", "单号: " + orderId, "申请人:" + name + ", 状态: 审批完成!");
    }
}
