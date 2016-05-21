@extends('template')

@section('active2')
    class="active"
@stop

@section('titre')
    Contact | Call Me Ishmael
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Contacter un Administrateur</h1>
        <div class="panel-body">
            <p style="color:red;">Ne marche pas encore !</p>
            <p>Un problème ? Une idée ? Une remarque ?<br />
            Alors contactez-nous via le formulaire ci-dessous ! Veuillez faire bien attention à renseigner une adresse mail valide, ce sans quoi, nous serons incapable de vous répondre.</p>

            {!! Form::open(['url' => 'contact']) !!}
            <div class="form-group {!! $errors->has('nom') ? 'has-error' : '' !!}">
                {!! Form::text('nom', null, ['class' => 'form-control', 'placeholder' => 'Votre nom']) !!}
                {!! $errors->first('nom', '<small class="help-block">:message</small>') !!}
            </div>
            <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                {!! Form::email('email', null, ['class' => 'form-control', 'placeholder' => 'Votre email']) !!}
                {!! $errors->first('email', '<small class="help-block">:message</small>') !!}
            </div>
            <div class="form-group {!! $errors->has('texte') ? 'has-error' : '' !!}">
                {!! Form::textarea ('texte', null, ['class' => 'form-control', 'placeholder' => 'Votre message']) !!}
                {!! $errors->first('texte', '<small class="help-block">:message</small>') !!}
            </div>
            {!! Form::submit('Envoyer !', ['class' => 'btn btn-info pull-right']) !!}
            {!! Form::close() !!}
        </div>
    </article>
@stop