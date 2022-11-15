<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
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
            <img src="/dashboard/assets/images/logo-ttstore.png" alt="" height="180px" width="240px">
        </div>

        <!-- LOGO -->


    </div>
    <!-- end Topbar -->
    <!-- ========== Left Sidebar Start ========== -->
    <%@include file="/dashboard/layout/left-sidebar.jsp" %>
    ;
    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">


        </div>
        <!-- End row -->
        <h4 class="header-title mb-4">EDIT PRODUCT</h4>
        <c:if test="${check}">
            <ul class="alert alert-danger col-3"
                style="list-style-type: none; width: 200px; align-items: center; justify-content: center">
                <li style="font-size: 15px">${error}</li>
            </ul>
        </c:if>

    </div>
    <!-- end container-fluid -->

</div>
<!-- end content -->


<!-- Footer Start -->
<%@include file="/dashboard/layout/footer.jsp" %>
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

<!-- Right bar overlay-->


<%@include file="/dashboard/layout/scrip.jsp" %>

</body>

</html>