<?php

namespace App\Repositories\User;

use App\Models\Subscription;
use App\Repositories\ResourceRepository;

class SubscriptionRepository extends ResourceRepository
{

    public function __construct(Subscription $subs)
    {
        $this->model = $subs;
    }
}