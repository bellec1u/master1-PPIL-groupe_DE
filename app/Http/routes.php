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
Route::resource('user', 'User\UserController',['except' => ['index', 'edit', 'update', 'show', 'destroy']]);

// Book access
// details
Route::get('book/{id}', ['as' => 'bookReturn', 'uses' => 'Book\BookController@show'])->where('id', '[0-9]+');


