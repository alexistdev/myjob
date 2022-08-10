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
                    $data->push($bid->user);

                }
                return response()->json([
                    'status' => true,
                    'message' => "data job tidak ditemukan",
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
