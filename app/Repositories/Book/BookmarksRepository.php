<?php

namespace App\Repositories\Book;

use App\Models\Book;
use App\Models\Bookmark;
use App\Repositories\ResourceRepository;

class BookmarksRepository extends ResourceRepository
{

    public function __construct(Bookmark $book)
    {
        $this->model = $book;
    }
    public function getBookmarkIdAndUser($id_book, $id_user)
    {
        return $this->model->where('book_id', '=', $id_book)->where('user_id', '=', $id_user)->get();
    }









}