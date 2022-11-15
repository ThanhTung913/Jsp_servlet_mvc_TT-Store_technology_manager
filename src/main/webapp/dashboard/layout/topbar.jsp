<%--
  Created by IntelliJ IDEA.
  User: prom1
  Date: 18/07/2022
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@include file="head.jsp" %>
<div class="navbar-custom">
    <ul class="list-unstyled topnav-menu float-right mb-0">

        <li class="dropdown notification-list">
            <a class="nav-link dropdown-toggle nav-user mr-0 waves-effect" data-toggle="dropdown" href="#"
               role="button" aria-haspopup="false" aria-expanded="false">
                <img src="/dashboard/assets/avatar-1.jpg" alt="user-image" class="rounded-circle">
                <span class="pro-user-name ml-1">
                            Thanh Tung
                        </span>
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
            <form class="app-search">
                <div class="app-search-box">
                    <div class="input-group">
                        <input type="text" class="form-control" placeholder="Search...">
                        <div class="input-group-append">
                            <button class="btn" type="submit">
                                <i class="fas fa-search"></i>
                            </button>
                        </div>
                    </div>
                </div>
            </form>
        </li>
    </ul>
</div>
<%@include file="scrip.jsp" %>
