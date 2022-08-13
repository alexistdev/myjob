<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\Job;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Yajra\DataTables\DataTables;

class JobController extends Controller
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
//        return $job = Job::with('kategori','bidding')->get();
        if ($request->ajax()) {
            $job = Job::with('kategori','bidding','user')->get();
            return DataTables::of($job)
                ->addIndexColumn()
                ->editColumn('created_at', function ($request) {
                    return $request->created_at->format('d-m-Y H:i:s');
                })
                ->editColumn('kategori', function ($request) {
                  return $request->kategori->name;
                })
                ->editColumn('tag', function ($request) {
                    return $request->kategori->tag;
                })
                ->editColumn('bidding', function ($request) {
                    if($request->bidding !== null){
                       return $request->bidding->user->name;
                    } else {
                        return "-";
                    }

                })
                ->addColumn('action', function ($row) {
                    $btn = "<button class=\"btn btn-primary m-1\">Edit</button>";
                    $btn = $btn."<button class=\"btn btn-danger m-1\">Hapus</button>";
                    return $btn;
                })
                ->rawColumns(['action'])
                ->make(true);
        }
        return view('admin.master.job', array(
            'judul' => "Dashboard Administrator | MyJob v.1",
            'menuUtama' => 'master',
            'menuKedua' => 'user',
        ));
    }
}
