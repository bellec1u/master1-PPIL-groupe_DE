<?php

namespace App\Http\Controllers\Api;

use App\Repositories\UserRepository;
use Illuminate\Http\Request;
use JWTAuth;
use Validator;

use App\Http\Controllers\Controller;

class JWTAuthController extends Controller
{
    protected $userRepository;

    public function __construct(UserRepository $userRepository)
    {
        $this->userRepository = $userRepository;
    }

    private function validator(Array $input)
    {
        return Validator::make($input, [
            'last_name'  => 'required|max:255',
            'first_name' => 'required|max:255',
            'email'      => 'required|email|max:255|unique:users',
            'password'   => 'required|min:8',
            'birth_date' => 'required|date',
        ]);
    }

    /**
     * Registers a new user, saves into database and returns a token.
     *
     * @param $request
     * @return json token if everything is OK, error message otherwise
     */
    public function store(Request $request)
    {
        if ($this->validator($request->all())->fails()) {
            return response()->json(['error' => 'user already exists'], 409);
        }

        $user = $this->userRepository->store($request->all());
        $token = JWTAuth::fromUser($user);

        return response()->json(compact('token'));
    }

    /**
     * Authenticates a user and returns a json token.
     *
     * @param $request
     * @return json token if everything is OK, error message otherwise
     */
    public function login(Request $request)
    {
        $credentials = $request->only('email', 'password');

        try {
            // attempt to verify the credentials and create a token for the user
            if (! $token = JWTAuth::attempt($credentials)) {
                return response()->json(['error' => 'invalid credentials'], 401);
            }
        } catch (JWTException $e) {
            // something went wrong whilst attempting to encode the token
            return response()->json(['error' => 'could not create token'], 500);
        }

        // all good so return the token
        return response()->json(compact('token'));
    }

    /**
     * Invalidates a token passed as parameter, thus logging out a user.
     *
     * @param Request $request
     * @return json status message
     */
    public function logout(Request $request)
    {
        JWTAuth::parseToken()->invalidate();

        return response()->json(['status' => 'logged out'], 200);
    }

}
