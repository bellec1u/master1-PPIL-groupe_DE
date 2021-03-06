<?php

namespace App\Http\Controllers\Book;


use App\Http\Requests\book\ReadingCreateRequest;
use App\Repositories\Book\ReadingRepository;
use App\Http\Requests;
use App\Repositories\Book\BookRepository;
use App\Http\Controllers\Controller;
use App\Http\Controllers\User\NotificationController;
use  App\Repositories\User\NotificationRepository;
use Auth;
use App\Models\User;

class ReadingController extends Controller
{
    protected $bookRepository;
    protected $readingRepository;
    protected $notification;

    public function __construct(
        BookRepository $bookRepository,
        ReadingRepository $readingRepository,
        NotificationController $notificationController
    ) {
        $this->bookRepository = $bookRepository;
        $this->readingRepository = $readingRepository;
        $this->notification = $notificationController;

        $this->middleware('auth');
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function add($id_book)
    {

        $book = $this->readingRepository->getReadingIdAndUser($id_book,
            Auth::user()->id);
        if ($book->count() == 0) {
            $request['user_id'] = Auth::user()->id;
            $request['book_id'] = $id_book;
            $request['current_page'] = '0';
            $reading = $this->readingRepository->store($request);

            if (Auth::user()->following_allowed) {
                $notif['book_id'] = $id_book;
                $notif['type'] = "Ajout liste de lecture";
                $notif['details'] = $reading->user->first_name . " a ajouté " . $reading->book->title . " dans sa liste de lecture";
                $this->notification->store($notif);
            }

        }
        

        return redirect()->back();
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {

    }

    /**
     * Display the specified resource.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */

    public function show()
    {
        $book = $this->readingRepository->getReadingId(Auth::user()->id);

        $details[] = '';

        foreach ($book as $bo) {
            $details[] = $this->bookRepository->getById($bo->book_id);
        }
        return view('consulter')->with('data', $details);
    }

    /**
     * ouverture d'un livre
     * @return \Illuminate\Contracts\View\Factory|\Illuminate\View\View
     */
    public function open($id)
    {
        //return view('Book/basic');
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function edit($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request $request
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->readingRepository->delete($id, Auth::user()->id);
        return redirect()->back();
    }
}