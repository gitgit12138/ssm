package com.qfedu.controller;

import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jayus
 * @create 2020-08-10-2:42 下午
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登陆提交地址，和applicationContext-shiro.xml中配置的loginurl一致
     * @param req
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest req) {
        //这里不再需要开发者手动获取前端传递的用户名和密码，其实这里已经为用户做好了登陆的判断
        //在进入处理器之前，已经在过滤器中进行了登陆判断，将结果放在req作用域中，键的名字
        //exceptionClassName保存登陆出现的异常信息
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");

        if (exceptionClassName != null){
            //有异常信息代表登陆失败
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                System.out.println("账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                System.out.println("用户名/密码错误");
            } else if("randomCodeError".equals(exceptionClassName)){
                System.out.println("验证码错误");
            } else{
                System.out.println("未知错误");;//最终在异常处理器生成未知错误
            }
            return "redirect:/html/userLogin.html";
        }

        //认证成功，会把用户保存在shiro自己的session中，利用凭证来获取
        // 此返回值不处理登陆成功（认证成功），shiro认证成功会自动跳转到上一个请求路径。仅在未认证时有效
        return "redirect:/html/userLogin.html";
    }

    @RequestMapping("/uList")
    public String uList(){
        System.out.println("访问用户列表成功");
        return "forward:/index.jsp";
    }

    @RequestMapping("/uCreate")
    public String uCreate(){
        System.out.println("增加用户成功");
        return "forward:/index.jsp";
    }
}
