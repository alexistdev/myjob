<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;
use App\Http\Controllers\Freelancer\{LoginController as LoginFree,JobController as JobFree,SpinnerController as Spinn};

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:sanctum')->get('/user', function (Request $request) {
    return $request->user();
});
Route::post('/login', [LoginFree::class, 'login'])->name('api.login');
Route::get('/freelancer/job', [JobFree::class, 'getJob'])->name('api.freelancer.job');
Route::post('/freelancer/job', [JobFree::class, 'jobApplied'])->name('api.freelancer.applied');
Route::get('/seeker/job', [JobFree::class, 'myJob'])->name('api.seeker.job');
Route::post('/seeker/job', [JobFree::class, 'tambahJob'])->name('api.seeker.savejob');
Route::get('/kategori', [Spinn::class, 'getKategori'])->name('api.kategori');

