package com.baidu.exception;

import com.baidu.vo.Msg;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by edwardsbean on 14-10-29.
 */
@Component
public class CustomHandlerExceptionResolver extends DefaultHandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1) || request
                .getParameter("callback") != null)) {
            //同步请求
            //TODO:未测试。
            //判断异常类型:
            super.resolveException(request, response, handler, ex);

        } else {
            // 异步请求
            try {
                PrintWriter writer = response.getWriter();
                String callback = request.getParameter("callback");
                Gson gson = new Gson();
                Msg msgcode = new Msg();
                msgcode.setCode("1");
                msgcode.setMsg(ex.getLocalizedMessage());

                if (callback != null) {
                    callback = callback + "(" + gson.toJson(msgcode) + ")";
                    writer.write(callback);
                } else {
                    writer.write(gson.toJson(msgcode));
                }
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
