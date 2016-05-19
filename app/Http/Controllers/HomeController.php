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
       

        for($i =0; $i<= 10 ; $i++){
            $id = rand(1,1000);
            $book1 = $this->bookRepository->getById($id);
            $liste[] = $book1;
        }
        return view('index')->with('liste', $liste);
    }
}
