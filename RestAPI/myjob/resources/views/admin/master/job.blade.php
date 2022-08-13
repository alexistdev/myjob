<x-admin.template-admin :title="$judul">
    <div class="page-content">
        <div class="row">
            <div class="col-md-12">
                <div class="card radius-10 w-100">
                    <div class="card-header bg-transparent">
                        <div class="d-flex align-items-center">
                            <div>
                                <h6 class="mb-0">Master Data JOB</h6>

                            </div>
                        </div>
                        <button class="btn btn-sm btn-primary float-end">TAMBAH</button>
                    </div>
                    <div class="card-body">
                        <div class="row">
                            <table id="tabelKu" class="table table-bordered dt-responsive nowrap table-striped m-1"
                                   style="width:100%">
                                <thead>
                                <tr>
                                    <th class="text-center">NO</th>
                                    <th class="text-center">JUDUL</th>
                                    <th class="text-center">KATEGORI</th>
                                    <th class="text-center">TAG</th>
                                    <th class="text-center">PEKERJA</th>
                                    <th class="text-center">PEMBUAT</th>
                                    <th class="text-center">DIBUAT</th>
                                    <th class="text-center">ACTION</th>
                                </tr>
                                </thead>
                                <tbody>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div><!--end row-->
    </div>

    <x-admin.js-layout />
    <script>
        $(document).ready(function () {
            let base_url = "{{route('adm.job')}}";
            $('#tabelKu').DataTable({
                responsive: true,
                processing: true,
                serverSide: true,
                ajax: {
                    type: 'GET',
                    url: base_url,
                    async: true,
                },
                language: {
                    processing: "<div class='water'></div> </br> <div class='loading'>LOADING...</div> ",
                },
                columns: [
                    {
                        data: 'index',
                        class: 'text-center',
                        defaultContent: '',
                        orderable: false,
                        searchable: false,
                        width: '5%',
                        render: function (data, type, row, meta) {
                            return meta.row + meta.settings._iDisplayStart + 1; //auto increment
                        }
                    },
                    {data: 'name', class: 'text-left'},
                    {data: 'kategori', class: 'text-left'},
                    {data: 'tag', class: 'text-left'},
                    {data: 'bidding', class: 'text-left'},
                    {data: 'user.name', class: 'text-left'},
                    {data: 'created_at', class: 'text-left'},
                    {data: 'action', class: 'text-center', width: "10%"},
                ],
                "bDestroy": true
            });
        });
    </script>
</x-admin.template-admin>
