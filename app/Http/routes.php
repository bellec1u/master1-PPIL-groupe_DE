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
/*
 * Web Routes
 */
Route::group(['middleware' => 'web'], function () {

    Route::get('/home', 'HomeController@index');
    Route::get('faq', function () {
        return view('faq');
    });

    /*Route::get('/', function () {
        return view('index');
    });*/

    Route::get('/', 'HomeController@index');

    Route::get('inscription', function () {
        return view('user/inscription');
    });
    Route::get('connexion', function () {
        return view('user/connexion');
    });
    Route::get('contact', function () {
        return view('user/contact');
    });
    Route::get('create', function () {
        return view('user/create');
    });
    Route::get('home', function () {
        return view('home');
    });
    Route::get('email_contact', function () {
        return view('user/email_contact');
    });
   



    Route::auth();
    // standard
    Route::get('user', 'UserController@create');
    Route::post('user', ['uses' => 'UserController@store', 'as' => 'storeUser']);
    // facebook and google+ users connection
    Route::get('/redirect/{provider}', 'SocialAuthController@redirect');
    Route::get('/callback/{provider}', 'SocialAuthController@callback');
    // book access
    // details
    Route::get('book/{id}', 'BookController@show')->where('id', '[0-9]+');
    Route::get('book/{id}/open', 'BookController@open')->where('id', '[0-9]+');

   
});


/*
 * API Routes
 */
Route::group(['prefix' => 'api', 'middleware' => 'api'], function () {
    Route::post('user/register', 'Api\JWTAuthController@store');
    Route::post('user/login', 'Api\JWTAuthController@login');
    Route::get('user/logout', ['middleware' => 'jwt.auth',
        'uses' => 'Api\JWTAuthController@logout']);
});
