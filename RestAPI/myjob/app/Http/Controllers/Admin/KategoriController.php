<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Service\KategoriService;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class KategoriController extends Controller
{
    protected $users;
    protected KategoriService $kategoriService;

    public function __construct(KategoriService $q)
    {
        $this->middleware(function ($request, $next) {
            $this->users = Auth::user();
            return $next($request);
        });
        $this->kategoriService = $q;
    }

    public function index(Request $request)
    {
        if ($request->ajax()) {
            return $this->kategoriService->index();
        }
        return view('admin.master.kategori', array(
            'judul' => "Dashboard Administrator | MyJob v.1",
            'menuUtama' => 'master',
            'menuKedua' => 'kategori',
        ));
    }
}
