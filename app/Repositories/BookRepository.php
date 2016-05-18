<?php

namespace App\Repositories;

use App\Models\Book;

class BookRepository extends ResourceRepository
{

    public function __construct(Book $book)
	{
		$this->model = $book;
	}
	

	

}