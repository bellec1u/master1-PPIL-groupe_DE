<?php

namespace App\Http\Requests\Book;

use App\Http\Requests\Request;

class ReadingCreateRequest extends Request
{

    public function authorize()
    {
        return true;
    }

    public function rules()
    {
        //a voir
    }


}