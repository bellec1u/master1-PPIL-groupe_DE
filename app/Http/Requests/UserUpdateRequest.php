<?php

namespace App\Http\Requests;

use App\Http\Requests\Request;

class UserUpdateRequest extends Request
{
    /**
     * Determine if the user is authorized to make this request.
     *
     * @return bool
     */
    public function authorize()
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array
     */
    public function rules()
    {
        return [
            'last_name'     => 'max:255|alpha',
            'first_name'    => 'max:255|alpha',
            'email'         => 'confirmed|email|max:255|unique:users',
            'password'      => 'confirmed|min:8',
            'birth_date'    => 'date',
            'profile_image' => 'image',
        ];
    }
}
