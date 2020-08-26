package com.fwtai.controller.app;

import com.fwtai.config.ConfigFile;
import com.fwtai.controller.BaseController;
import com.fwtai.response.BusinessException;
import com.fwtai.response.EmBusinessError;
import com.fwtai.service.module.ApiService;
import com.fwtai.tool.ToolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/app")
public final class AppController extends BaseController{

	@Autowired
	private ApiService service;

    private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**测试json*/
	@RequestMapping("/json")
	@ResponseBody
	public final void json(final HttpServletResponse response){
		String rows = getPageFormData().getString("rows");
		if (rows == null || rows.length() <=0){
			rows = "0";
		}
		int code = 0 ;
		try {
			code = Integer.parseInt(rows);
		} catch (Exception e){
			ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,"参数有误"),response);
			return;
		}
		final String json = ToolClient.executeRows(code);
		ToolClient.responseJson(json,response);
	}
	
	/**测试json-restful风格:url:/app/json/2*/
	@RequestMapping("/json/{rows}")
	@ResponseBody
	public final void json(@PathVariable String rows,final HttpServletResponse response){
		if (rows == null || rows.length() <=0){
			rows = "0";
		}
		int code = 0;
		try {
			code = Integer.parseInt(rows);
		} catch (Exception e){
			ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,"参数有误"),response);
			return;
		}
		final String json = ToolClient.executeRows(code);
		ToolClient.responseJson(json,response);
	}
	
	/**测试2-json-restful风格;url:/app/101/js*/
	@RequestMapping("/{rows}/js")
	@ResponseBody
	public final void js(@PathVariable String rows,final HttpServletResponse response){
		if (rows == null || rows.length() <=0){
			rows = "0";
		}
		int row = 0;
		try {
			row = Integer.parseInt(rows);
		} catch (Exception e){
			ToolClient.responseJson(ToolClient.createJson(ConfigFile.code202,"参数有误"),response);
			return;
		}
		final String json = ToolClient.executeRows(row);
		ToolClient.responseJson(json,response);
	}
	
	/**测试jsonArray*/
	@RequestMapping("/jsonArray")
	@ResponseBody
	public final void jsonArray(final HttpServletResponse response){
		final ArrayList<HashMap<String,Object>> roles = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < 5; i++){
			final HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("key"+i,i);
			roles.add(map);
		}
		final String json = ToolClient.queryJson(roles);
		ToolClient.responseJson(json,response);
	}
	
	/**跨域请求*/
	@CrossOrigin(origins = "*",maxAge = 3600)//*表示接收所有请求,@CrossOrigin(origins = "http://localhost:9000"),表示仅接受http://localhost:9000发送来的跨域请求。
	@RequestMapping("/crossOrigin")
	@ResponseBody
	public final void crossOrigin(final HttpServletResponse response){
		final ArrayList<HashMap<String,Object>> roles = new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < 5; i++){
			final HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("key"+i,i);
			roles.add(map);
		}
		final String json = ToolClient.queryJson(roles);
		ToolClient.responseJson(json,response);
	}

	/**公钥加密-私钥解密*/
	@RequestMapping("/publicPrivate")//http://localhost/api/publicPrivate?rows=1
	@ResponseBody
	public final void publicPrivate(final HttpServletRequest request, final HttpServletResponse response){
		final String rows = request.getParameter("rows");
		try {
			final int row = Integer.valueOf(rows);
			final String json = service.publicPrivate(row);
			ToolClient.responseObj(json,response);
		} catch (Exception e) {
			e.printStackTrace();
			ToolClient.responseException(response);
		}
	}

	/**私钥加密-->公钥解密*/
	@RequestMapping("/privatePublic")//http://localhost/api/privatePublic?rows=0
	@ResponseBody
	public final void privatePublic(final HttpServletRequest request,final HttpServletResponse response){
		final String rows = request.getParameter("rows");
		try {
			final int row = Integer.valueOf(rows);
			final String json = service.privatePublic(row);
			ToolClient.responseObj(json,response);
		} catch (Exception e) {
			e.printStackTrace();
			ToolClient.responseException(response);
		}
	}

    @RequestMapping("/handlerException")//http://localhost/api/handlerException
    @ResponseBody
    public final void handlerException(final HttpServletRequest request,final HttpServletResponse response) throws BusinessException{
        final String rows = request.getParameter("rows");
        //int i = 5/0;//在测试可以把本行注释掉
        HashMap<String,Object> map = null;
        if(map == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        map.put("k",1024);
    }
}