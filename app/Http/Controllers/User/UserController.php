<?php

namespace App\Http\Controllers\User;


use App\Http\Requests\User\UserCreateRequest;
use App\Http\Requests\User\UserUpdateRequest;
use Auth;
use App\Repositories\User\UserRepository;
use App\Services\EmailConfirmationService;
use App\Http\Controllers\Controller;

//use Illuminate\Http\Request;

class UserController extends Controller
{

    protected $userRepository;

    public function __construct(UserRepository $userRepository)
	{
		$this->userRepository = $userRepository;

        $this->middleware('auth', ['only' => [
            'update',
            'profile',
            'delete',
            'showOther'
        ]]);
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


    public function update(UserUpdateRequest $request)
    {

        $id = Auth::user()->id;
        if ($request->hasFile('profile_image')) {
            $image = $request->file('profile_image');
            $this->userRepository->updateWithImage($id, $request->all(), $image);
        } else {
            $this->userRepository->update($id, $request->all());
        }

        return redirect('user/profile')->with('status', 'Profil mis à jour');
    }


    public function profile()
    {
        $user = Auth::user();
        return view('user.profile', compact('user'));
    }

    public function edit()
    {
        $user = Auth::user();
        return view('user.edit', compact('user'));
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

    public function resendEmail(EmailConfirmationService $emailConfService, $id)
    {
        $user = $this->userRepository->getById($id);
        if ($user && !$user->email_validated) {
            $emailConfService->resendConfirmationMail($id);
            return redirect('/')->with('status', 'Email de validation à été envoyé');
        } else {
            return redirect()->back();
        }
    }


    public function delete()
    {
        $user = $this->userRepository->getById(Auth::user()->id);
        Auth::logout();

        if ($user->delete()) {
            return redirect('/')->with('status', 'Votre compte a été supprimé');
        }
    }

    public function showOther($id)
    {
        $user = $this->userRepository->getById($id);
        $followers = Auth::user()->subscriptionsTo;
        $estSuivi = false;
        $idFollower = 0;
        foreach ($followers as $follower) {
            if ($follower->followed_user_id == $id) {
                $estSuivi = true;
            }
            $idFollower = $follower->id;
        }
        return view('user/consultOther',
            compact('user', 'estSuivi', 'idFollower'));
    }

    public function registration()
    {
        if (Auth::check()) {
            return redirect('/')->with('status',
                'Déjà connecté inscription abandonné !!');
        } else {
            return view("user/inscription");
        }
    }

    public function following_allowed()
    {
        if (Auth::check()) {
            $user = Auth::user();
            $user->following_allowed = !$user->following_allowed;

            $this->userRepository->update($user->id, $user->toArray());
        }

        return redirect()->back();
    }

    

}