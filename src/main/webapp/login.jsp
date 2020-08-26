<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
final String path = request.getContextPath();
final String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<base href="<%=basePath%>">
<title>${platformName}</title>
<link rel="shortcut icon" href="images/favicon.ico" />
</head>
<body>
	<script type="text/javascript" >
		window.location.href = "<%=basePath%>login";
	</script>
</body>z
</html>