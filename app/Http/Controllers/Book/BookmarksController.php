<?php

namespace App\Http\Controllers\Book;

use App\Repositories\Book\BookmarksRepository;
use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use Illuminate\Support\Facades\Response;
use Auth;
class BookmarksController extends Controller
{
    protected $bookmarksRepository;

    public function __construct(BookmarksRepository $bookmarksRepository)
    {
        $this->bookmarksRepository = $bookmarksRepository;
     
    }

    public function add($idBook){

        $test['user_id'] = Auth::user()->id;
        $test['book_id'] = $idBook;
        $test['page'] = $_GET['path'];
        
        $this->bookmarksRepository->store($test);

        return response()->json();
    }
}
