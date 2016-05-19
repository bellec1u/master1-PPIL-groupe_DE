@extends('template')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
    <div class="container">
        <div class="panel panel-info">
            <div class="panel-heading">La liste de ma lecture en cours </div>
            <div class="panel-body">
                @if(count($data) > 1 )
                    @foreach($data as $book)
                        @if($book != '')
                            <p><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img src="{{ $book->cover_url  }}" alt="" /></a></p>
                            <p><b>Titre :</b>  {{ $book->title }}</p>
                            <p><b>Genre :</b>  {{ $book->genre }}</p>
                            <p><b>Auteur :</b> {{ $book->author }}</p>
                            <p><b>Langue :</b> {{ $book->language }}</p>
                            <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
                            <p><b>Note moyenne : </b> {{ $book->stars_average  }}</p>
                            <a href="{{URL::route('deleteReading', array('id'=> $book->id))}}" class="btn bg-primary">supprimer</a>
                            <hr>
                        @endif <br />
                    @endforeach
                @endif
                <br /><br /><br /><br /><br /><br /><br /><br />
            </div>
        </div>
    </div>
@stop