<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%
  final String path = request.getContextPath();
  final String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<head>
  <meta charset="utf-8"/>
  <base href="<%=basePath%>">
  <title>系统提示</title>
  <link rel="shortcut icon" href="images/favicon.ico"/>
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <style type="text/css">
    *{
      font-size:14px;
    }
    a{
      text-decoration:none;
      outline:none;
    }
    .box{
      width:517px;
      height:455px;
      position:absolute;
      left:50%;
      top:50%;
      margin:-227px 0 0 -258px;
      background-image:url('/images/home/img404.png');
    }
  </style>
</head>
<body>
<div class="box">
  不要到处乱逛,还没使用引路者URL服务吧?出事了吧?快去申请用用吧!<br>
  <a href="http://www.yinlz.com" target="_blank">免费申请使用</a>
</div>
</body>
</html>