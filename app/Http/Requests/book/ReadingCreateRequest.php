<?php

namespace App\Http\Requests\book;

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