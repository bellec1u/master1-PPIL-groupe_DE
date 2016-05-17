<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Rating extends Model
{
    protected $fillable = [
        'book_id',
        'user_id',
        'comment',
        'stars'

    ];

    public function user(){
        return $this->belongsTo('App\Models\User');
    }
    public function book(){
        return $this->belongsTo('App\Models\book');
    }

}
