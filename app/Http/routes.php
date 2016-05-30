<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/index', 'HomeController@index');
Route::get('/', array('uses'=>'HomeController@index', 'as' =>'/'));

Route::auth();
// standard
Route::get('user/profile', 'User\UserController@profile');
Route::resource('user', 'User\UserController',['except' => ['index', 'edit', 'update', 'show', 'destroy']]);
Route::get('user/consult/{id}', ['uses' => 'User\UserController@showOther','as'   => 'showOtherUser'])->where('id', '[0-9]+');
Route::put('user/update',['uses' => 'User\UserController@update', 'as' => 'userUpdate']);
Route::delete('user/desinscription',['uses' => 'User\UserController@delete', 'as' => 'userDelete']);
Route::get('user/edit',['uses' => 'User\UserController@edit', 'as' => 'userEdit']);
Route::post('following_allowed' , ['uses'=>'User\UserController@following_allowed', 'as'=>'following_allowed']);

// email validation
Route::get('user/verify/{token}', 'User\UserController@confirmEmail')->where('token', '[a-zA-Z0-9]+');
Route::get('user/{id}/resend',['as' => 'resendEmail', 'uses' => 'User\UserController@resendEmail'])->where('id', '[0-9]+');

//subscriptions
Route::post('follow',['uses' => 'User\SubscriptionController@store', 'as' => 'addFollower']);
Route::get('follow',['uses' => 'User\SubscriptionController@show', 'as' => 'ConsultFollower']);
Route::delete('follow/delete/{id}',['uses' => 'User\SubscriptionController@delete', 'as' => 'deleteFollower']);
Route::put('follow/update/{id}', ['uses' => 'User\SubscriptionController@update', 'as' => 'updateFollower']);

// Book access
// details
Route::get('book/{id}', ['as' => 'bookReturn', 'uses' => 'Book\BookController@show'])->where('id', '[0-9]+');

// Book modif
Route::get('createRating/{id}',['uses' => 'Book\RatingController@create', 'as' => 'createRating'])->where('id', '[0-9]+');
Route::post('storeRating', ['uses' => 'Book\RatingController@store', 'as' => 'storeRating']);

// liste de lecture.
Route::get('bookshelf/add/{id}', ['uses' => 'Book\ReadingController@add', 'as' => 'addReading'])->where('id', '[0-9]+');
Route::get('bookshelf', ['uses' => 'Book\ReadingController@show', 'as' => 'showReading']);
Route::get('bookshelf/delete/{id}', ['uses' => 'Book\ReadingController@destroy', 'as' => 'deleteReading'])->where('id', '[0-9]+'); 


