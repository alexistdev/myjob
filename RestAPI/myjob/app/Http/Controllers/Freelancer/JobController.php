<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Bidding;
use App\Models\Job;
use App\Models\Kategori;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Exception;

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
            if($user !== null){
                $job = Job::with('bidding')->where('user_id',$user->id)->get();
                if($job->isEmpty()){
                    return response()->json(array(
                        'status' => false,
                        'message' => 'Data kosong',
                    ),404);
                } else {
                    $data = collect();

                    foreach($job as $jb){
                        $datax['id'] = $jb->id;
                        $datax['name'] = $jb->name;
                        $datax['deskripsi'] = $jb->deskripsi;
                        $datax['fee'] = $jb->fee;
                        $datax['deadline'] = $jb->deadline;
                        $datax['bidder'] = ($jb->bidding != null)?$jb->bidding->user->name:null;
                        $datax['status'] = $jb->status;
                        $data->push($datax);
                    }
                    return response()->json(array(
                        'status' => true,
                        'message' => 'Data berhasil didapatkan',
                        'result' =>$data,
                    ),200);
                }

            } else {
                return response()->json([
                    'status' => false,
                    'message' => "User tidak ditemukan",
                ], 404);
            }

        }
    }

    public function tambahJob(Request $request)
    {
        $rules = [
            'user_id'=> 'required|numeric',
            'kategori' => 'required|max:255',
            'judul' => 'required|max:255',
            'deskripsi' => 'required|max:255',
            'fee' => 'required',
            'deadline' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            DB::beginTransaction();
            try{
                $user= User::where('id',$request->user_id)->where('role_id',3)->first();
                if($user !== null){
                   $getKategori = Kategori::where('name',$request->kategori)->first();
                    if($getKategori !== null){
                        $job = new Job();
                        $hari = date('Y-m-d', strtotime(now(). ' + '.$request->deadline.'days'));
                        $job->user_id = $request->user_id;
                        $job->kategori_id = $getKategori->id;
                        $job->name = $request->judul;
                        $job->deskripsi = $request->deskripsi;
                        $job->fee = $request->fee;
                        $job->deadline = $hari;
                        $job->save();
                        DB::commit();
                        return response()->json([
                            'status' => true,
                            'message' => "data berhasil disimpan!",
                        ], 200);
                    } else {
                        DB::rollback();
                        return response()->json([
                            'status' => false,
                            'message' => "Data  kategori tidak ditemukan!",
                        ], 404);
                    }

                }else{
                    DB::rollback();
                    return response()->json([
                        'status' => false,
                        'message' => "User tidak ditemukan!",
                    ], 404);
                }
            } catch (Exception $e) {
                DB::rollback();
                return response()->json([
                    'status' => false,
                    'message' => "Error: ".$e->getMessage(),
                ], 404);
            }
        }
    }


    /** route: */
    public function jobApplied(Request $request)
    {
        $rules = [
            'user_id' => 'required',
            'job_id' => 'required',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('id',$request->user_id)->where('role_id',2)->first();
            if($user != null){
                $job = Job::where('id', $request->job_id)->where('status',1)->first();
                if($job != null){
                    $bidding = Bidding::where('user_id',$request->user_id)->where('job_id',$request->job_id)->first();
                    if($bidding != null){
                        return response()->json([
                            'status' => false,
                            'message' => "Anda sudah melakukan bid!",
                        ], 401);
                    }else{
                        DB::beginTransaction();
                        try{
                            $bid = new Bidding();
                            $bid->user_id = $request->user_id;
                            $bid->job_id = $request->job_id;
                            $bid->bidder = $this->generateRandomString();
                            $bid->save();
                            DB::commit();
                            return response()->json([
                                'status' => true,
                                'message' => "Anda berhasil melakukan bid!",
                            ], 200);
                        } catch (Exception $e) {
                            DB::rollback();
                            return response()->json([
                                'status' => false,
                                'message' => "Error: ".$e->getMessage(),
                            ], 404);
                        }

                    }
                } else{
                    return response()->json([
                        'status' => false,
                        'message' => "JOB tidak ditemukan!",
                    ], 404);
                }
            }else{
                return response()->json([
                    'status' => false,
                    'message' => "User tidak ditemukan!",
                ], 404);
            }
        }
    }


    private function generateRandomString($length = 10) {
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }

    public function cekStatusJobFreelancer(Request $request){
        $rules = [
            'user_id' => 'required',
            'job_id' => 'required',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('id',$request->user_id)->where('role_id',2)->first();
            if($user != null){
                $job = Job::where('id', $request->job_id)->first();
                if($job != null){
                    $bidding = Bidding::where('user_id',$request->user_id)->where('job_id',$request->job_id)->first();
                    if($bidding != null){
                        return response()->json([
                            'status' => true,
                            'message' => "Sudah Bid!",
                            'status_bid' => "1",
                        ], 200);
                    } else {
                        return response()->json([
                            'status' => true,
                            'message' => "Belum bid!",
                            'status_bid' => "0",
                        ], 200);
                    }
                } else {
                    return response()->json([
                        'status' => false,
                        'message' => "Job tidak ditemukan!",
                    ], 404);
                }
            } else {
                return response()->json([
                    'status' => false,
                    'message' => "User tidak ditemukan!",
                ], 404);
            }
        }
    }
}
