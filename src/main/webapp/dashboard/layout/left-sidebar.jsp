<%--
  Created by IntelliJ IDEA.
  User: prom1
  Date: 18/07/2022
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>

<%@include file="head.jsp" %>
<div class="left-side-menu">

    <div class="slimscroll-menu">

        <!--- Sidemenu -->
        <div id="sidebar-menu">
            <ul class="metismenu" id="side-menu">
                <li>
                    <a href="javascript: void(0);" class="waves-effect">
                        <i class="ion-md-speedometer"></i>
                        <span> Dashboard </span>
                    </a>

                </li>

                <li>
                    <a href="javascript: void(0);" class="waves-effect">
                        <i class="ion-md-speedometer"></i>
                        <span> Dashboard </span>
                    </a>

                </li>

                <li>
                    <a href="javascript: void(0);" class="waves-effect">
                        <i class="ion-md-basket"></i>
                        <span> USER </span>
                        <span class="menu-arrow"></span>
                    </a>
                    <ul class="nav-second-level" aria-expanded="false">

                        <li><a href="/users?action=list">List User</a></li>
                        <li><a href="/users?action=create">Create New User</a></li>
                        <li><a href="/users?action=userActive">User Active</a></li>
                        <li><a href="/users?action=userUnActive">User UnActive</a></li>


                    </ul>
                </li>

                <li>
                    <a href="javascript: void(0);" class="waves-effect">
                        <i class="ion-ios-apps"></i>
                        <span> PRODUCTS </span>
                        <span class="menu-arrow"></span>
                    </a>
                    <ul class="nav-second-level" aria-expanded="false">
                        <li><a href="/products?action=list">List product</a></li>
                        <li><a href="/products?action=create">Create product</a></li>
                        <li><a href="#">Product Lock</a></li>

                    </ul>
                </li>

                <li>
                    <a href="javascript: void(0);" class="waves-effect">
                        <i class="ion-md-speedometer"></i>
                        <span> CARD </span>
                    </a>
                    <ul class="nav-second-level" aria-expanded="false">
                        <li><a href="#">Order</a></li>
                        <li><a href="#">orderDetail</a></li>
                        <li><a href="#">Payment</a></li>
                    </ul>
                </li>


            </ul>

        </div>
        <!-- End Sidebar -->

        <div class="clearfix"></div>

    </div>
    <!-- Sidebar -left -->

</div>
<%@include file="scrip.jsp" %>
