package xyz.hhang.boot.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import xyz.hhang.boot.exception.ServiceException;
import xyz.hhang.boot.service.UserService;
import xyz.hhang.boot.service.impl.MailService;
import xyz.hhang.boot.utils.ResponseDataBuilder;
import xyz.hhang.boot.utils.VerifyCodeUtil;
import xyz.hhang.boot.utils.VerifyImageUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.apache.commons.jexl2.parser.ParserConstants.le;
import static org.apache.commons.jexl2.parser.ParserConstants.ne;

@RestController
// 用来标识当前controller的访问路径的
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;


    @RequestMapping("/index")
    public ModelAndView index(HttpSession session) {
        return returnLogin("", session);
    }

    //===============验证码转化 图片部分
    @GetMapping(value = "/verifyCode")
    public void verifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        String code = VerifyImageUtil.generateVerifyCode(4);
        session.setAttribute("code", code);
        VerifyImageUtil.outputImage(170, 50, response.getOutputStream(), code);
    }

//    @PostMapping(value = "/toregister")
//    public Map<String,Object> register(@RequestBody Map<String,Object> user) throws ServiceException {
//        ResponseDataBuilder responseDataBuilder = new ResponseDataBuilder();
//        try{
//                Map<String, Object> result = userService.saveUser(user);
//                responseDataBuilder.success().data(result);
//            } catch (ServiceException e) {
//                e.printStackTrace();
//                responseDataBuilder.error().message(e.getMessage());
//            } catch (Exception e){
//                e.printStackTrace();
//                responseDataBuilder.error().message("注册失败");
//            }
//        return responseDataBuilder.build();
//    }
    //员工查询
    @GetMapping(value = "/findUser")
    public Map<String, Object> findUser(@RequestParam Map<String, Object> param) {
        try {
            PageInfo<Map<String, Object>> byCon = userService.findUser(param);
            return new ResponseDataBuilder().success().data(byCon).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("查询失败").build();
        }
    }
    //登录接口
    @PostMapping(value = "/tologin")
    public Map<String, Object> login(@RequestBody Map<String, Object> param, HttpSession session) {
        String inputCode = (String) param.get("verify");
        String code = (String) session.getAttribute("code");
        if (!inputCode.equalsIgnoreCase(code)) {
            return new ResponseDataBuilder().error().message("验证码输入错误").build();
        }
        try {
            // 向获取用户输入的用户名密码,校验
            Map<String, Object> loginResult = userService.login(param);
            if (loginResult != null) {
                session.setAttribute("userInfo", loginResult);
                loginResult.remove("password");
                //=================menu的查询====================
                List<Map<String, Object>> menuList = userService.findMenu(param);
                loginResult.put("menu", menuList);
                return new ResponseDataBuilder().success().data(loginResult).build();
            } else {
                return new ResponseDataBuilder().error().message("登陆失败").build();
            }
        } catch (Exception se) {
            se.printStackTrace();
            return new ResponseDataBuilder().error().message(se.getMessage()).build();
        }

    }

    @DeleteMapping("/delete")
    public Map<String, Object> delete(@RequestParam(value = "ids") Long[] ids) {
        try {
            int res = userService.delete(Arrays.asList(ids));
            return new ResponseDataBuilder().success().data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }

    @PostMapping(value = "/upUser")
    public Map<String, Object> upStatus(@RequestBody Map<String, Object> param) {
        try {
            int res = userService.upUser(param);
            return new ResponseDataBuilder().success().message("启用成功").data(res).build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改失败").build();
        }
    }

    @PostMapping(value = "/saveUser")
    public Map<String, Object> saveUser(@RequestBody Map<String, Object> user) throws ServiceException {
        ResponseDataBuilder responseDataBuilder = new ResponseDataBuilder();
        try {
            Map<String, Object> result = userService.saveUser(user);
            responseDataBuilder.success().data(result);
        } catch (ServiceException e) {
            e.printStackTrace();
            responseDataBuilder.error().message(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            responseDataBuilder.error().message("注册失败");
        }
        return responseDataBuilder.build();
    }

    @PostMapping("/saveDep_role")
    public Map<String,Object> saveDep_acc_role(@RequestBody Map<String,Object> depart) throws ServiceException{
        Map<String, Object> res = userService.saveDep_acc_role(depart);
        return new ResponseDataBuilder().success().message("成功").data(res).build();
    }


    //=========发送验证码部分
    @GetMapping("/resetCode")
    public Map<String, Object> resetCode(String mail, HttpServletRequest request) {
        try {
            String code = VerifyCodeUtil.getCode(4);
            System.out.println(code);
            request.getServletContext().setAttribute(mail, code);
            ExecutorService es = Executors.newFixedThreadPool(1);
            // 定时器， 300秒后清理application中的验证码
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
                    ServletContext servletContext = webApplicationContext.getServletContext();
                    servletContext.removeAttribute(mail);
                    System.out.println(mail + "的验证码已失效");
                }
            }, 1000 * 300);
            // 发送邮件
            mailService.sendResetMail(mail, code);
            return new ResponseDataBuilder().success().message("验证码已经发送至您的邮箱，请在30分之内输入，否则验证码失效，需重新申请").build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("验证码发送失败").build();
        }
    }

    ;


    //=====执行重置密码逻辑 并且验证 验证码是否匹配
    @PostMapping("/reset")
    public Map<String, Object> reset(@RequestBody Map<String, Object> param, HttpServletRequest request) {
        String mail = (String) param.get("mail");
        String CheckCode = (String) param.get("checkCode");
        String systemCode = (String) request.getServletContext().getAttribute(mail);
        if (CheckCode.equalsIgnoreCase(systemCode)) {
            return new ResponseDataBuilder().success().build();
        } else {
            return new ResponseDataBuilder().error().message("验证码错误").build();
        }
    }

    //=========修改密码
    @PostMapping("/doChangePass")
    public Map<String, Object> doChange(@RequestBody Map<String, Object> param) {
        try {
            userService.changePass(param);
            return new ResponseDataBuilder().success().message("密码修改成功请登录").build();
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message("修改密码失败").build();
        }
    }

    @GetMapping("/exist/{mail}")
    public Map<String, Object> exist(@PathVariable String mail) {
        try {
            Map<String, Object> exist = userService.exist(mail);
            if (exist != null) {
                return new ResponseDataBuilder().error().message("用户存在").build();
            } else {
                return new ResponseDataBuilder().success().build();
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            return new ResponseDataBuilder().error().message(e.getMessage()).build();
        }
    }

    //======⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇⬇验证码部分公共模组
    private ModelAndView returnLogin(String message, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("user/login");
        modelAndView.addObject("message", message);
        //生成验证码
        String codeNew = VerifyImageUtil.generateVerifyCode(4);
        //把验证码存到session里
        session.setAttribute("code", codeNew);
        modelAndView.addObject("code", codeNew);
        return modelAndView;
    }

}
