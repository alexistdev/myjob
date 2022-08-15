<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Validation\Rule;

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

    public function simpanAkun(Request $request)
    {
        $rules = [
            'user_id' => 'required|numeric',
            'name' => 'required|max:255',
            'phone' => 'required|max:255',
            'password' => 'nullable|max:255',
            'email' => [
                'required',
                'email',
                Rule::unique('users')->ignore($request->user_id),
            ],
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            $errors = $validator->errors();
            if($errors->has('email')){
                $str = " : ".$errors->first('email');
            }else {
                $str ="";
            }
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap ".$str,
            ], 404);
        } else {
            $user= User::where('id',$request->user_id)->where('role_id',2)->first();
            if($user !== null){
                if($request->password !== null){
                    User::where('id',$user->id)->update([
                        'name' => $request->name,
                        'email' => $request->email,
                        'phone' => $request->phone,
                        'password' => Hash::make($request->password),
                    ]);
                } else {
                    User::where('id',$user->id)->update([
                        'name' => $request->name,
                        'email' => $request->email,
                        'phone' => $request->phone,
                    ]);
                }
                return response()->json([
                    'status' => true,
                    'message' => "Data berhasil disimpan!",
                ], 200);
            } else {
                return response()->json([
                    'status' => false,
                    'message' => "User tidak ditemukan",
                ], 404);
            }
        }

    }
}
