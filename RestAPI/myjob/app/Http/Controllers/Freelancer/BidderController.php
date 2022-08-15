<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Balas;
use App\Models\Bidding;
use App\Models\Chat;
use App\Models\Job;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Exception;

class BidderController extends Controller
{
    public function getDataBidder(Request $request)
    {
        $rules = [
            'job_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $job = Job::where('id',$request->job_id)->where('status',1)->first();
            if($job != null){
                $bidder = Bidding::with('user')->where('job_id',$request->job_id)->get();
                $data = collect();
                foreach($bidder as $bid){
                    $mybid = [];
                    $mybid['id'] = $bid->id;
                    $mybid['user_id'] = $bid->user_id;
                    $mybid['nama'] = $bid->user->name;
                    $mybid['email'] = $bid->user->email;
                    $data->push($mybid);
                }
                return response()->json([
                    'status' => true,
                    'message' => "berhasil",
                    'result' => $data,
                ], 200);

            }else{
                return response()->json([
                    'status' => false,
                    'message' => "data job tidak ditemukan",
                ], 404);
            }
        }
    }

    public function approve(Request $request){
        $rules = [
            'bidder_id' => 'required',
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
                $bidder = Bidding::where('id',$request->bidder_id)->first();
                if($bidder != null){
                    Job::where('id',$bidder->job_id)->update([
                       'status' => 2,
                       'bidder' => $bidder->bidder,
                    ]);
                    $job = Job::where('id',$bidder->job_id)->first();

                    $chat = new Chat();
                    $chat->user_id = $bidder->user_id;
                    $chat->job_id = $bidder->job_id;
                    $chat->save();
                    $idChat=$chat->id;

                    $user = User::where('id',$job->user_id)->first();
                    $balas=new Balas();
                    $balas->user_id = $job->user_id;
                    $balas->chat_id = $idChat;
                    $balas->pesan = "Selamat anda telah terpilih menjadi penerima pekerjaan dari saya. Perkenalkan nama saya adalah " .$user->name. " Untuk koordinasi pekerjaan, bisa melalui halaman chat ini!";
                    $balas->save();
                    DB::commit();
                    return response()->json([
                        'status' => true,
                        'message' => "job sudah diapprove",
                    ], 200);
                }else{
                    return response()->json([
                        'status' => false,
                        'message' => "data tidak ada",
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
}
