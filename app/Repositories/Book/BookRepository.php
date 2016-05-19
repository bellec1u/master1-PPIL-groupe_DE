<?php

namespace App\Repositories\Book;

use App\Models\Book;
use App\Repositories\ResourceRepository;

class BookRepository extends ResourceRepository
{

    public function __construct(Book $book)
	{
		$this->model = $book;
	}
	

	

}