@extends('user.template')

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
                    {{$book->title }} 		{!! link_to('book/destroyBibliotheque/'.$book->id, 'supprimer de la bibliothèque', $attribute = array(), $secrure = null ) !!}
                @endif

                    @endforeach
                @endif
                <br /><br /><br /><br /><br /><br /><br /><br />

            </div>
        </div>
    </div>
@stop