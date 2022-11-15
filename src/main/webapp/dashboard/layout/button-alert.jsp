<%--
  Created by IntelliJ IDEA.
  User: prom1
  Date: 23/07/2022
  Time: 12:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    // document.getElementById('submit').onclick = function () {
    //     swal("Here's a message!");
    // };

    document.getElementById('b2').onclick = function () {
        swal("Here's a message!", "It's pretty, isn't it?")
    };

    document.getElementById('submit').onclick = function () {
        swal("Good job!", "messSuccsec", "success");
    };

    document.getElementById('b4').onclick = function () {
        swal({
                title: "Are you sure?",
                text: "You will not be able to recover this imaginary file!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: 'Yes, delete it!',
                closeOnConfirm: false,
                //closeOnCancel: false
            },
            function () {
                swal("Deleted!", "Your imaginary file has been deleted!", "success");
            });
    };

    document.getElementById('b5').onclick = function () {
        swal({
                title: "Are you sure?",
                text: "You will not be able to recover this imaginary file!",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: '#DD6B55',
                confirmButtonText: 'Yes, delete it!',
                cancelButtonText: "No, cancel plx!",
                closeOnConfirm: false,
                closeOnCancel: false
            },
            function (isConfirm) {
                if (isConfirm) {
                    swal("Deleted!", "Your imaginary file has been deleted!", "success");
                } else {
                    swal("Cancelled", "Your imaginary file is safe :)", "error");
                }
            });
    };

    document.getElementById('b6').onclick = function () {
        swal({
            title: "Sweet!",
            text: "Here's a custom image.",
            imageUrl: 'http://i.imgur.com/4NZ6uLY.jpg'
        });
    };
</script>
