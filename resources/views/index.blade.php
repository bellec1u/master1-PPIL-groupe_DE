@extends('user.template')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
    <div class="container">
        <div class="panel panel-info">
            <div class="panel-heading">Bienvenue sur Call Me Ishmael !</div>
            <div class="panel-body">
                <strong>Call Me Ishmael</strong> est un site de référencement d'ebooks (format .epub) disponibles librement sur internet et blablabla etc...
                <br /><br /><br /><br /><br /><br /><br /><br />
                <hr />
                {!! link_to('book/consultBibliotheque', 'voir sa bibliothèque', $attribute = array(), $secrure = null ) !!}
                <hr />
                <span style="text-decoration: underline;font-weight: bold;">Top 10</span> :<br />
                <p><a href="{{ url('book/4') }}"><img src="{{ $book->cover_url  }}" alt="" /></a></p>
                <br /><br /><br /><br /><br /><br /><br /><br />
                <hr />
                <span style="text-decoration: underline;font-weight: bold;">&Agrave; Découvrir</span> :<br />
                Là les derniers ebooks.
                <br /><br /><br /><br /><br /><br /><br /><br />
            </div>
        </div>
    </div>
@stop