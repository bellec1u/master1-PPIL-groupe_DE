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

Route::get('/', function () {
    return view('welcome');
});


Route::get('home', array('as' => 'home', 'uses' => function(){
  return view('home');
}));
// création d'utilisateur standard 
Route::get('user', 'UserController@create');
Route::post('user', ['uses' => 'UserController@store', 'as' => 'storeUser']);


// création dutilisateur facebook 

Route::get('/redirect', 'SocialAuthController@redirect');
Route::get('/callback', 'SocialAuthController@callback');;


Route::auth();

Route::get('/home', 'HomeController@index');
