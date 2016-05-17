<?php

namespace App\Http\Requests\book;

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