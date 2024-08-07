<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Balas;
use App\Models\Chat;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;
use Exception;

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
                $balas = Balas::with('user')->orderBy('id','DESC')->where('chat_id',$chat->id)->get();
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
            'job_id' => 'required|numeric',
            'pesan' => 'required|max:255',
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
                $chat= Chat::where('job_id',$request->job_id)->first();
                if($chat !== null){
                    $balas = new Balas();
                    $balas->user_id = $request->user_id;
                    $balas->chat_id = $chat->id;
                    $balas->pesan = $request->pesan;
                    $balas->save();
                    DB::commit();
                    return response()->json([
                        'status' => true,
                        'message' => "data berhasil disimpan",
                    ], 200);
                }else{
                    DB::rollback();
                    return response()->json([
                        'status' => false,
                        'message' => "data kosong",
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

    public function getSeekerChat(Request $request)
    {
        $rules = [
            'user_id' => 'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = User::where('id',$request->user_id)->where('role_id',3)->first();
            if($user !== null){
                $userId = $request->user_id;
                $chat = User::query()->with(["chats"])->find($userId);
                if($chat !== null){
                    $chatNew = collect();
                    foreach($chat->chats as $row){
                        $xdata['id'] = $row->id;
                        $xdata['freelancer'] = $row->user->name;
                        $xdata['job_id'] = $row->job_id;
                        $xdata['judul'] = "Project :".$row->jobrelated->name;
                        $xdata['tanggal'] = date("d-m-Y",strtotime($row->created_at));
                        $chatNew->push($xdata);
                    }
                    return response()->json([
                        'status' => true,
                        'message' => 'Berhasil',
                        'result' => $chatNew,
                    ], 200);
                } else {

                    return response()->json([
                        'status' => false,
                        'message' => "data tidak ditemukan!",
                    ], 404);
                }
            }else{
                return response()->json([
                    'status' => false,
                    'message' => "user tidak ditemukan!",
                ], 404);
            }

        }

    }
}
