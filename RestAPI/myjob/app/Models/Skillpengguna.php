<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Skillpengguna extends Model
{
    use HasFactory;

    public function tag(){
        return $this->hasOne(Kategori::class,'id','kategori_id');
    }
}
