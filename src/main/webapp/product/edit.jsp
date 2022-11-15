<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Edit Product</title>
    <%@include file="/dashboard/layout/head.jsp" %>

</head>

<body>

<!-- Begin page -->
<div id="wrapper">


    <!-- Topbar Start -->
    <div class="navbar-custom" style="background-color: #eef0f0">
        <ul class="list-unstyled topnav-menu float-right mb-0">
            <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect" data-toggle="dropdown" href="#"
               role="button" aria-haspopup="false" aria-expanded="false">
                <img src="/dashboard/assets/avatar-1.jpg" alt="user-image" class="rounded-circle">
                <span class="pro-user-name ml-1">
                            Thanh Tùng <i class="mdi mdi-chevron-down"></i>
                        </span>
            </a>
        </ul>

        <!-- LOGO -->
        <div class="logo-box">
            <a href="/users?action=menu" class="logo text-center logo-light">
                    <span class="logo-lg">
                        <img src="/dashboard/assets/images/logo-light.png" alt="" height="18">
                        <!-- <span class="logo-lg-text-dark">Velonic</span> -->
                    </span>
                <span class="logo-sm">
                        <!-- <span class="logo-lg-text-dark">V</span> -->
                        <img src="/dashboard/assets/images/logo-sm.png" alt="" height="22">
                    </span>
            </a>
        </div>

        <!-- LOGO -->


    </div>
    <!-- end Topbar -->
    <!-- ========== Left Sidebar Start ========== -->

    <!-- Left Sidebar End -->
    <%@include file="/dashboard/layout/left-sidebar.jsp" %>
    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->

            <!-- end col -->


        </div>
        <!-- End row -->

        <%--        LỌm2--%>

        <%--        End Lọm 2--%>

        <!-- LỌM -->
        <div class="card-body">
            <h4 style="text-align: center; font-size: 20px; font-family: 'American Typewriter' "
                class="header-title mb-4">EDIT PRODUCT
            </h4>
            <c:choose>
                <c:when test="${errors1 == null }">
                    <form method="post" enctype="multipart/form-data" action="/products?action=edit">
                        <c:if test="${product != null}">
                            <input type="hidden" name="id" value="<c:out value='${product.getId()}' />">
                        </c:if>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="col-form-label" style="color: black ">Name</label>
                                <input type="text" class="form-control" name="name"
                                       style="color: black "
                                       value="<c:out value='${product.getName()}' />"
                                >
                            </div>
                            <div class="form-group col-md-6">
                                <label class="col-form-label" style="color: black ">Price</label>
                                <input type="text" class="form-control" name="price" style="color: black "
                                       value="<c:out value='${product.getPrice()}' />">
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="form-group col-md-6">
                                <label class="col-form-label" style="color: black ">CATAGORY</label>
                                <select class="form-control" name="catagory" style="color: black ">
                                    <<c:forEach items="${applicationScope.listCatagory }" var="catagory">

                                    <c:choose>
                                        <c:when test="${catagory.getId() == product.getCatagory() }">
                                            <option value="${catagory.getId() }"
                                                    selected>${catagory.getName() }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${catagory.getId() }">${catagory.getName() }</option>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                </select>

                            </div>

                            <div class="form-group col-md-6">
                                <label class="col-form-label" style="color: black ">Status</label>
                                <select class="form-control" name="status" style="color: black ">
                                    <<c:forEach items="${applicationScope.listStatusProduct }" var="statusproduct">

                                    <c:choose>
                                        <c:when test="${statusproduct.getId() == product.getStatus() }">
                                            <option value="${statusproduct.getId() }"
                                                    selected>${statusproduct.getName() }</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${statusproduct.getId() }">${statusproduct.getName() }</option>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="form-row">
                            <label class="col-lg-2 col-form-label" for="examplefileinput"> Image Product
                            </label>
                            <div class="col-lg-12">
                                <input accept="image/*" type="file" class="form-control" id="examplefileinput"
                                     value="${product.getImage()}"  name="image"

                                       style="color: black ">
<%--                                <input type="hidden" name="image" value="${product.getImage()}">--%>
                                <img style="width: 200px; heigh: 150px; margin-top:10px; " id="blah"
                                     src="${product.getImage()}"
                                     alt="Image Product"/>
                            </div>
                        </div>

                        <div class="form-row">
                            <label class=" col-lg-2 col-form-label " for="example-textarea" style="color: black ">
                                Description
                            </label>
                            <div class="col-lg-12">
                        <textarea class="form-control" rows="5" id="example-textarea" name="description"
                                  style="color: black "
                                  value="<c:out value='${product.getDescription()}' />"></textarea>
                            </div>
                        </div>

                        <button type="submit" class="btn btn-primary">Update</button>

                        <script>
                            examplefileinput.onchange = evt => {
                                const [file] = examplefileinput.files
                                if (file) {
                                    blah.src = URL.createObjectURL(file)
                                }
                            }
                        </script>
                    </form>
                    <div style="margin-top: 20px">
                        <c:if test="${requestScope['success'] == true}">
                            <ul class="alert alert-success col-3"
                                style="list-style-type: none; background-color: #8fdf82;justify-content: center;align-items: center; text-align: center; color: white">
                                <li style="font-size: 15px">SUCCSESS!!!!</li>
                            </ul>
                        </c:if>
                        <c:if test="${!requestScope['errors'].isEmpty()}">
                            <ul style="list-style-type: none">
                                <c:forEach items="${requestScope['errors']}" var="errors">
                                    <li class="alert alert-danger col-3" style="font-size: 15px">
                                            ${errors}
                                    </li>
                                </c:forEach>
                            </ul>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul style="list-style-type: none">
                        <c:forEach items="${requestScope['errors1']}" var="errors1">
                            <li class="alert alert-danger col-3" style="font-size: 15px">
                                    ${errors1}
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>


        </div>

        <!-- END LỌM -->
    </div>
    <!-- end container-fluid -->

</div>
<!-- end content -->


<!-- Footer Start -->
<footer class="footer">
    <div class="container-fluid">
        <div class="row">
            <%--            <div class="col-md-12">--%>
            <%--                2015 - 2020 &copy; Velonic theme by <a href="">Coderthemes</a>--%>
            <%--            </div>--%>
        </div>
    </div>
</footer>
<!-- end Footer -->

</div>

<!-- ============================================================== -->
<!-- End Page content -->
<!-- ============================================================== -->

</div>
<!-- END wrapper -->


<!-- Right Sidebar -->
<%@include file="/dashboard/layout/rightbar.jsp" %>
<!-- /Right-bar -->


<%@include file="/dashboard/layout/scrip.jsp" %>
</body>

</html>