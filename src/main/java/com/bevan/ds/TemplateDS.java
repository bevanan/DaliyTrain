package com.bevan.ds;

/**
 * 账户抽象类
 **/
abstract class Account {
    //step1 具体方法-验证用户信息是否正确
    public boolean validate(String account,String password){
        System.out.println("账号: " + account + ",密码: " + password);
        if(account.equalsIgnoreCase("tom") &&
                password.equalsIgnoreCase("123456")){
            return true;
        }else{
            return false;
        }
    }

    //step2 抽象方法-计算利息
    public abstract void calculate();

    //step3 具体方法-显示利息
    public void display(){
        System.out.println("显示利息!");
    }

    //模板方法
    public void handle(String account,String password){
        if(!validate(account,password)){
            System.out.println("账户或密码错误!!");
            return;
        }
        calculate();
        display();
    }
}

/**
 * 借款一个月
 **/
class LoanOneMonth extends Account{
    @Override
    public void calculate() {
        System.out.println("借款周期30天,利率为10%!");
    }
}

/**
 * 借款七天
 **/
class LoanSevenDays extends Account{
    @Override
    public void calculate() {
        System.out.println("借款周期7天,无利息!仅收取贷款金额1%的服务费!");
    }

    @Override
    public void display() {
        System.out.println("七日内借款无利息!");
    }

}

public class TemplateDS {
    public static void main(String[] args) {
        Account a1 = new LoanSevenDays();
        a1.handle("tom","123456");

        System.out.println("==========================");

        Account a2 = new LoanOneMonth();
        a2.handle("tom","123456");
    }
}