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
                $balas = Balas::with('user')->where('chat_id',$chat->id)->get();
                if($balas->isEmpty()){
                    return response()->json([
                        'status' => false,
                        'message' => "data kosong",
                    ], 404);
                } else {
                    $datax =collect();
                    foreach($balas as $row){
                        $x['id'] = $row->id;
                        $x['nama_pengguna'] = $row->user->name;
                        $x['pesan'] = $row->pesan;
                        $datax->push($x);
                    }
                    return response()->json([
                        'status' => true,
                        'chatID' => $chat->id,
                        'message' => "data berhasil didapatkan",
                        'result' => $datax,
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

    public function kirimPesan(Request $request){
        $rules = [
            'user_id' => 'required|numeric',
            'chat_id' => 'required|numeric',
        ];

    }
}
