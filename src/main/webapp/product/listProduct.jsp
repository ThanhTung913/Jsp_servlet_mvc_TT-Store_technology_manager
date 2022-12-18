<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
<%--    <title>Product TT-STORE TECHNOLOGY</title>--%>
    <%@include file="../dashboard/layout/head.jsp" %>

</head>

<body>

<!-- Begin page -->
<div id="wrapper">


    <!-- Topbar Start -->
    <div class="navbar-custom">
        <ul class="list-unstyled topnav-menu float-right mb-0">

            <li class="dropdown notification-list">
                <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect" data-toggle="dropdown" href="#"
                   role="button" aria-haspopup="false" aria-expanded="false">
                    <img src="/dashboard/assets/avatar-1.jpg" alt="user-image" class="rounded-circle">
                    <span class="pro-user-name ml-1">
                            Thanh Tung <i class="mdi mdi-chevron-down"></i>
                        </span>
                </a>
                <div class="dropdown-menu dropdown-menu-right profile-dropdown ">
                    <!-- item-->
                    <div class="dropdown-header noti-title">
                        <h6 class="text-overflow m-0">Welcome !</h6>
                    </div>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                        <i class="mdi mdi-account-outline"></i>
                        <span>Profile</span>
                    </a>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                        <i class="mdi mdi-settings-outline"></i>
                        <span>Settings</span>
                    </a>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                        <i class="mdi mdi-lock-outline"></i>
                        <span>Lock Screen</span>
                    </a>

                    <div class="dropdown-divider"></div>

                    <!-- item-->
                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                        <i class="mdi mdi-logout-variant"></i>
                        <span>Logout</span>
                    </a>

                </div>
            </li>

            <li class="dropdown notification-list">
                <a href="javascript:void(0);" class="nav-link right-bar-toggle waves-effect">
                    <i class="mdi mdi-settings-outline noti-icon"></i>
                </a>
            </li>


        </ul>

        <!-- LOGO -->
        <div class="logo-box">
            <img src="/dashboard/assets/images/logo-ttstore.png" alt="" height="180px" width="240px">
        </div>

        <!-- LOGO -->


        <ul class="list-unstyled topnav-menu topnav-menu-left m-0">
            <li>
                <button class="button-menu-mobile waves-effect">
                    <i class="mdi mdi-menu"></i>
                </button>
            </li>

            <li class="d-none d-lg-block">
                <form class="app-search" method="post" action="/products?action=search">
                    <div class="app-search-box">
                        <div class="input-group">
                            <input type="text" class="form-control" placeholder="Search..." name="searchProduct"
                                   onchange="buttonsearch" value="${searchProduct}">
                            <div class="input-group-append">
                                <button class="btn" type="submit" id="buttonsearch">
                                    <i class="fas fa-search"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </form>
            </li>
        </ul>
    </div>
    <!-- end Topbar -->
    <!-- ========== Left Sidebar Start ========== -->

    <!-- Left Sidebar End -->
    <%@include file="../dashboard/layout/left-sidebar.jsp" %>
    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <div class="container-fluid">

            </div>
            <!-- end col -->


        </div>
        <!-- End row -->
        <div class="card">
            <div class="card-body">
                <h4 style="font-size: 20px; font-family: American Typewriter; text-align: center"
                    class="header-title mb-4">
                    LIST PRODUCT</h4>
                <div class="table-responsive">
                    <table class="table table-centered" id="btn-editable">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>Image</th>
                            <th>Price</th>
                            <th>Category</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr id="2">
                            <c:forEach var="product" items="${requestScope.listProduct}">
                            <td><c:out value="${product.getId()}"></c:out></td>

                            <td><c:out value="${product.getName()}"></c:out></td>

                            <td>
                                <img style="width: 70px; height: 70px; border-radius: 10%" src="${product.getImage()}">
                            </td>

                            <td>
                                <fmt:formatNumber type="number"
                                                  pattern="###,### VND" value="${product.getPrice()}">

                                </fmt:formatNumber>
                            </td>

                            <td name="catagory">
                                <c:forEach items="${applicationScope.listCatagory}" var="catagory">
                                    <c:if test="${catagory.getId() == product.getCatagory()}">
                                        <c:out value="${catagory.getName()}"></c:out>
                                    </c:if>
                                </c:forEach>
                            </td>

                            <td name="status">
                                <c:forEach items="${applicationScope.listStatusProduct}" var="status">
                                    <c:if test="${status.getId() == product.getStatus()}">
                                        <c:out value="${status.getName()}"></c:out>
                                    </c:if>
                                </c:forEach>
                            </td>


                            <td style="display: flex; ">
                                <a href="/products?action=edit&id=${product.id}">
                                    <button type="button" class="btn btn-warning waves-effect width-md waves-light"
                                            style="width: 5px">
                                        Edit
                                    </button>

                                </a>
                            </td>

                        </tr>
                        </c:forEach>

                        </tbody>
                        <div class="col-sm-12 col-md-6">
                            <div>
                                <ul class="pagination">
                                    <c:if test="${requestScope.currentPage != 1}">
                                        <li class="page-item"><a class="page-link"
                                                                 href="products?page=${requestScope.currentPage - 1}">Previous</a>
                                        </li>
                                    </c:if>
                                    <c:forEach begin="1" end="${noOfPages}" var="i">
                                        <c:choose>
                                            <c:when test="${requestScope.currentPage eq i}">
                                                <li class="page-item"><a class="page-link"
                                                                         href="products?page=${i}">${i}</a></li>
                                            </c:when>
                                            <c:otherwise>
                                                <li class="page-item"><a class="page-link"
                                                                         href="products?page=${i}">${i}</a></li>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                                        <li class="page-item"><a class="page-link"
                                                                 href="products?page=${requestScope.currentPage + 1}">Next</a>
                                        </li>
                                    </c:if>
                                </ul>
                            </div>
                        </div>
                    </table>
                </div>
                <!-- end .table-responsive-->
            </div>
            <!-- end card-body -->
        </div>


    </div>

</div>
<!-- end container-fluid -->

</div>
<!-- end content -->


<!-- Footer Start -->
<footer class="footer">
    <div class="container-fluid">
        <div class="row">
            <div class="col-md-12">
                2015 - 2020 &copy; Velonic theme by <a href="">Coderthemes</a>
            </div>
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
<%@include file="../dashboard/layout/rightbar.jsp" %>
<!-- /Right-bar -->


<%@include file="../dashboard/layout/scrip.jsp" %>
</body>

</html>