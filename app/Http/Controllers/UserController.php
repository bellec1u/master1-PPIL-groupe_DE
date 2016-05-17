<?php

namespace App\Http\Controllers;

use App\Http\Requests\UserCreateRequest;
use App\Http\Requests\UserUpdateRequest;
use App\Managers\ImageManager;

use App\Repositories\UserRepository;
use Illuminate\Support\Facades\Input;
//use Illuminate\Http\Request;

class UserController extends Controller
{

    protected $userRepository;

    protected $nbrPerPage = 4;

    public function __construct(UserRepository $userRepository)
	{
		$this->userRepository = $userRepository;
	}


	public function index()
	{
		$users = $this->userRepository->getPaginate($this->nbrPerPage);
		$links = $users->render();

		return view('index', compact('users', 'links'));
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

        return redirect('/')->with('status', 'Vous avez bien été enregistré');
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


     
    
   
		
	



}