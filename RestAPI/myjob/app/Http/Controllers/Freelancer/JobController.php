<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Job;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class JobController extends Controller
{
    /** route:api.freelancer.job */
    public function getJob(Request $request)
    {
        $rules = [
            'user_id' => 'required',
            'token' => 'required',
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
                    'message' => 'Data berhasil didapatkan',
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


    /** route: */
    public function myJob(Request $request)
    {
        $rules = [
            'user_id' => 'required',
            'token' => 'required',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('id',$request->user_id)->where('token',$request->token)->first();
            $job = Job::where('user_id',$user->id)->get();
            return response()->json(array(
                'status' => true,
                'message' => 'Data berhasil didapatkan',
                'result' => $job,
            ),200);
        }
    }
}
