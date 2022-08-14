<x-admin.template-admin :title="$judul">
    <div class="page-content">
        <div class="row">
            <div class="col-md-3">
                <div class="col">
                    <div class="card radius-10 border-start border-0 border-3 border-info">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div>
                                    <p class="mb-0 text-secondary">Freelancer</p>
                                    <h4 class="my-1 text-info">{{$freelancer}}</h4>
                                    <p class="mb-0 font-13">+2.5% from last week</p>
                                </div>
                                <div class="widgets-icons-2 rounded-circle bg-gradient-scooter text-white ms-auto"><i class='bx bxs-user-account'></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card radius-10 border-start border-0 border-3 border-danger">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div>
                                    <p class="mb-0 text-secondary">Pemberi Kerja</p>
                                    <h4 class="my-1 text-danger">$84,245</h4>
                                    <p class="mb-0 font-13">+5.4% from last week</p>
                                </div>
                                <div class="widgets-icons-2 rounded-circle bg-gradient-bloody text-white ms-auto"><i class='bx bxs-wallet'></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card radius-10 border-start border-0 border-3 border-success">
                        <div class="card-body">
                            <div class="d-flex align-items-center">
                                <div>
                                    <p class="mb-0 text-secondary">Job Tersedia</p>
                                    <h4 class="my-1 text-success">$84,245</h4>
                                    <p class="mb-0 font-13">+5.4% from last week</p>
                                </div>
                                <div class="widgets-icons-2 rounded-circle bg-gradient-ohhappiness text-white ms-auto"><i class='bx bxs-user-account'></i>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-9">
                <div class="card">
                    <div class="card-header bg-transparent">
                        <div class="d-flex align-items-center">
                            <div>
                                <h6 class="mb-0">Job Terbaru</h6>
                            </div>
                            <div class="dropdown ms-auto">
                                <a class="dropdown-toggle dropdown-toggle-nocaret" href="#" data-bs-toggle="dropdown"><i class='bx bx-dots-horizontal-rounded font-22 text-option'></i>
                                </a>
                                <ul class="dropdown-menu">
                                    <li><a class="dropdown-item" href="javascript:;">Action</a>
                                    </li>
                                    <li><a class="dropdown-item" href="javascript:;">Another action</a>
                                    </li>
                                    <li>
                                        <hr class="dropdown-divider">
                                    </li>
                                    <li><a class="dropdown-item" href="javascript:;">Something else here</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <div class="col-lg-7 col-xl-8 border-end">
                                <div id="geographic-map-2"></div>
                            </div>
                            <div class="col-lg-5 col-xl-4">

                                <div class="mb-4">
                                    <p class="mb-2"><i class="flag-icon flag-icon-us me-1"></i> USA <span class="float-end">70%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-primary progress-bar-striped" role="progressbar" style="width: 70%"></div>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <p class="mb-2"><i class="flag-icon flag-icon-ca me-1"></i> Canada <span class="float-end">65%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-danger progress-bar-striped" role="progressbar" style="width: 65%"></div>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <p class="mb-2"><i class="flag-icon flag-icon-gb me-1"></i> England <span class="float-end">60%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-success progress-bar-striped" role="progressbar" style="width: 60%"></div>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <p class="mb-2"><i class="flag-icon flag-icon-au me-1"></i> Australia <span class="float-end">55%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-warning progress-bar-striped" role="progressbar" style="width: 55%"></div>
                                    </div>
                                </div>

                                <div class="mb-4">
                                    <p class="mb-2"><i class="flag-icon flag-icon-in me-1"></i> India <span class="float-end">50%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-info progress-bar-striped" role="progressbar" style="width: 50%"></div>
                                    </div>
                                </div>

                                <div class="mb-0">
                                    <p class="mb-2"><i class="flag-icon flag-icon-cn me-1"></i> China <span class="float-end">45%</span></p>
                                    <div class="progress" style="height: 7px;">
                                        <div class="progress-bar bg-dark progress-bar-striped" role="progressbar" style="width: 45%"></div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div><!--end row-->

    </div>

    <x-admin.js-layout />
</x-admin.template-admin>
