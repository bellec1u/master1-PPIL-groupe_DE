<?php

namespace App\Repositories\User;

use App\Managers\ImageManager;
use App\Models\User;
use App\Services\EmailConfirmationService;
use App\Repositories\ResourceRepository;

class UserRepository extends ResourceRepository
{

    protected $imageManager;
    protected $emailConfService;

    public function __construct(
        User $user,
        ImageManager $imgManager,
        EmailConfirmationService $emailConfServ
    ) {
        $this->model = $user;
        $this->imageManager = $imgManager;
        $this->emailConfService = $emailConfServ;
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

    public function store(Array $inputs)
    {
        $inputs = $this->emailConfService->sendConfirmationMail($inputs);
        return $this->model->create($inputs);
    }

}