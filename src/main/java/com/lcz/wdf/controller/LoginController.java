package com.lcz.wdf.controller;

import com.lcz.wdf.constant.ErrorMessages;
import com.lcz.wdf.entity.Users;
import com.lcz.wdf.entity.common.ResponseMessage;
import com.lcz.wdf.entity.exception.BizException;
import com.lcz.wdf.entity.request.UserLoginRequest;
import com.lcz.wdf.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.Duration;

/**
 * 登录控制层
 *
 * @author li.chengzhen
 * @version 1.0
 * @date 2021/3/16 14:55
 */
@Validated
@RestController
@Slf4j
public class LoginController {

    @Resource private UserService userService;

    @PostMapping("/login")
    public void login(
            @Valid @RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request)
            throws BizException {
        UsernamePasswordToken token =
                new UsernamePasswordToken(
                        userLoginRequest.getAccount(), userLoginRequest.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
        } catch (IncorrectCredentialsException ice) {
            // 捕获密码错误异常
            throw new BizException("密码错误！");
        } catch (UnknownAccountException uae) {
            // 捕获未知用户名异常
            throw new BizException("用户名不存在！");
        } catch (ExcessiveAttemptsException eae) {
            // 捕获错误登录过多的异常
            throw new BizException("多次登录失败，请稍后重试！");
        }
        Users user = userService.findByAccount(userLoginRequest.getAccount());
        subject.getSession().setAttribute("user", user);

        log.info("真实id：{}", getIpAddr(request));

        // 设置session有效时间
        SecurityUtils.getSubject().getSession().setTimeout(Duration.ofDays(1).toMillis());

        log.info(
                "shiro中session设置的有效时间："
                        + SecurityUtils.getSubject().getSession().getTimeout()
                        + "毫秒");
    }

    /**
     * 获取真实id
     *
     * @param request {@link HttpServletRequest}
     * @return java.lang.String
     * @author li.chengzhen
     * @since 2020/10/28 10:31
     */
    public String getIpAddr(HttpServletRequest request) {
        String xIp = request.getHeader("X-Real-IP");
        String unKnown = "unknown";
        String xFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xFor) && !unKnown.equalsIgnoreCase(xFor)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xFor.indexOf(",");
            if (index != -1) {
                return xFor.substring(0, index);
            } else {
                return xFor;
            }
        }
        xFor = xIp;
        if (StringUtils.isNotEmpty(xFor) && !unKnown.equalsIgnoreCase(xFor)) {
            return xFor;
        }
        if (StringUtils.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xFor) || unKnown.equalsIgnoreCase(xFor)) {
            xFor = request.getRemoteAddr();
        }
        return xFor;
    }
    /**
     * 无权限时重定向登陆，返回401和错误信息
     *
     * @return void
     * @author lianglu
     * @since 2020/10/19 20:42
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @GetMapping("/unauthorized")
    public ResponseMessage unauthorized() {
        return ResponseMessage.errorWithStatus(401, ErrorMessages.UNAUTHORIZED_ROLE);
    }
}



