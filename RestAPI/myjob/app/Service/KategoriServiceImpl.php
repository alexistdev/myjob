<?php

namespace App\Service;

use App\Models\Kategori;
use Yajra\DataTables\DataTables;

class KategoriServiceImpl implements KategoriService
{
    public function index()
    {
        $kategori = Kategori::orderBy('name','ASC')->get();
        return DataTables::of($kategori)
            ->addIndexColumn()
            ->editColumn('created_at', function ($row) {
                return $row->created_at->format('d-m-Y H:i:s');
            })
            ->editColumn('role', function ($row) {
                if($row->role_id == 2) {
                    $str = "Freelancer";
                } else {
                    $str = "Pemberi Kerja";
                }
                return $str;
            })
            ->addColumn('action', function ($row) {
                $btn = "<button class=\"btn btn-sm btn-primary m-1\">Edit</button>";
                $btn = $btn."<button class=\"btn btn-sm btn-danger m-1\">Hapus</button>";
                return $btn;
            })
            ->rawColumns(['action'])
            ->make(true);
    }
}
