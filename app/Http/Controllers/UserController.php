<?php

namespace App\Http\Controllers;

use App\Http\Requests\UserCreateRequest;
use App\Http\Requests\UserUpdateRequest;

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
		return view('create');
	}

    public function store(UserCreateRequest $request)
    {
        // chargement de l'image pour copie sur le serveur.
        $file = array('profile_image' => Input::file('profile_image'));
        if (Input::hasFile('profile_image') && Input::file('profile_image')->isValid()) {
            $destinationPath = 'uploads'; // upload path
            $extension = Input::file('profile_image')->getClientOriginalExtension(); // getting image extension
            $fileName = rand(11111, 99999) . '.' . $extension; // renameing image
            if (Input::file('profile_image')->move(base_path() . '/public/imageUser/',
                $fileName)
            ) {
                $path = base_path() . '/public/imageUser/' . $fileName;
                $request2 = $request->treatment($path);
                $user = $this->userRepository->store($request2);
            return redirect('/');

            }
        } else {
            $user = $this->userRepository->store($request->all());
            return redirect('/');

        }
      }
     
    
     
    
   
		
	



}