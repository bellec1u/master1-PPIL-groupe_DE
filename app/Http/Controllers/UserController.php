<?php

namespace App\Http\Controllers;


use App\Http\Requests\User\UserCreateRequest;
use App\Http\Requests\User\UserUpdateRequest;
use Auth;
use App\Repositories\UserRepository;
use App\Services\EmailConfirmationService;
use Illuminate\Support\Facades\Input;
//use Illuminate\Http\Request;

class UserController extends Controller
{

    protected $userRepository;

    public function __construct(UserRepository $userRepository)
	{
		$this->userRepository = $userRepository;
	}



	public function create()
	{
		return view('user.inscription');
	}


    public function store(UserCreateRequest $request)
    {
        if ($request->hasFile('profile_image')) {
            $image = $request->file('profile_image');
            $this->userRepository->storeWithImage($request->all(), $image);
        } else {
            $this->userRepository->store($request->all());
        }

        return redirect('/')
            ->with('status', 'Vous avez bien été enregistré.
                              Un mail de confirmation vous a été envoyé.');
    }


    public function update(UserUpdateRequest $request, $id)
    {
        if ($request->hasFile('profile_image')) {
            $image = $request->file('profile_image');
            $this->userRepository->updateWithImage($id, $request->all(), $image);
        } else {
            $this->userRepository->update($id, $request->all());
        }

        return redirect('user/'.$id)->with('status', 'Profil mis à jour');
    }


    public function show($id)
    {
        $user = $this->userRepository->getById($id);
        return view('user.profile', compact('user'));
    }


    public function confirmEmail(EmailConfirmationService $emailConfService, $token)
    {
        $user = $emailConfService->confirmEmail($token);
        if ($user) {
            Auth::login($user);
            return redirect('/')->with('status', 'Votre email a été validé. Bienvenue !');
        } else {
            return redirect('/');
        }
    }
     
    
   
		
	



}