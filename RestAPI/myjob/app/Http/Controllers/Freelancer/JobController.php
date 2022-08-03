<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Job;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class JobController extends Controller
{
    public function getJob(Request $request)
    {
        $rules = [
            'user_id' => 'required|numeric',
            'token' => 'required|max:255',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::with('skill')->where('id',$request->user_id)->where('token',$request->token)->first();
            if($user !== null){
                $data = Job::where('kategori_id',$user->skill->kategori_id)
                    ->where('status',1)
                    ->orWhere('name','LIKE','%'.$user->skill->tag->tag.'%')
                    ->orWhere('deskripsi','LIKE','%'.$user->skill->tag->tag.'%')
                    ->get();
                return response()->json(array(
                    'status' => true,
                    'message' => 'Data diary berhasil didapatkan',
                    'result' => $data,
                ),200);
            } else {
                return response()->json([
                    'status' => false,
                    'message' => "user tidak ditemukan",
                ], 404);
            }
        }
    }
}
