<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Book extends Model
{
    protected $fillable = [
        'id',
        'title',
        'author',
        'publication_date',
        'resume',
        'genre',
        'language',
        'stars_average',
        'url',
        'cover_url',
        'page_number'
    ];

    public $timestamps = false;

    public function ratings(){
        return $this->hasMany('App\Models\Rating');
    }

    public function reading(){
        return $this->hasMany('App\Models\Reading');
    }
}
