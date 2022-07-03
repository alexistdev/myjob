<!doctype html>
<html lang="en" class="semi-dark">

<head>
    <title>{{$title}}</title>
    <x-admin.header-layout />
</head>

<body>
<!--wrapper-->
<div class="wrapper">

    <!--START: SIDEBAR -->
    <div class="sidebar-wrapper" data-simplebar="true">
        <x-admin.sidebar-layout />
    </div>
    <!--END: SIDEBAR -->

    <!--START: NAVBAR -->
    <header>
        <x-admin.navbar-layout />
    </header>
    <!--START: NAVBAR -->

    <!--START: KONTEN -->
    <div class="page-wrapper">
       {{$slot}}
    </div>
    <!--END: KONTEN -->

    <!--start overlay-->
    <div class="overlay toggle-icon"></div>
    <!--end overlay-->

    <!--Start Back To Top Button-->
    <a href="javaScript:;" class="back-to-top"><i class='bx bxs-up-arrow-alt'></i></a>
    <!--End Back To Top Button-->

    <!--Start: Footer-->
    <x-admin.footer-layout />
    <!--End: Footer-->
</div>
<!--end wrapper-->

</body>

</html>
