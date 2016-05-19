<?php

namespace App\Http\Controllers\Book;


use App\Http\Requests\book\ReadingCreateRequest;
use App\Repositories\Book\ReadingRepository;
use App\Http\Requests;
use App\Repositories\Book\BookRepository;
use App\Http\Controllers\Controller;
use Auth;
use App\Models\User;

class ReadingController extends Controller
{
	protected $bookRepository;
    protected $readingRepository;
    
    public function __construct(BookRepository $bookRepository, ReadingRepository $readingRepository)
    {
        $this->bookRepository = $bookRepository;
        $this->readingRepository = $readingRepository;
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
    {   $book = $this->readingRepository->getReadingIdAndUser($id_book, Auth::user()->id);
        if($book->count() ==0 ){
            $request['user_id'] = Auth::user()->id;
            $request['book_id'] = $id_book;
            $request['current_page'] = '0';
            $this->readingRepository->store($request);
        }

        return redirect()->route('bookReturn', ['id' => $id_book]);
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
        $details[]= '';
        
        foreach($book as $bo){
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
        return redirect()->route('showReading');

    }
}