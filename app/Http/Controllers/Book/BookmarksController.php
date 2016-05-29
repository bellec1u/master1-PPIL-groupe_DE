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

        if(Auth::check()){
            $user =  Auth::user();
            $bookmarks = $this->bookmarksRepository->getBookmarkIdAndUser($idBook,$user->id );
            if(count($bookmarks) == 0){
                $test['user_id'] =$user->id;
                $test['book_id'] = $idBook;
                $test['page'] =$_GET['path'];

                $this->bookmarksRepository->store($test);
            }
            else{
                foreach($bookmarks as $bookmark){

                    if($bookmark->page == $_GET['path']){
                        $this->bookmarksRepository->destroy($bookmark->id);
                    }else{
                        $bookmark->page = $_GET['path'];
                        $this->bookmarksRepository->update($bookmark->id, array_merge($bookmark->toArray()) );
                    }


                }

            }
        }



        return response()->json();
    }
    public function isActualBookmark($idBook){
        if(Auth::check()){
            $user =  Auth::user();
            $bookmarks = $this->bookmarksRepository->getBookmarkIdAndUser($idBook,$user->id );
            if(count($bookmarks) == 0){
                $response = false;
            }
            else{
                foreach($bookmarks as $bookmark){
                    if($bookmark->page == $_GET['bookMark']){
                        $response =  true;
                    }
                    else{
                        $response = false;
                    }


                }
            }
        }
        else{
            $response = false;
        }



        return Response::json(array(

            'erreur' => "non abouti",

            'datas' => $response,

            'message' => 'retour ajax de la methode getItems',

        ));
    }
}
