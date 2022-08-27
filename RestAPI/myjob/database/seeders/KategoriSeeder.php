<?php

namespace Database\Seeders;

use App\Models\Kategori;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Carbon;

class KategoriSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        $date = Carbon::now()->format('Y-m-d H:i:s');
        $kategori = [
            array('name' => 'Graphic Desainer','tag' => 'adobe','created_at' => $date,'updated_at' => $date),
            array('name' => 'Video Editor','tag' => 'video','created_at' => $date,'updated_at' => $date),
            array('name' => 'Motion Designer','tag' => 'motion','created_at' => $date,'updated_at' => $date),
            array('name' => 'Photo Product','tag' => 'photo','created_at' => $date,'updated_at' => $date),
            array('name' => 'Programmer','tag' => 'programmer','created_at' => $date,'updated_at' => $date),
        ];
        Kategori::insert($kategori);
    }
}
