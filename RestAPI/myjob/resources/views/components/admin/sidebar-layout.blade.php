<div>
    <div class="sidebar-header">
        <div>
            <img src="{{asset('rocker/assets/images/logo-icon.png')}}" class="logo-icon" alt="logo icon">
        </div>
        <div>
            <h4 class="logo-text">Rocker</h4>
        </div>
        <div class="toggle-icon ms-auto"><i class='bx bx-arrow-to-left'></i>
        </div>
    </div>
    <!--navigation-->
    <ul class="metismenu" id="menu">
        <li>
            <a href="{{route('adm.dashboard')}}">
                <div class="parent-icon"><i class='bx bx-home-circle'></i>
                </div>
                <div class="menu-title">Dashboard</div>
            </a>
        </li>
        <li>
            <a href="javascript:;" class="has-arrow">
                <div class="parent-icon"><i class='bx bx-menu'></i>
                </div>
                <div class="menu-title">Master Data</div>
            </a>
            <ul>
                <li> <a href="{{route('adm.users')}}"><i class="bx bx-right-arrow-alt"></i>Users</a>
                </li>
                <li> <a href="{{route('adm.kategori')}}"><i class="bx bx-right-arrow-alt"></i>Kategori</a>
                </li>
                <li> <a href="{{route('adm.job')}}"><i class="bx bx-right-arrow-alt"></i>Data Job</a>
                </li>
            </ul>
        </li>

    </ul>
    <!--end navigation-->
</div>
