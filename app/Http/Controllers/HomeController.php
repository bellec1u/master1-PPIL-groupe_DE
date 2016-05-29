<?php

namespace App\Http\Controllers;

use App\Http\Requests;
use Illuminate\Http\Request;
use App\Repositories\Book\BookRepository;


class HomeController extends Controller
{
    protected $bookRepository;
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct(BookRepository $bookRepository)
    {
        $this->bookRepository = $bookRepository;
    }

    /**
     * Show the application dashboard.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
       $books = $this->bookRepository->getBestBook();


        $top10 = [];
        for($i =0; $i < 10 ; $i++){
            $top10[] = $books[$i];
        }
        return view('index', compact('top10'));
    }
}
