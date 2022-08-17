<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Chat extends Model
{
    use HasFactory;
    protected $fillable =['user_id','pesan','isAdmin'];
    protected $casts = [
        'created_at' => 'date:Y-m-d',
    ];

    public function user(){
        return $this->belongsTo(User::class);
    }

    public function jobs(){
        return $this->belongsToMany(Job::class);
    }

    public function jobrelated(){
        return $this->belongsTo(Job::class,'job_id','id');
    }

}
