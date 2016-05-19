@extends('template')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
    <div class="container">
<<<<<<< HEAD
        <article class="panel panel-info">
=======

        <div class="panel panel-info">
>>>>>>> ee024eef68bf134c47a93f53d0545cb7756e4df3
            <div class="panel-heading">Bienvenue sur Call Me Ishmael !</div>
            <div class="panel-body">
                <strong>Call Me Ishmael</strong> est un site de référencement d'ebooks (format .epub) disponibles librement sur internet et blablabla etc...
                <br /><br /><br /><br /><br /><br /><br /><br />
                <hr />

                <span style="text-decoration: underline;font-weight: bold;">Top 10</span> :<br />
                @foreach($liste as $book)
                <p><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img src="{{ $book->cover_url  }}" alt="" /></a></p>

                @endforeach
                <br /><br /><br /><br /><br /><br /><br /><br />
                <hr />
                <span style="text-decoration: underline;font-weight: bold;">&Agrave; Découvrir</span> :<br />
                Là les derniers ebooks.
                <br /><br /><br /><br /><br /><br /><br /><br />
            </div>
        </article>
    </div>
@stop