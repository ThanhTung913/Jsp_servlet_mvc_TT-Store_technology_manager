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
        <div class="card">
            <div class="card-body">
                <h4 class="header-title mb-4">EDIT USER</h4>
                <div style="margin-top: 20px">
                    <c:choose>
                        <c:when test="${errors1 == null }">
                            <form method="post" action="/users?action=edit">
                                <c:if test="${user != null}">
                                    <input type="hidden" name="id"
                                           value="<c:out value='${user.getId()}' />"/>
                                </c:if>
                                <div class="form-group">
                                    <label class="col-form-label">Name </label>
                                    <input type="text" class="form-control" name="name"
                                           value="<c:out value='${user.getName()}' />"/>
                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label class="col-form-label">UserName</label>
                                        <input type="text" class="form-control" name="username"
                                               value="<c:out value='${user.getUsername()}' />"/>

                                    </div>
                                    <div class="form-group col-md-6">
                                        <label class="col-form-label">Password</label>
                                        <input type="text" class="form-control" name="password"
                                               value="<c:out value='${user.getPassword()}' />"/>

                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label class="col-form-label">Email</label>
                                        <input type="text" class="form-control" name="email"
                                               value="<c:out value='${user.getEmail()}' />"/>

                                    </div>

                                    <div class="form-group col-md-6">
                                        <label class="col-form-label">Phone</label>
                                        <input type="text" class="form-control" name="phone"
                                               value="<c:out value='${user.getPhone()}' />"/>

                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-form-label">Address</label>
                                    <input type="text" class="form-control"
                                           name="address"
                                           value="<c:out value='${user.getAddress()}' />"/>

                                </div>
                                <div class="form-row">
                                    <div class="form-group col-md-2">
                                        <label class="col-form-label" name="status">Status</label>
                                        <select  name="status" class="form-control">
                                            <c:forEach items="${applicationScope.listStatus }" var="status">
                                                <c:choose>
                                                    <c:when test="${status.getId() == user.getStatus() }">
                                                        <option value="${status.getId() }"
                                                                selected>${status.getName() }</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option value="${status.getId() }">${status.getName() }</option>
                                                    </c:otherwise>
                                                </c:choose>

                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-2">
                                        <label class="col-form-label" name="role">Role</label>
                                        <select id="inputState" name="role" class="form-control">
                                            <<c:forEach items="${applicationScope.listRole }" var="role">

                                            <c:choose>
                                                <c:when test="${role.getId() == user.getRole() }">
                                                    <option value="${role.getId() }"
                                                            selected>${role.getName() }</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="${role.getId() }">${role.getName() }</option>
                                                </c:otherwise>
                                            </c:choose>

                                        </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <button type="submit" class="btn btn-primary">Update</button>
                            </form>
                            <c:if test="${requestScope['success'] == true}">
                                <ul class="alert alert-success col-3" style="list-style-type: none; width: 200px">
                                    <li style="font-size: 15px">Sửa thành công</li>
                                </ul>
                            </c:if>

                            <c:if test="${check}">
                                <ul class="alert alert-danger col-3" style="list-style-type: none; width: 200px">
                                    <li style="font-size: 15px">${error}</li>
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
            </div>

        </div>

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