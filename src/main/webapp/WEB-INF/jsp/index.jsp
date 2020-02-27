<%--
  Created by IntelliJ IDEA.
  User: lingjunhao
  Date: 2019/11/3
  Time: 10:36 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath }/fileUpload" method="POST" enctype="multipart/form-data">
    <input type="file" name="img"/>
    <input type="submit" value="提交"/>
</form>
</body>
</html>
