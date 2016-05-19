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
                    {{$book->title }} 		  <a href="{{URL::route('deleteReading', array('id'=> $book->id))}}" class="btn bg-primary">supprimer</a>
                @endif

                    @endforeach
                @endif
                <br /><br /><br /><br /><br /><br /><br /><br />

            </div>
        </div>
    </div>
@stop