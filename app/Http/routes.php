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
    return view('index');
});

Route::get('index', function () {
    return view('index');
});

Route::get('faq', function () {
    return view('faq');
});

Route::get('inscription', function () {
    return view('inscription');
});

Route::get('connexion', function () {
    return view('connexion');
});

Route::get('contact', function () {
    return view('contact');
});

Route::get('user_home', function () {
    return view('user_home');
});

Route::get('create', function () {
    return view('create');
});

Route::get('home', function () {
    return view('home');
});

Route::get('email_contact', function () {
    return view('email_contact');
});

Route::get('photo', function () {
    return view('photo');
});

Route::get('home', array('as' => 'home', 'uses' => function(){
  return view('home');
}));


// création d'utilisateur standard 
Route::get('user', 'UserController@create');
Route::post('user', ['uses' => 'UserController@store', 'as' => 'storeUser']);


// création dutilisateur facebook et google +

Route::get('/Redirect/{provider}', 'SocialAuthController@Redirect');
Route::get('/Callback/{provider}', 'SocialAuthController@Callback');


// accès livre 

        // détails
        Route::get('/book/show/{id}', 'BookController@show');


Route::auth();

Route::get('/home', 'HomeController@index');
