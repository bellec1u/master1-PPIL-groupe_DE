<?php

namespace App\Repositories\book;

use App\Models\Rating;
use App\Repositories\ResourceRepository;
use App\Models\Book;
class RatingRepository extends ResourceRepository
{
    protected $rating;
    public function __construct(Rating $rating)
    {
        $this->model = $rating;
        $this->rating = $rating;
    }

    public function getArraycom($id_book){

        return $this->rating->where('book_id', '=' , $id_book);
    }
    public function getRatingId($id_book){
        return Rating::where('book_id', '=' , $id_book)->get();
    }

}