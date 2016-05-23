@extends('template')

@section('titre')
    Home
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Bienvenue sur Call Me Ishmael !</h1>
        <div class="panel-body">
            <p><strong>Call Me Ishmael</strong> est un site de référencement d'ebooks (format .epub) disponibles librement sur internet et blablabla etc...
            <br /><br /><br /><br /><br /><br /><br /><br /></p>
            <hr />
            <span style="text-decoration: underline;font-weight: bold;">Top 10</span> :<br />
            <p>
                @foreach($liste as $book)
                <a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img src="{{ $book->cover_url  }}" alt="" /></a>
                @endforeach
            </p>
            <hr />
            <span style="text-decoration: underline;font-weight: bold;">&Agrave; Découvrir</span> :<br />
            <p>Là les derniers ebooks.
            <br /><br /><br /><br /><br /><br /><br /><br /></p>
        </div>
    </article>
@stop