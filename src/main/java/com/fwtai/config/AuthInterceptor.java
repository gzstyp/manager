package com.fwtai.config;

import com.fwtai.bean.PageFormData;
import com.fwtai.service.core.MenuService;
import com.fwtai.tool.ToolClient;
import com.fwtai.tool.ToolString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 权限认证拦截器-过滤器,其作用是进入到控制器Controller的请求之前进行调用
 * @作者 田应平
 * @版本 v1.0
 * @创建时间 2016年12月25日 23:40:29
 * @QQ号码 444141300
 * @官网 http://www.fwtai.com
*/
public final class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private MenuService menu;
	
	public String[] allowUrls;//不拦截的url访问地址

	public void setAllowUrls(String[] allowUrls){
		this.allowUrls = allowUrls;
	}
	
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,Object object, Exception exception) throws Exception {
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, Object object, ModelAndView modelAndView) throws Exception {
	}

	/**指定提交http请求方式*/
	protected final boolean methods(final String method){
		final String[] arr = {"get","post"};
		boolean b = false;
		for(int x = 0; x < arr.length; x++){
		    final String array = arr[x];
		    if(method.equalsIgnoreCase(array)){
		    	b = true;
		    	break;
		    }
		}
		return b;
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object object) throws Exception {
		final String method = request.getMethod();
		if(!methods(method)){
			ToolClient.responseObj(ToolClient.createJson(ConfigFile.code207,ConfigFile.illegality_handle),response);
			return false;
		}
        final boolean login = ToolClient.checkLogin(request);
        final String path = request.getRequestURI().replace(request.getContextPath(),"");
        if(!login){
            if(allowUrls != null && allowUrls.length >= 1){
                for (String url : allowUrls){
                    if(path.equals(url)){
                        return true;
                    }
                }
            }
        }
		final StringBuilder row_click = new StringBuilder();
		final String treeid = request.getParameter("treeid");
		if(!ToolString.isBlank(treeid) && login){
			final PageFormData pageFormData = new PageFormData();
			pageFormData.put("type",3);//type为3时查询的是行按钮权限javascript:;
			pageFormData.put("pId",treeid);
			final List<Map<String, Object>> listRows = menu.authRow(request,pageFormData);//获取有行按钮
			if(listRows != null && listRows.size() > 0){
				for(final Map<String, Object> rows : listRows){
					if(row_click.length() > 0)
						row_click.append("<span style=\"margin-left:4px;margin-right:4px;display:inline-block;\">|</span>");
					final Object text = rows.get("name");
					if(text.toString().indexOf("删除") > -1){
						row_click.append("<a href=\"javascript:;\" onclick=\"" + rows.get("uri") + "\" style=\"text-decoration:none;color:red;outline:none;\" title=\"" + rows.get("name") +"\">" + rows.get("name") + "</a>");
					} else {
						row_click.append("<a href=\"javascript:;\" onclick=\"" + rows.get("uri") + "\" style=\"text-decoration:none;color:#3b8cff;outline:none;\" title=\"" + rows.get("name") +"\">" + rows.get("name") + "</a>");
					}
					final Object uri = rows.get("uri");
					if(uri.toString().indexOf("thisPage.edit(1") > -1){
						request.setAttribute(ConfigFile.DOUBLE_CLICK,ConfigFile.DOUBLE_CLICK);
					}
				}
			}
		}
		if(path.matches(ConfigFile.expression) || login){//不需要验证的url地址
			if(ToolString.isBlank(row_click)){
				request.setAttribute(ConfigFile.HANDLE_ROW,null);
				request.setAttribute("row_click",null);//行按钮的数据权限
			}else{
				request.setAttribute(ConfigFile.HANDLE_ROW,ConfigFile.HANDLE_ROW);
				request.setAttribute("row_click",row_click);//行按钮的数据权限
			}
			if(!ToolString.isBlank(treeid)){
				request.setAttribute("authBtnId",treeid);//设置session的父级菜单的id然后页面去查询按钮权限
			}
			return true;
		}else{
			response.sendRedirect(request.getContextPath() + "/"+ConfigFile.TIMEOUT);
			return false;
		}
	}
}