<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Validator;

class AkunController extends Controller
{
    public function getAkun(Request $request)
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
            $user= User::where('id',$request->user_id)->where('role_id',2)->first();
            if($user !== null){
                return response()->json([
                    'status' => true,
                    'message' => "data tidak lengkap",
                    'name' => $user->name,
                    'email' => $user->email,
                    'phone' => $user->phone,
                ], 200);
            } else{
                return response()->json([
                    'status' => false,
                    'message' => "data user tidak ditemukan",
                ], 404);
            }
        }
    }
}
