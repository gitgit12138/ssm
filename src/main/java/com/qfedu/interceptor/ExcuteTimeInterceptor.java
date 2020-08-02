package com.qfedu.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jayus
 * @create 2020-08-02-8:28 下午
 * 自定义拦截器实现性能监控：处理器执行的时间，如果超过5s则认为代码需要优化修改
 * 使用拦截器，在进入处理器之前记录一个时间点，在处理器执行完毕之后，再记录一个时间点，结束减去开始，即处理器执行的时间
 * 拦截器也是aop思想的一种实现
 * 和过滤器的区别是过滤器可以过滤所有类型的请求，但是拦截器只针对处理器
 */
public class ExcuteTimeInterceptor implements HandlerInterceptor {
    /*
    在多线程下会出现一个bug，执行时间不准确，原因是拦截器默认是单例的。
    假如更改为多例，每次都需要创建一个新的拦截器对象，占用内存太大
    假如使用同步锁解决，那个一个用户访问完成之后，另一个用户才可以继续访问，效率极低
    解决方案：
    可以使用线程副本ThreadLocal
    这样我们操作的变量不再是一个直接声明类中的属性了，因为这样所有的线程都共享它，都可以对它进行操作
    使用ThreadLocal之后，将数据保存在副本中，这样每一个线程想要操作它，ThreadLocal负责为线程提供一个变量的副本，
    线程对变量任何的操作都是对副本的改变，不会影响其他线程的副本
     */

    private long startTime;

    private ThreadLocal<Long> threadLocal = new ThreadLocal<Long>();
    /**
     * 预处理，在进入处理器之前会执行preHandle方法
     * @param request 请求
     * @param response 响应
     * @param handler 具体的处理器
     * @return 返回是一个布尔值，如果是false表示拦截，如果是true则表示继续调用下面的controller
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle");
        startTime = System.currentTimeMillis();
        //startTime放到threadLocal之后会和当前线程进行绑定，也就是为当前线程创建创建一个独立的startTime变量
        threadLocal.set(startTime);
        return true;
    }

    /**
     * 后置处理，在离开处理器之后会执行的方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        long endTime = System.currentTimeMillis();
        //取出数据副本
        long currentStartTime = threadLocal.get();
        System.out.println("执行时间"+(endTime - currentStartTime));
    }

    /**
     * 一个请求完整的结束，也就是响应完毕之后需要执行的方法
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
