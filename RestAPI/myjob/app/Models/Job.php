<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Casts\Attribute;
use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Job extends Model
{
    use HasFactory;
    protected $fillable =[
      'user_id','kategori_id','name','deskripsi','fee','deadline','bidder','status'
    ];

    protected function deadline(): Attribute
    {
        return Attribute::make(
            get: fn ($value) => date("d-m-Y",strtotime($value)),
        );
    }

    protected function fee(): Attribute
    {
        return Attribute::make(
            get: fn ($value) => number_format($value,0,".","."),
        );
    }
}
