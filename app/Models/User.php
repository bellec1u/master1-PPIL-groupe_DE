<?php

namespace App\Models;

use Illuminate\Foundation\Auth\User as Authenticatable;
use Hash;

class User extends Authenticatable
{
    /**
     * The attributes that are mass assignable.
     *
     * @var array
     */
    protected $fillable = [
        'email',
        'first_name',
        'last_name',
        'password',
        'sex',
        'birth_date',
        'profile_image',
        'following_allowed',
        'validation_code'
    ];

    /**
     * The attributes that should be hidden for arrays.
     *
     * @var array
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    public function setPasswordAttribute($password)
    {
        if (Hash::needsRehash($password)) {
            $this->attributes['password'] = bcrypt($password);
        } else {
            $this->attributes['password'] = $password;
        }
    }

    public function ratings()
    {
        return $this->hasMany('App\Models\Rating');
    }

    public function readings()
    {
        return $this->hasMany('App\Models\Reading');
    }

    public function bookmarks(){
        return $this->hasMany('App\Models\Bookmark');
    }
    public function socialAccount()
    {
        return $this->hasOne('App\Models\SocialAccount');
    }

    public function subscriptionsTo()
    {
        return $this->hasMany('App\Models\Subscription', 'user_id');
    }

    public function subscriptionsFrom()
    {
        return $this->hasMany('App\Models\Subscription', 'followed_user_id');
    }

    public function notificationsTo()
    {
        return $this->hasMany('App\Models\Notification', 'notifier_user_id')->orderBy('created_at', 'desc');
    }

    public function notificationsFrom()
    {
        return $this->hasMany('App\Models\Notification', 'notified_user_id')->orderBy('created_at', 'desc');
    }
    
}
