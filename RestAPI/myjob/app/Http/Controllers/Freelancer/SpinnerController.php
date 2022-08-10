<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Kategori;
use Illuminate\Http\Request;

class SpinnerController extends Controller
{
    public function getKategori()
    {
        $kategori = Kategori::all();
        if($kategori->isEmpty()){
            return response()->json([
                'status' => false,
                'message' => "Data Kosong",
            ], 404);
        } else {
            return response()->json(array(
                'status' => true,
                'message' => 'Data berhasil didapatkan',
                'result' => $kategori,
            ),200);
        }

    }
}
