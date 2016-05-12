<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Book extends Model
{
     protected $fillable = [
         'id', 'title', 'author', 'publication_date', 'resume', 'genre', 'langage', 'star_average', 'url', 'cover_url', 'page_number'
    ];

}
