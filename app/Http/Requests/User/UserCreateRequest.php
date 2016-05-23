<?php

namespace App\Http\Requests\User;

use App\Http\Requests\Request;

class UserCreateRequest extends Request
{


    public function authorize()
    {
        return true;
    }

    public function rules()
    {

        return [

            'last_name'     => 'required|max:255|alpha',
            'first_name'    => 'required|max:255|alpha',
            'email'         => 'required|email|confirmed|max:255|unique:users',
            'password'      => 'required|confirmed|min:8',
            'birth_date'    => 'required|date',
            'profile_image' => 'image',
        ];
    }
    
}