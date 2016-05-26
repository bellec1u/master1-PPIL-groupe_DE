<?php

namespace App\Managers;


class ImageManager
{

    public function save($image)
    {
        if ($image->isValid()) {
            $path = config('images.path');
            $extension = $image->getClientOriginalExtension();

            do {
                $name = str_random(10) . '.' . $extension;
            } while(file_exists($path . '/' . $name));

            if ($image->move($path, $name)) {
                return $path . '/' . $name;
            }
            return false;
        }

        return false;
    }


}