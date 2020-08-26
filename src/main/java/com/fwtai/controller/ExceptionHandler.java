package com.fwtai.controller;

import com.fwtai.tool.ToolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局的系统异常处理json数据格式提示
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2017年5月14日 下午1:01:22
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Component
public class ExceptionHandler implements HandlerExceptionResolver{

    private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@Override
	public ModelAndView resolveException(final HttpServletRequest request,final HttpServletResponse response,final Object object,final Exception exception){
		ToolClient.responseException(response);
		logger.error("ExceptionHandler-->resolveException{}",exception);
		return null;
	}
}