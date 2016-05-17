<?php

namespace App\Http\Controllers\book;


use App\Http\Requests\book\RatingCreateRequest;
use App\Repositories\book\RatingRepository;
use App\Http\Requests;
use App\Repositories\BookRepository;
use App\Http\Controllers\Controller;

class RatingController extends Controller
{

    protected $ratingRepository;
    protected $bookRepository;
    protected   $noteMoyenne =0;
    public function __construct(RatingRepository $ratingRepository, BookRepository $bookRepository)
    {

        $this->bookRepository = $bookRepository;
        $this->ratingRepository = $ratingRepository;
    }

    public function create($id){
        $book = $id;
        return view('book/evaluate', compact('book'));
    }
    
    public function store(RatingCreateRequest $request){


        $inputs = array_merge($request->all(), ['user_id' => $request->user()->id]);
        $this->ratingRepository->store($inputs);


        $notesRepository = $this->ratingRepository->getArraycom($request->book_id);

        $nbNotes = $notesRepository->count();

        $notesRepository->each(function($noteRepository){

            $this->noteMoyenne += $noteRepository->stars;
        });

        $this->noteMoyenne /= $nbNotes;

        $book = $this->bookRepository->getById($request->book_id);
        $book->stars_average = $this->noteMoyenne;



        echo $book->stars_average;
        $this->bookRepository->update($request->book_id, $book->toArray());


        return redirect(route('/'));
    }
    public function destroy($id){
        $this->ratingRepository->destroy($id);
        return redirect_to('/');
    }
}
