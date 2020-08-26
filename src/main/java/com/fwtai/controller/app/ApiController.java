package com.fwtai.controller.app;

import com.fwtai.controller.BaseController;
import com.fwtai.response.BusinessException;
import com.fwtai.response.EmBusinessError;
import com.fwtai.service.module.ApiService;
import com.fwtai.tool.ToolClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 对外公布api调用接口
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2018-01-09 16:33
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
@Controller
@RequestMapping("/api")
public final class ApiController extends BaseController{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApiService service;

    @RequestMapping("/read/{rows}")
    @ResponseBody
    public final void read(@PathVariable Integer rows,final HttpServletRequest request,final HttpServletResponse response){
        final String json = ToolClient.executeRows(rows);
        ToolClient.responseJson(json,response);
    }

    @RequestMapping("/hello")
    public String hello(final Model m){
        m.addAttribute("name", "spring-boot");
        return "hello";
    }

    @RequestMapping("/list")
    public String list(final Model model){
        final ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>(0);
        for (int i = 0; i < 5; i++) {
            final HashMap<String,String> map = new HashMap<String,String>();
            map.put("name","存储"+i);
            list.add(map);
        }
        model.addAttribute("message","下午好");
        model.addAttribute("list", list);
        return "listUser";
    }

    /**thymeleaf只支持HTML5*/
    @RequestMapping("/tf")
    public String helloHtml(final HashMap<String,Object> map){
        map.put("hello","from ApiController.tf");
        return"tf/hello";
    }

    /**公钥加密-私钥解密*/
    @RequestMapping("/publicPrivate")//http://localhost/api/publicPrivate?rows=1
    @ResponseBody
    public final void publicPrivate(final HttpServletRequest request,final HttpServletResponse response){
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
        } catch (final Exception e) {
            e.printStackTrace();
            ToolClient.responseException(response);
        }
    }

    @RequestMapping("/handlerException")// http://127.0.0.1/api/handlerException?values=hgltzz
    @ResponseBody
    public final void handlerException(final HttpServletRequest request,final HttpServletResponse response) throws BusinessException{
        final String rows = request.getParameter("values");
        HashMap<String,String> formParams = getFormParams();
        //int i = 5/0;//在测试可以把本行注释掉
        HashMap<String,Object> map = null;
        if(map == null){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        map.put("k",1024);
    }
}