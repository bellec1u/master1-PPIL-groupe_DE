<?php

namespace App\Repositories\User;

use App\Models\Notification;
use App\Repositories\ResourceRepository;

class NotificationRepository extends ResourceRepository
{

    public function __construct(Notification $subs)
    {
        $this->model = $subs;
    }

   /* public function addFollower(Array $inputs)
    {
        $count = $this->model->where('user_id', '=', $inputs['user_id'])
            ->where('followed_user_id', '=', $inputs['followed_user_id'])->count();
        if ($count == 0) {
            $this->store($inputs);
            return true;
        } else {
            return false;
        }
    }*/

    
}