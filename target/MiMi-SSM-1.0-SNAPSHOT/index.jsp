<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body{
            background-color: #FBDA61;
            background-image: linear-gradient(45deg, #FBDA61 0%, #FF5ACD 100%);
            display:flex;
            justify-content:center;
            align-items:center;
        }
        #in{
            text-decoration: none;
            font-family: "Times New Roman", Georgia, Serif, serif;
            font-size: 100px;
            font-style:oblique;
        }
        #in:hover{
            color: #8d45b9;
        }
    </style>
</head>
<body>
    <a href="${pageContext.request.contextPath}/Admin/addlogin.do" id="in">in</a>
</body>
</html>
