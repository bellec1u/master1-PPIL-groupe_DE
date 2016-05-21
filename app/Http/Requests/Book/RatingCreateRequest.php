<?php

namespace App\Http\Requests\Book;

use App\Http\Requests\Request;

class RatingCreateRequest extends Request
{

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        return [
            'comment' => 'required'
        ];
    }


}