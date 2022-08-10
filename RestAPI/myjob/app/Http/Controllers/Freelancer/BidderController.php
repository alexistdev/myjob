<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Bidding;
use App\Models\Job;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class BidderController extends Controller
{
    public function getDataBidder(Request $request)
    {
        $rules = [
            'job_id' => 'required',
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
}
