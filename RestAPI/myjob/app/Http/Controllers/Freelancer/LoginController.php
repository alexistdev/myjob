<?php

namespace App\Http\Controllers\Freelancer;

use App\Http\Controllers\Controller;
use App\Models\Skillpengguna;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;
use Illuminate\Support\Str;

class LoginController extends Controller
{
    public function login(Request $request)
    {
        $rules = [
            'email' => 'required|email|max:255',
            'password' => 'required|max:255',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $data = [
                'email'     => $request->input('email'),
                'password'  => $request->input('password'),
            ];
            Auth::attempt($data);
            if (Auth::check()) {
                $user = User::where('id',Auth::user()->id)->where('token',Auth::user()->token)->first();
                if($user !== null){
                    User::where('id',Auth::user()->id)->update([
                        'token' => Str::random(32),
                    ]);
                    $dataUser = User::where('id',Auth::user()->id)->first();
                    return response()->json([
                        'status' => true,
                        'message' => "Berhasil",
                        'user_id' => Auth::user()->id,
                        'token' => $dataUser->token,
                        'role' => Auth::user()->role_id,
                    ], 200);
                } else {
                    return response()->json([
                        'status' => false,
                        'message' => "User tidak ditemukan!",
                    ], 404);
                }

            } else {
                return response()->json([
                    'status' => false,
                    'message' => "Username atau email salah!",
                ], 401);
            }
        }
    }

    public function daftar(Request $request)
    {
        $rules = [
            'email' => 'required|email|unique:users,email|max:255',
            'name' => 'required|max:255',
            'password' => 'required|max:255',
            'tipe' => 'required|numeric',
            'kategori' =>  'required|numeric',
        ];
        $validator = Validator::make($request->all(), $rules);
        if ($validator->fails()) {
            return response()->json([
                'status' => false,
                'message' => "data tidak lengkap",
            ], 404);
        } else {
            $user = new User();
            $user->role_id = $request->tipe;
            $user->name = $request->name;
            $user->email = $request->email;
            $user->phone = $request->password;
            $user->password = Hash::make($request->password);
            $user->status= 1;
            $user->save();
            $idUser = $user->id;
            if($request->tipe == 2){
                $skill = new Skillpengguna();
                $skill->user_id = $idUser;
                $skill->kategori_id = $request->kategori;
                $skill->save();
            }

            return response()->json([
                'status' => true,
                'message' => "berhasil",
            ], 200);
        }
    }
}
