<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@page import="com.mysql.xiaomi.pojo.*" %>--%>
<%@page import="java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <script type="text/javascript">
        if ("${msg}" != "") {
            alert("${msg}");
        }
    </script>
    <c:remove var="msg"></c:remove>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bright.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <title></title>
</head>

<script>
    // 选中商品类型
    $(function (){
        $("#typeid").find("option[value = '${vo.typeid}']").attr("selected","selected");
    })
</script>

<script type="text/javascript">
    function allClick() {
        //取得全选复选框是否选中的状态
        let ischeck = $("#all").prop("checked");
        //将此状态赋值给每个商品列表里的复选框
        $("input[name=ck]").each(function (){
            this.checked = ischeck;
        })
    }

    function ckClick() {
        // 取得所有name=ck的被选中的复选框
        let length=$("input[name=ck]:checked").length;
        // 取得所有name=ck的复选框
        let len=$("input[name=ck]").length;
        // 判断单选框是否全部选中
        $("#all").prop("checked",len === length);
    }
</script>
<script>
    condition=function (){
        let pname=$("#pname").val();
        let typeid=$("#typeid").val();
        let lprice=$("#lprice").val();
        let hprice=$("#hprice").val();

        $.ajax({
            url:"/product/condition.do",
            type:"post",
            data:{
                "pname":pname,
                "typeid":typeid,
                "lprice":lprice,
                "hprice":hprice
            },
            success:function (){
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        })
    }
</script>
<body>
<div id="brall">
    <div id="nav">
        <p>商品管理>商品列表</p>
    </div>
    <div id="condition" style="text-align: center">
        <form id="myform">
            商品名称：<input name="pname" id="pname">&nbsp;&nbsp;&nbsp;
            商品类型：
            <select name="typeid" id="typeid">
                <option value="-1">请选择</option>
                <c:forEach items="${typeList}" var="pt">
                    <option value="${pt.typeId}">${pt.typeName}</option>
                </c:forEach>
            </select>&nbsp;&nbsp;&nbsp;
            价格：<input name="lprice" id="lprice">-<input name="hprice" id="hprice">
            <input type="button" value="查询" onclick="condition()">
        </form>
    </div>
    <br>
    <div id="table">

        <c:choose>
            <c:when test="${info.list.size()!=0}">

                <div id="top">
                    <input type="checkbox" id="all" onclick="allClick()" style="margin-left: 50px">&nbsp;&nbsp;全选
                    <a href="${pageContext.request.contextPath}/admin/addproduct.jsp">

                        <input type="button" class="btn btn-warning" id="btn1"
                               value="新增商品">
                    </a>
                    <input type="button" class="btn btn-warning" id="btn1"
                           value="批量删除" onclick="deleteBatch(${info.pageNum})">
                </div>
                <!--显示分页后的商品-->
                <div id="middle">
                    <table class="table table-bordered table-striped">
                        <tr>
                            <th></th>
                            <th>商品名</th>
                            <th>商品介绍</th>
                            <th>定价（元）</th>
                            <th>商品图片</th>
                            <th>商品数量</th>
                            <th>操作</th>
                        </tr>
                        <c:forEach items="${list}" var="p">
                            <tr>
                                <td valign="center" align="center"><input type="checkbox" name="ck" id="ck" value="${p.pId}" onclick="ckClick()"></td>
                                <td>${p.pName}</td>
                                <td>${p.pContent}</td>
                                <td>${p.pPrice}</td>
                                <td><img width="55px" height="45px"
                                         src="${pageContext.request.contextPath}/image_big/${p.pImage}" alt=""></td>
                                <td>${p.pNumber}</td>
                                <td>
                                    <button type="button" class="btn btn-info "
                                            onclick="one(${p.pId},${info.pageNum})">编辑
                                    </button>
                                    <button type="button" class="btn btn-warning" id="mydel"
                                            onclick="del(${p.pId},'${p.pImage}',${info.pageNum})">删除
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <!--分页栏-->
                    <div id="bottom">
                        <div>
                            <nav aria-label="..." style="text-align:center;">
                                <ul class="pagination">
                                    <li>
                                        <a href="javascript:ajaxsplit(${info.prePage})" aria-label="Previous">

                                            <span aria-hidden="true">«</span></a>
                                    </li>
                                    <c:forEach begin="1" end="${info.pages}" var="i">
                                        <c:if test="${info.pageNum==i}">
                                            <li>
                                                <a href="javascript:ajaxsplit(${i})"
                                                   style="background-color: grey">${i}</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${info.pageNum!=i}">
                                            <li>
                                                <a href="javascript:ajaxsplit(${i})">${i}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                    <li>
                                        <a href="javascript:ajaxsplit(${info.nextPage})" aria-label="Next">
                                            <span aria-hidden="true">»</span></a>
                                    </li>
                                    <li style=" margin-left:150px;color: #0e90d2;height: 35px; line-height: 35px;">总共&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pages}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <c:if test="${info.pageNum!=0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">${info.pageNum}</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                        <c:if test="${info.pageNum==0}">
                                            当前&nbsp;&nbsp;&nbsp;<font
                                            style="color:orange;">1</font>&nbsp;&nbsp;&nbsp;页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        </c:if>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div>
                    <h2 style="width:1200px; text-align: center;color: orangered;margin-top: 100px">暂时没有符合条件的商品！</h2>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<!--编辑的模式对话框-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">新增商品</h4>
            </div>
            <div class="modal-body" id="addTD">
                <form action="${pageContext.request.contextPath}/admin/product?flag=save" enctype="multipart/form-data"
                      method="post" id="myform">
                    <table>
                        <tr>
                            <td class="one">商品名称</td>
                            <td><input type="text" name="pname" class="two" class="form-control"></td>
                        </tr>
                        <!--错误提示-->
                        <tr class="three">
                            <td class="four"></td>
                            <td><span id="pnameerr"></span></td>
                        </tr>
                        <tr>
                            <td class="one">商品介绍</td>
                            <td><input type="text" name="pcontent" class="two" class="form-control"></td>
                        </tr>
                        <!--错误提示-->
                        <tr class="three">
                            <td class="four"></td>
                            <td><span id="pcontenterr"></span></td>
                        </tr>
                        <tr>
                            <td class="one">定价</td>
                            <td><input type="number" name="pprice" class="two" class="form-control"></td>
                        </tr>
                        <!--错误提示-->
                        <tr class="three">
                            <td class="four"></td>
                            <td><span id="priceerr"></span></td>
                        </tr>

                        <tr>
                            <td class="one">图片介绍</td>
                            <td><input type="file" name="pimage" class="form-control"></td>
                        </tr>
                        <tr class="three">
                            <td class="four"></td>
                            <td><span></span></td>
                        </tr>

                        <tr>
                            <td class="one">总数量</td>
                            <td><input type="number" name="pnumber" class="two" class="form-control"></td>
                        </tr>
                        <!--错误提示-->
                        <tr class="three">
                            <td class="four"></td>
                            <td><span id="numerr"></span></td>
                        </tr>


                        <tr>
                            <td class="one">类别</td>
                            <td>
                                <select name="typeid" class="form-control">
                                    <c:forEach items="${typeList}" var="type">
                                        <option value="${type.typeId}">${type.typeName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <!--错误提示-->
                        <tr class="three">
                            <td class="four"></td>
                            <td><span></span></td>
                        </tr>

                        <tr>
                            <td>
                                <input type="submit" class="btn btn-success" value="提交" class="btn btn-success">
                            </td>
                            <td>
                                <button type="button" class="btn btn-info" data-dismiss="modal">取消</button>
                            </td>
                        </tr>
                    </table>
                </form>

            </div>

        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>
</body>
<!--弹出新增模式对话框-->
<script type="text/javascript">
    $(function () {
        $(".btn-info").on("click", function () {
            //浏览不关，第二次打开时要清空
            $("#myModal").modal("hide");
        });
        //新增学生非空判断
        $(".btn-success").on("click", function () {
            $("#myModal").modal("hide");
        });
    });
</script>
<script type="text/javascript">
    function mysubmit() {
        $("#myform").submit();
    }

    //批量删除
    function deleteBatch(page) {
        let pname=$("#pname").val();
        let typeid=$("#typeid").val();
        let lprice=$("#lprice").val();
        let hprice=$("#hprice").val();

        if (confirm("确定删除吗")) {
            //取得所有被选中删除商品的pid
            let zhi=$("input[name=ck]:checked");
            let str="";
            let id="";
            if(zhi.length===0){
                alert("请选择将要删除的商品！");
            }else{
                // 有选中的商品，则取出每个选中商品的ID，拼提交的ID的数据
                if(confirm("您确定删除"+zhi.length+"条商品吗？")){
                //拼接ID
                    $.each(zhi,function () {
                        id=$(this).val();
                        if(id!=null)
                            str += id+",";
                    });

                    $.ajax({
                        url:"/product/deleteBatch.do",
                        data:{
                            "pids":str,

                            "page":page,
                            "pname":pname,
                            "typeid":typeid,
                            "lprice":lprice,
                            "hprice":hprice
                        },
                        type:"post",
                        dataType:"text",
                        success:function (massage) {
                            alert(massage);//弹出删除是否成功
                            $("#table").load("http://localhost:8080/admin/product.jsp #table")
                        },
                        error : function () {
                            alert("删除失败");
                        }
                    });
                }
            }
        }
    }

    //单个删除
    function del(pid,pImage,page){
        let pname=$("#pname").val();
        let typeid=$("#typeid").val();
        let lprice=$("#lprice").val();
        let hprice=$("#hprice").val();

        let pImage0=pImage+"";
        if (confirm("您确定要删除吗?")) {
            $.ajax({
                url:"/product/delete.do",
                data:{
                    "pid":pid,
                    "pImage":pImage0,

                    "page":page,
                    "pname":pname,
                    "typeid":typeid,
                    "lprice":lprice,
                    "hprice":hprice
                },
                type:"post",
                dataType:"text",
                success:function (massage){
                    alert(massage);
                    $("#table").load("http://localhost:8080/admin/product.jsp #table");
                }
            });
        }
    }


    function one(pid,page) {
        let pname=$("#pname").val();
        let typeid=$("#typeid").val();
        let lprice=$("#lprice").val();
        let hprice=$("#hprice").val();
        let src="?pname="+pname+"&typeid="+typeid+"&lprice="+lprice+"&hprice="+hprice+"&pid="+pid+"&page="+page;
        window.location.href="${pageContext.request.contextPath}/product/one.do"+ src;
    }
</script>

<!--分页的AJAX实现-->
<script type="text/javascript">

    function ajaxsplit(page){
        let pname=$("#pname").val();
        let typeid=$("#typeid").val();
        let lprice=$("#lprice").val();
        let hprice=$("#hprice").val();
        $.ajax({
            url:"/product/ajaxSplit.do",
            data:{
                "page":page,
                "pname":pname,
                "typeid":typeid,
                "lprice":lprice,
                "hprice":hprice
            },
            type:"post",
            success:function (){
                $("#table").load("http://localhost:8080/admin/product.jsp #table");
            }
        })
    }

</script>
</html>