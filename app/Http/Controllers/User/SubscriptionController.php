<?php

namespace App\Http\Controllers\User;

use App\Repositories\User\SubscriptionRepository;
use Illuminate\Http\Request;

use App\Http\Requests\User\SubscriptionRequest;
use App\Http\Controllers\Controller;

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
        $this->subsRepository->store($inputs);
        
        return redirect()->back()->with('status',
            'Utilisateur ajouté à la liste de suivi');
    }
}
