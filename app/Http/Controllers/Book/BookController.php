<?php

namespace App\Http\Controllers\Book;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Repositories\Book\BookRepository;
use App\Repositories\Book\RatingRepository;
use App\Http\Controllers\Controller;
use App\Http\Controllers\Book\FichierController;
class BookController extends Controller
{
    protected $bookRepository;
    protected $ratingRapository;
    protected $fichier;

    public function __construct(BookRepository $bookRepository, RatingRepository $ratingRepository, FichierController $fichierController)
    {
        $this->bookRepository = $bookRepository;
        $this->ratingRapository = $ratingRepository;
        $this->fichier = $fichierController;
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

    }

    /**
     * Display the specified resource.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    
    public function show($id)
    {

        $book = $this->bookRepository->getById($id);
        $ratings = $this->ratingRapository->getRatingId($id);

        return view('book/detailsBook', compact('book'))->with('data', $ratings);

    }

    /**
     * ouverture d'un livre
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function open($id)
    {

        $book = $this->bookRepository->getById($id);
        $path = $book->url;
        $id_book = basename($path);
        $file_exist =  $_SERVER['DOCUMENT_ROOT'].'/PPILresetA/public/Books/Book'.$id_book.'.epub';
        if(!file_exists($file_exist)){
            $this->fichier->download_Book($id_book);
        }


        
        return view('book/basic')->with('idBook', $id_book);

    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request $request
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }
}
