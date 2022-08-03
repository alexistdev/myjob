<?php

namespace Database\Seeders;

use App\Models\Skillpengguna;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;

class SkillpenggunaSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $skill = [
            array('user_id' =>2, 'kategori_id' => 1,'created_at' => $date,'updated_at' => $date),
            ];
        Skillpengguna::insert($skill);
    }
}
