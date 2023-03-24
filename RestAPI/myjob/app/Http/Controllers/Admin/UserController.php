<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Kota;
use App\Models\Provinsi;
use App\Models\User;
use App\Service\UserService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Yajra\DataTables\DataTables;

class UserController extends Controller
{
    protected $users;
    protected UserService $userService;

    public function __construct(UserService $q)
    {
        $this->middleware(function ($request, $next) {
            $this->users = Auth::user();
            return $next($request);
        });
        $this->userService = $q;
    }

    public function index(Request $request)
    {
        if ($request->ajax()) {
            $user = User::where('role_id',"!=","1")->get();
            return DataTables::of($user)
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
                    $btn = "<button class=\"btn btn-primary m-1\">Edit</button>";
                    $btn = $btn."<button class=\"btn btn-danger m-1\">Hapus</button>";
                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.master.user', array(
            'judul' => "Dashboard Administrator | MyJob v.1",
            'menuUtama' => 'master',
            'menuKedua' => 'user',
        ));
    }
}
