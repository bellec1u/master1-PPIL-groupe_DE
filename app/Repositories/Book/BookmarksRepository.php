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









}