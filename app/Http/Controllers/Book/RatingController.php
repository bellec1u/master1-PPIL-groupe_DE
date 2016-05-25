<?php

namespace App\Http\Controllers\Book;


use App\Http\Requests\Book\RatingCreateRequest;
use App\Repositories\Book\RatingRepository;
use App\Http\Requests;
use App\Repositories\Book\BookRepository;
use App\Http\Controllers\Controller;
use App\Repositories\User\UserRepository;
use Auth;
use Redirect;
use Illuminate\Support\Facades\Response;

class RatingController extends Controller
{

    protected $ratingRepository;
    protected $bookRepository;
    protected $userRepository;
    protected $noteMoyenne = 0;

    public function __construct(RatingRepository $ratingRepository, BookRepository $bookRepository, UserRepository $userRepository)
    {

        $this->bookRepository = $bookRepository;
        $this->ratingRepository = $ratingRepository;
        $this->userRepository = $userRepository;

    }

    /**
     * @param $id id d'un livre
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function create($id)
    {
        $book = $id;
        $rating = $this->ratingRepository->getRatingIdEtUser($book, Auth::user()->id );
        if(count($rating) == 0){
            return view('book/evaluate', compact('book'));
        }
        else{
            return redirect()->route('bookReturn', ['id' => $book]);
        }

    }



    /**
     * @param RatingCreateRequest $request
     * @return \Illuminate\Http\RedirectResponse|\Illuminate\Routing\Redirector
     */
    public function store(RatingCreateRequest $request)
    {
        $rating = $this->ratingRepository->getRatingIdEtUser($request->book_id,Auth::user()->id );
        if(count($rating) ==0){
            // on enregistre le commentaire et la note.
            $inputs = array_merge($request->all(), ['user_id' => $request->user()->id]);
            $this->ratingRepository->store($inputs);

            if ($request->stars != 0) {
                // on recupère toutes les notes  du livre
                $notesRepository = $this->ratingRepository->getArraycom($request->book_id);

                $nbNotes = $notesRepository->count();


                // on calcule la somme de toutes les notes
                $notesRepository->each(function ($noteRepository) {

                    $this->noteMoyenne += $noteRepository->stars;
                });
                // on calcule la moyenne
                $this->noteMoyenne /= $nbNotes;

                // on recupère le livre
                $book = $this->bookRepository->getById($request->book_id);
                // on modifie sa moyenne
                $book->stars_average = $this->noteMoyenne;


                // on enregistre la modification dans la base de données.
                $this->bookRepository->update($request->book_id, $book->toArray());
            }

        }


        return redirect()->route('bookReturn', ['id' => $request->book_id]);
    }

    public function edit($id)
    {
        $ratings = $this->ratingRepository->getById($id);
        if (Auth::user()->id == $ratings->user_id) {

            return view('book/editRating', compact('ratings'));
        } else {
            return redirect()->route('bookReturn', ['id' => $ratings->book_id]);
        }


    }

    public function update(RatingCreateRequest $request)
    {
        $test = $this->ratingRepository->getById($request->id);
        if($test->user_id == Auth::user()->id){
            $inputs = array_merge($request->all(), ['user_id' => $request->user()->id]);
            $this->ratingRepository->update($request->id, $inputs);
            // on recupère toutes les notes  du livre
            $notesRepository = $this->ratingRepository->getArraycom($request->book_id);

            $nbNotes = $notesRepository->count();


            // on calcule la somme de toutes les notes
            $notesRepository->each(function ($noteRepository) {

                $this->noteMoyenne += $noteRepository->stars;
            });
            // on calcule la moyenne
            $this->noteMoyenne /= $nbNotes;

            // on recupère le livre
            $book = $this->bookRepository->getById($request->book_id);
            // on modifie sa moyenne
            $book->stars_average = $this->noteMoyenne;


            // on enregistre la modification dans la base de données.
            $this->bookRepository->update($request->book_id, $book->toArray());
        }



        return redirect()->route('bookReturn', ['id' => $request->book_id]);
    }


    public function destroy($id, $idBook)
    {
        $rating = $this->ratingRepository->getById($id);
        if (Auth::user()->id == $rating->user_id) {

            $this->ratingRepository->destroy($id);
            // on recupère toutes les notes  du livre

            $notesRepository = $this->ratingRepository->getArraycom($idBook);
            $nbNotes = $notesRepository->count();
            if ($nbNotes != 0) {
                $notesRepository->each(function ($noteRepository) {

                    $this->noteMoyenne += $noteRepository->stars;
                });
                // on calcule la moyenne
                $this->noteMoyenne /= $nbNotes;
            } else {
                $this->noteMoyenne = 0;
            }


            // on calcule la somme de toutes les notes

            // on recupère le livre
            $book = $this->bookRepository->getById($rating->book_id);
            // on modifie sa moyenne
            $book->stars_average = $this->noteMoyenne;


            // on enregistre la modification dans la base de données.
            $this->bookRepository->update($rating->book_id, $book->toArray());


            return redirect()->route('bookReturn', ['id' => $idBook]);
        } else {
            return redirect()->route('/');
        }
    }


   
}
