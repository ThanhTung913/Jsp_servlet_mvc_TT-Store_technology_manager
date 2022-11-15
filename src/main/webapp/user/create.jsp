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
    <%@include file="/dashboard/layout/topbar-noSearch.jsp" %>

    <!-- end Topbar -->
    <!-- ========== Left Sidebar Start ========== -->
    <%@include file="/dashboard/layout/left-sidebar.jsp" %>

    <!-- Left Sidebar End -->

    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div id="content">

            <div class="container-fluid">


            </div>
            <!-- end col -->


        </div>
        <!-- End row -->
        <div class="card">
            <div class="card-body">
                <h4 class="header-title mb-4"
                    style="font-size: 20px; font-family: American Typewriter; text-align: center">CREATE USER</h4>

                <form method="post" action="/users?action=create">
                    <input type="hidden" name="id" value="${user.getId()}">
                    <div class="form-group">
                        <label class="col-form-label">Name </label>
                        <input type="text" class="form-control"
                               name="name" value="${user.getName()}">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label class="col-form-label">UserName</label>
                            <input type="text" class="form-control" value="${user.getUsername()}"
                                   name="username">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-form-label">Password</label>
                            <input type="text" class="form-control" }
                                   name="password" value="${user.getPassword()}">
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label class="col-form-label">Email</label>
                            <input type="text" class="form-control" name="email" value="${user.getEmail()}">
                        </div>
                        <div class="form-group col-md-6">
                            <label class="col-form-label">Phone</label>
                            <input type="text" class="form-control" name="phone" value="${user.getPhone()}">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-form-label">Address</label>
                        <input type="text" class="form-control"
                               name="address" value="${user.getAddress()}">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-2">
                            <label class="col-form-label">Status</label>
                            <select name="status" class="form-control">
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
                            <label class="col-form-label">Role</label>
                            <select name="role" class="form-control">
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
                    <button type="submit" class="btn btn-primary" id="submit">Create</button>
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