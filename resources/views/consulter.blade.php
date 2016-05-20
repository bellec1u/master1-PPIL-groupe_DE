@extends('template')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Liste de Lectures</h1>
        <div class="panel-body">
            @if(count($data) > 1 )
                @foreach($data as $book)
                    @if($book != '')
                        {{$book->title }} 		  <a href="{{URL::route('deleteReading', array('id'=> $book->id))}}" class="btn bg-primary">Supprimer</a>
                    @endif
                @endforeach
            @endif
        </div>
    </article>
@stop