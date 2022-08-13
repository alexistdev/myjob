<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Yajra\DataTables\DataTables;

class KategoriController extends Controller
{
    protected $users;

    public function __construct()
    {
        $this->middleware(function ($request, $next) {
            $this->users = Auth::user();
            return $next($request);
        });
    }

    public function index(Request $request)
    {
        if ($request->ajax()) {
            $kategori = Kategori::all();
            return DataTables::of($kategori)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('role', function ($request) {
                    if($request->role_id == 2) {
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
        return view('admin.master.kategori', array(
            'judul' => "Dashboard Administrator | MyJob v.1",
            'menuUtama' => 'master',
            'menuKedua' => 'kategori',
        ));
    }
}
