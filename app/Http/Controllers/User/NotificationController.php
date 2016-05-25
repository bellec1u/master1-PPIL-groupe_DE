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
        $listFolowee = $user->subscriptionsFrom;
        $request['notifier_user_id'] = $user->id;

        foreach($listFolowee as $followee){

           if($followee->notifications_accepted == 1){
               $request['notified_user_id'] = $followee->user_id;
               $this->notifsRepository->store($request);
           }

        }

    }

    public function show(){
        if(Auth::check()){
            $user = Auth::user();
            $notifications = $user->notificationsFrom;
            
            return view('user\notification', compact('notifications'));
        }
    }
    
    public function delete($id){
        $this->notifsRepository->destroy($id);
        if(Auth::check()){
            $user = Auth::user();
            $notifications = $user->notificationsTo;

            return view('user\notification', compact('notifications'));
        }

    }
    public function update($id){

    }
}
