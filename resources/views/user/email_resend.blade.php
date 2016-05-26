@extends('template')

@section('titre')
    Validation email
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Validez votre email</h1>
        <div class="panel-body">
            <div class="col-sm-3 text-center">
                <img style="width:200px" src="{{ url('design/envelope.png') }}">
            </div>
            <div class="col-sm-6">
                <p>Veuillez valider votre mail avant de vous connecter. Vérifiez votre boîte mail.</p>
                <a href="{{ route('resendEmail', $user->id) }}" class="btn btn-primary">
                    <span class="glyphicon glyphicon-send"></span> Renvoyer le mail de validation
                </a>
            </div>
        </div>
    </article>
@stop