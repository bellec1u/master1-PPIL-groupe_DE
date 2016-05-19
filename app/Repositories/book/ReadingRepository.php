<?php

namespace App\Repositories\book;

use App\Models\Reading;
use App\Repositories\ResourceRepository;
use App\Models\Book;

class ReadingRepository extends ResourceRepository
{
    public function __construct(Reading $reading)
    {
        $this->model = $reading;
    }

    public function getArrayList($id_book)
    {
        return $this->model->where('book_id', '=', $id_book);
    }

    public function getReadingId($id_user)
    {
        return $this->model->where('user_id', '=', $id_user)->get();
    }

    public function getReadingIdAndUser($id_book, $id_user)
    {
        return $this->model->where('book_id', '=', $id_book)->where('user_id', '=', $id_user)->get();
    }

    public function delete($id_book, $id_user)
    {
        $this->model->where('book_id', '=', $id_book)->where('user_id', '=', $id_user)->delete();
    }

}
