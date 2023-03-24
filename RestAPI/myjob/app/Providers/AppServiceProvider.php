<?php

namespace App\Providers;


use App\Service\KategoriService;
use App\Service\KategoriServiceImpl;
use App\Service\UserService;
use App\Service\UserServiceImpl;
use Illuminate\Support\ServiceProvider;

class AppServiceProvider extends ServiceProvider
{

    public $bindings = [
        UserService::class => UserServiceImpl::class,
        KategoriService::class => KategoriServiceImpl::class,
    ];

    public function register()
    {
        //
    }

    /**
     * Bootstrap any application services.
     *
     * @return void
     */
    public function boot()
    {
        //
    }
}
