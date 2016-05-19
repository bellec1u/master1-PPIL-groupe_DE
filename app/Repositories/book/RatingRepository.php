<?php

namespace App\Repositories\Book;

use App\Models\Rating;
use App\Repositories\ResourceRepository;
use App\Models\Book;
class RatingRepository extends ResourceRepository
{
    protected $rating;
    public function __construct(Rating $rating){

        $this->model = $rating;

    }

    public function getArraycom($id_book){
    
        return $this->rating->where('book_id', '=' , $id_book)->where('stars', '>' , '0');
    }

    public function getRatingId($id_book){
    
        return $this->model->where('book_id', '=' , $id_book)->get();
    }
    
    public function getRatingIdEtUser($id_book, $id_user){
    
        return $this->model->where('book_id', '=' , $id_book)->where('user_id', '=' , $id_user)->get();
    }

}