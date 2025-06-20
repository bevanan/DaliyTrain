package com.bevan.design.chain.origin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public static void main(String[] args) throws ParseException {
        AuthController controller = new AuthController();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse("2022-11-12 00:00:00");

        // 设置申请流程

        // 三级审核
        // 1.调用doAuth方法,模拟发送申请人相关信息
        AuthInfo info1 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态:  " + info1.getInfo());

        /**
         * 2.模拟进行审核操作, 虚拟审核人ID: 1000013
         * 调用auth() 方法进行审核操作, 就是向Map中添加一个 审核人ID和申请单ID
         */
        AuthService.auth("1000013", "100001000010000");
        System.out.println("三级负责人审批完成,审批人: 王工");
        System.out.println("===========================================================================");

        // 二级审核
        AuthInfo info2 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态:  " + info2.getInfo());

        AuthService.auth("1000012", "100001000010000");
        System.out.println("二级负责人审批完成,审批人: 张经理");
        System.out.println("===========================================================================");

        // 一级审核
        AuthInfo info3 = controller.doAuth("研发小周", "100001000010000", date);
        System.out.println("当前审核状态:  " + info3.getInfo());

        AuthService.auth("1000011", "100001000010000");
        System.out.println("一级负责人审批完成,审批人: 罗总");
    }
}
