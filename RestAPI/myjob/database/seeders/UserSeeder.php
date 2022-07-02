<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;
use Illuminate\Support\Facades\Hash;

class UserSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $user = [
          ['role_id' => 1, 'name' => 'admin', 'email' => 'admin@gmail.com' , 'password' => Hash::make('1234'), 'status' => 1 ,'created_at' => $date, 'updated_at' => $date],
          ['role_id' => 2, 'name' => 'freelancer', 'email' => 'freelancer@gmail.com' , 'password' => Hash::make('1234'), 'status' => 1 ,'created_at' => $date, 'updated_at' => $date],
          ['role_id' => 3, 'name' => 'freelancer', 'email' => 'seeker@gmail.com' , 'password' => Hash::make('1234'), 'status' => 1 ,'created_at' => $date, 'updated_at' => $date],
        ];
        User::insert($user);
    }
}
