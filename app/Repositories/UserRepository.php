<?php

namespace App\Repositories;

use App\Managers\ImageManager;
use App\Models\User;

class UserRepository extends ResourceRepository
{

    private $imageManager;

    public function __construct(User $user, ImageManager $imgManager)
    {
        $this->model = $user;
        $this->imageManager = $imgManager;
    }

    public function storeWithImage(Array $inputs, $image)
    {
        if ($path = $this->imageManager->save($image)) {
            $inputs['profile_image'] = $path;
            return $this->store($inputs);
        }

        return false;
    }

    public function updateWithImage($id, Array $inputs, $image)
    {
        if ($path = $this->imageManager->save($image)) {
            $inputs['profile_image'] = $path;
            $this->update($id, $inputs);
        }

        return false;
    }

}