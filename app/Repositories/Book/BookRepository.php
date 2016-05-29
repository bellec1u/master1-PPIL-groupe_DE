<?php

namespace App\Repositories\Book;

use App\Models\Book;
use App\Repositories\ResourceRepository;

class BookRepository extends ResourceRepository
{
    protected $search_params;

    public function __construct(Book $book)
    {
        $this->model = $book;
    }

    /**
     * Retrieves proper parameter value from user search input
     *
     * @param $key
     * @return
     */
    private function param($key)
    {
        $value = array_get($this->search_params, $key);
        if ($value) {
            return $value;
        } else {
            return '%%';
        }
    }

    /**
     * Builds proper closure for search query depending on the existence of
     * 'author' parameter
     *
     * @param $hasAuthor boolean
     * @param $author author value
     * @return \Closure
     */
    private function make_closure($hasAuthor, $author)
    {
        if ($hasAuthor) {
            return function ($query) use ($author) {
                $query->where('author', 'LIKE', $author)
                    ->where('title', 'LIKE', $this->param('query'));
            };
        } else {
            return function ($query) use ($author) {
                $query->where('author', 'LIKE', $author)
                    ->OrWhere('title', 'LIKE', $this->param('query'));
            };
        }
    }

    public function search($inputs, $nbPerPage)
    {
        $inputs = array_only($inputs, ['query', 'author', 'genre', 'lang']);
        // check if all values are empty
        if (!array_filter($inputs, function($val) {return trim($val);})) {
            return $this->model->where('id', '=', '')->paginate(1);
        }

        // prepare values for search query
        $params = array_map(function ($val) {
            return '%'.trim($val).'%';
        }, $inputs);
        $this->search_params = $params;

        // make closure depending on 'author' parameter
        $author = $this->param('author');
        if ($author != '%%') {
            $closure = $this->make_closure(true, $author);
        } else {
            $closure = $this->make_closure(false, $this->param('query'));
        }

        // execute query
        $books = $this->model
            ->orWhere($closure)
            ->where('genre', 'LIKE', $this->param('genre'))
            ->where('language', 'LIKE', $this->param('lang'))->paginate($nbPerPage);

		return $books;
	}

    public function getBestBook()
    {
        return $this->model->orderBy('stars_average', 'DESC')->get();
    }

    public function getLanguageListAsArray()
    {
        $langs = $this->model->select('language')->groupBy('language')->get();
        $lang_arr = ['' => '-'];
        foreach ($langs as $lang) {
            $lang_str = $lang['language'];
            $lang_arr[$lang_str] = $lang_str;
        }
        return $lang_arr;
    }


}