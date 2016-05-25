<?php

namespace App\Http\Controllers\User;

use App\Repositories\User\SubscriptionRepository;
use Illuminate\Http\Request;

use App\Http\Requests\User\SubscriptionRequest;
use App\Http\Controllers\Controller;
use Auth;
class SubscriptionController extends Controller
{
    protected $subsRepository;

    public function __construct(SubscriptionRepository $subsRep)
    {
        $this->subsRepository = $subsRep;

        $this->middleware('auth');
    }


    public function store(SubscriptionRequest $request)
    {
        $inputs = array_merge($request->all(),
            ['user_id' => $request->user()->id]);
        if ($this->subsRepository->addFollower($inputs)) {
            $status = 'Utilisateur ajouté à la liste de suivi';
        } else {
            $status = 'Utilisateur est déjà dans votre liste de suivi';
        }
        return redirect()->back()->with('status', $status);
    }

    public function show(){
        if(Auth::check()){
            $user = Auth::user();
            $listeFolower = $user->subscriptionsTo;

            return view('user\subscription', compact('listeFolower'));
        }
    }
    public function delete($id){


        if(Auth::check()){
            if(count($this->subsRepository->getById($id)) != 0){
                $this->subsRepository->destroy($id);
            }



            $status = ' Utilisateur supprimé de votre liste de suivi';
              return redirect()->back()->with('status', $status);
        }
        
    }
    public function update($id){
    $sub = $this->subsRepository->getById($id);
        $sub->notifications_accepted = ! $sub->notifications_accepted;
        $this->subsRepository->update($id,$sub->toArray());
        if(Auth::check()){
            $user = Auth::user();
            $listeFolower = $user->subscriptionsTo;

            return view('user\subscription', compact('listeFolower'));
        }
    }
}
