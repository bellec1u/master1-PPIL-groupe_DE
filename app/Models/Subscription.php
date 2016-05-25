<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Subscription extends Model
{
    protected $fillable = [
        'user_id',
        'followed_user_id',
        'notifications_accepted'
    ];

    public function follower()
    {
        return $this->belongsTo('App\Models\User', 'user_id');
    }

    /*
     * The one who is followed by follower.
     */
    public function followee()
    {
        return $this->belongsTo('App\Models\User', 'followed_user_id');
    }
}
