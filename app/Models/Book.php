<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Book extends Model
{
    protected $fillable = [
        'title',
        'author',
        'publication_date',
        'resume',
        'genre',
        'language',
        'star_average',
        'url',
        'cover_url',
        'page_number'
    ];

}
