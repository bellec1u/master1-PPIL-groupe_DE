<?php

namespace App\Repositories\book;

use App\Models\Reading;
use App\Repositories\ResourceRepository;
use App\Models\Book;
class ReadingRepository extends ResourceRepository
{
    protected $reading;
    public function __construct(Reading $reading){

        $this->model = $reading;
        $this->reading = $reading;
    }

    public function getArrayList($id_book){

        return $this->reading->where('book_id', '=' , $id_book);
    }

    public function getReadingId($id_book){
        
        return Reading::where('book_id', '=' , $id_book)->get();
    }

    public function getReadingIdAndUser($id_book, $id_user){
    
        return Reading::where('book_id', '=' , $id_book)->where('user_id', '=' , $id_user)->get();
    }

}
