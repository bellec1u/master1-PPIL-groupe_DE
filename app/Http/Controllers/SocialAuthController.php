<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
use App\SocialAccountService;
use Socialite;

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
