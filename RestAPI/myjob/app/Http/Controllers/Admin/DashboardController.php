<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class DashboardController extends Controller
{
    protected $users;
    protected $role;
    protected $notif;

    public function __construct()
    {
        $this->middleware(function ($request, $next) {
            $this->users=Auth::user();
            return $next($request);
        });
    }

    public function index()
    {
        $user = User::where('role_id',"!=","1")->get();
        return view('admin.dashboard',array(
            'judul' => "Dashboard Administrator | MyJob v.1",
            'menuUtama' => 'dashboard',
            'menuKedua' => 'dashboard',
            'freelancer' => collect($user)->where('role_id',2)->count(),
        ));
    }
}
