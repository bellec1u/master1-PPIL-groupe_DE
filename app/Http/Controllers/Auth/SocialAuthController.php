<?php

namespace App\Http\Controllers\Auth;

use Illuminate\Http\Request;
use Socialite;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\Services\SocialAccountService;


class SocialAuthController extends Controller
{
    public function redirect($provider)
    {
        return Socialite::driver($provider)->redirect();   
    } 
  

    public function callback(SocialAccountService $service, Request $request, $provider)
    {
        if (!$request->has('error')) {
            $user = $service->createOrGetUser(Socialite::driver($provider));
            auth()->login($user);
        }

        return redirect()->to('/');
    }
   
}
