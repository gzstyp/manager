<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>${platformName}</title>
		<link rel="shortcut icon" href="images/favicon.ico" />
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" />
		<!-- icheck样式库 -->
		<link rel="stylesheet" type="text/css" href="resources/lib/icheck/checkboxRadio.css"/>
		<link rel="stylesheet" type="text/css" href="resources/lib/icheck/blue.css"/>
		<link rel="stylesheet" type="text/css" href="resources/lib/easyui/themes/metro/easyui.css">   
		<link rel="stylesheet" type="text/css" href="resources/lib/easyui/themes/icon.css" />
		<link rel="stylesheet" type="text/css" href="resources/system/css/core/page.core.control.css"/>
		<link rel="stylesheet" type="text/css" href="resources/lib/ztree/css/metroStyle/metroStyle.css" media="screen"/>
		<link rel="stylesheet" type="text/css" href="resources/css/select.css" media="screen"/>
		<link rel="stylesheet" type="text/css" href="resources/css/buttons.css" media="screen"/>
		<link rel="stylesheet" type="text/css" href="resources/css/table.view.css" media="screen"/>
		<!-- 下拉菜单样式 -->
		<link rel="stylesheet" type="text/css" href="resources/css/drop-down.css" media="screen"/>
        <style>
            .panel-title,.panel-header{
                height:24px;
                line-height:24px;
                vertical-align: middle;
                font-size:14px;
            }
        </style>
        <script type="text/javascript">
            (function() {
                var OriginTitile = document.title, titleTime;
                document.addEventListener('visibilitychange', function() {
                    if (document.hidden) {
                        document.title = '你已离开请注意退出';
                        clearTimeout(titleTime);
                    } else {
                        document.title = '很好,欢迎回来!';
                        titleTime = setTimeout(function() {
                            document.title = OriginTitile;
                        },2000);
                    }
                });
            })();
        </script>
	</head>
<body class="easyui-layout">
	<jsp:include page="layout/north.jsp"/>
	<jsp:include page="layout/west.jsp"/>
	<div region="center" style="overflow: hidden;">
		<div id="bodyContainer" class="easyui-panel" data-options="fit:true,border:false,title:'欢迎使用本系统云计算管理服务平台',loadingMessage:'正在加载页面……',onBeforeLoad:function(){},onLoadError:function(res){if(res.status=='404'){layerFn.center(AppKey.code.code199,'主人,您访问的页面不存在!');}else if(res.status=='0'){layerFn.alert('网络中断,连接服务器失败!');}else{layerFn.alert('抱歉,系统出现错误,代码:'+res.status+','+res.statusText);}}" >
			<div style="height:100%;height:100%;"></div>
		</div>
	</div>
	<script type="text/javascript" src="resources/lib/easyui/jquery.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="ueditor/ueditor.config.js"></script>
	<script type="text/javascript" charset="utf-8" src="ueditor/ueditor.all.js"> </script>
	<script type="text/javascript" charset="utf-8" src="ueditor/lang/zh-cn/zh-cn.js"></script>
	<script type="text/javascript" src="resources/lib/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="resources/lib/layer/layer.js"></script>
	<script type="text/javascript" src="resources/lib/jquery-ui.min.js"></script>
	<script type="text/javascript" src="resources/lib/select-widget-min.js"></script>
	<script type="text/javascript" src="resources/lib/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="resources/lib/easyui/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="resources/lib/datagrid.tips.js"></script>
	<script type="text/javascript" src="resources/lib/icheck/icheck.min.js"></script><!-- icheck库 -->
	<script type="text/javascript" src="resources/lib/jquery.placeholder.min.js"></script>
	<script type="text/javascript" src="resources/lib/page.common.js"></script><!-- 自定义封装及插件库 -->
	<script type="text/javascript" src="resources/lib/jquery/select.area.js"></script>
	<script type="text/javascript" src="resources/lib/ztree/js/jquery.ztree.core.min.js"></script>
	<script type="text/javascript" src="resources/system/js/core/page.core.control.js"></script>
	<script type="text/javascript" src="resources/system/js/core/page.core.menu.js"></script>
</body>
</html>