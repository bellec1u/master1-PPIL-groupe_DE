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
	
	public function search($query)
	{
		if (empty(trim($query))) return [];
		
		$query = '%'.$query.'%';
		$books = $this->model->where('author', 'LIKE', $query)
			->orWhere('title', 'LIKE', $query)
			->orWhere('genre', 'LIKE', $query)->get();

		return $books;
	}
	
	

}