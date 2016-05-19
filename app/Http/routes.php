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

    Route::get('/', array('uses'=>'HomeController@index', 'as' =>'/'));

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
    //Juste un teste pour la consultation de la bibliotheque perso
    Route::get('consulter_biblio', function () {
        return view('consulter');
    });



    Route::auth();
    // standard
    Route::resource('user', 'UserController',
        ['except' => ['index', 'edit', 'destroy']]);
//    Route::get('user', 'UserController@create');
//    Route::post('user', ['uses' => 'UserController@store', 'as' => 'storeUser']);
    
    // facebook and google+ users connection
    Route::get('/redirect/{provider}', 'SocialAuthController@redirect');
    Route::get('/callback/{provider}', 'SocialAuthController@callback');
    
    
    // Book access
    // details
    Route::get('book/{id}', ['as' => 'bookReturn', 'uses' => 'Book\BookController@show'])->where('id', '[0-9]+');
    Route::get('book/{id}/open/', ['as' => 'bookOpen', 'uses' => 'Book\BookController@open'])->where('id', '[0-9]+');

    // Book modif
    Route::get('createRating/{id}', 'Book\RatingController@create')->where('id', '[0-9]+');
    Route::post('storeRating', ['uses' => 'Book\RatingController@store', 'as' => 'storeRating']);
    Route::get('destroyRating/{id}/{idbook}', 'Book\RatingController@destroy')->where('id', '[0-9]+')->where('idbook', '[0-9]+');
    Route::get('editRating/{id}', 'Book\RatingController@edit')->where('id', '[0-9]+');
    Route::post('updateRating', ['uses' => 'Book\RatingController@update', 'as' => 'updateRating']);


    // liste de lecture.
    Route::get('bookshelf/add/{id}', 'Book\ReadingController@add')->where('id', '[0-9]+');
    Route::get('bookshelf', ['uses' => 'Book\ReadingController@show', 'as' => 'showReading']);
    Route::get('bookshelf/delete/{id}', ['uses' => 'Book\ReadingController@destroy', 'as' => 'deleteReading'])->where('id', '[0-9]+');;

   
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
