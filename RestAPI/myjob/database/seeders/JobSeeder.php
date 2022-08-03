<?php

namespace Database\Seeders;

use App\Models\Job;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;

class JobSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $job = [
            array('user_id' => 3,'kategori_id'=>1,'name' => 'Desain Grafis','deskripsi'=>'lorem ipsum','fee'=>500000,'deadline'=>'2022-08-25','status'=>1,'created_at' => $date,'updated_at' => $date),
        ];
        Job::insert($job);
    }
}
