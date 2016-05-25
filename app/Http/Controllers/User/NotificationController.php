<?php

namespace App\Http\Controllers\User;

use App\Models\Notification;
use Illuminate\Http\Request;
use  App\Repositories\User\NotificationRepository;
use App\Http\Requests;
use App\Http\Controllers\Controller;
use Auth;

class NotificationController extends Controller
{
    protected $notifsRepository;


    public function __construct(NotificationRepository $notifsRep)
    {
        $this->notifsRepository = $notifsRep;

        $this->middleware('auth');
    }


    public function store(Array $request)
    {
        $user = Auth::user();
        $listFolowee = $user->subscriptionsTo;
        $request['notifier_user_id'] = $user->id;

        foreach($listFolowee as $followee){
            echo "rentre";
        $request['notified_user_id'] = $followee->followed_user_id;
            $this->notifsRepository->store($request);
        }

    }

    public function show(){
        if(Auth::check()){
            $user = Auth::user();
            $listeNotifs = $user->notificationsTo;

            return view('user\notification', compact('listeNotifs'));
        }
    }
    public function delete($id){
        $this->notifsRepository->destroy($id);
        if(Auth::check()){
            $user = Auth::user();
            $listeFolower = $user->notificationsTo;

            return view('user\notification', compact('listeFolower'));
        }

    }
    public function update($id){

    }
}
