<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Balas;
use App\Models\Chat;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class ChatController extends Controller
{
    /** route: api.freelancer.chat
     * chat freelancer
     */
    public function getChatFreelancer(Request $request)
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
            $chat = Chat::where('job_id',$request->job_id)->first();
            if($chat !== null){
                $balas = Balas::where('chat_id',$chat->id)->get();
                if($balas->isEmpty()){
                    return response()->json([
                        'status' => false,
                        'message' => "data kosong",
                    ], 404);
                } else {
                    return response()->json([
                        'status' => true,
                        'message' => "data berhasil didapatkan",
                        'result' => $balas,
                    ], 200);
                }
            }else{
                return response()->json([
                    'status' => false,
                    'message' => "data kosong",
                ], 404);
            }

        }
    }
}
