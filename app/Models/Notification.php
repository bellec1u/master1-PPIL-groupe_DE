<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Notification extends Model
{


    protected $fillable = [
        'notifier_user_id',
        'notified_user_id',
        'book_id',
        'type',
        'details'
    ];

    public function notifier()
    {
        return $this->belongsTo('App\Models\User', 'notifier_user_id');
    }

    /*
     * The one who is followed by follower.
     */
    public function notified()
    {
        return $this->belongsTo('App\Models\User', 'notified_user_id');
    }

    public function book()
    {
        return $this->belongsTo('App\Models\Book', 'book_id');
    }
}
