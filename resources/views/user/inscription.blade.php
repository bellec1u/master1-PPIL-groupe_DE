@extends('template')

@section('titre')
    Inscription | Call Me Ishmael
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Formulaire d'Inscription</h1>
        <div class="panel-body">
            <div class="form-horizontal">
                {!! Form::open(array('route'=>'user.store', 'method'=>'POST', 'files'=>'true')) !!}
                <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Adresse mail :</label>
                    <div class="col-lg-7">
                        {!! Form::email('email', null, ['class' => 'form-control', 'placeholder' => 'Votre email...']) !!}
                        {!! $errors->first('email', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Confirmation de l'Adresse mail :</label>
                    <div class="col-lg-7">
                        {!! Form::email('email_confirmation', null, ['class' => 'form-control', 'placeholder' => 'Confirmation de votre email...']) !!}
                        {!! $errors->first('email', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Prénom :</label>
                    <div class="col-lg-7">
                        {!! Form::text('first_name', null, ['class' => 'form-control', 'placeholder' => 'Votre prénom...']) !!}
                        {!! $errors->first('name', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Nom :</label>
                    <div class="col-lg-7">
                        {!! Form::text('last_name', null, ['class' => 'form-control', 'placeholder' => 'Votre nom...']) !!}
                        {!! $errors->first('name', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group {!! $errors->has('password') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Mot de passe :</label>
                    <div class="col-lg-7">
                        {!! Form::password('password', ['class' => 'form-control', 'placeholder' => '8 caractères minmum...']) !!}
                        {!! $errors->first('password', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">Confirmation du Mot de passe :</label>
                    <div class="col-lg-7">
                        {!! Form::password('password_confirmation', ['class' => 'form-control', 'placeholder' => 'Répéter le mot de passe...']) !!}
                    </div>
                </div>
                <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Genre :</label>
                    <div class="col-lg-7">
                        <label>{!! Form::radio('sex', 'F') !!} Femme</label>
                        <label>{!! Form::radio('sex', 'M', true) !!} Homme</label>
                        {!! $errors->first('sex', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group{!! $errors->has('name') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Date de naissance :</label>
                    <div class="col-lg-7">
                        {!! Form::date('birth_date', \Carbon\Carbon::now()) !!}
                        {!! $errors->first('birth_date', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="form-group{!! $errors->has('name') ? 'has-error' : '' !!}">
                    <label class="col-lg-3 control-label">Image de profil :</label>
                    <div class="col-lg-7">
                        {!! Form::file('profile_image') !!}
                        {!! $errors->first('profile_image', '<small class="help-block">:message</small>') !!}
                    </div>
                </div>
                <div class="col-lg-7 col-lg-offset-3">
                        {!! Form::submit('Inscription', ['class' => 'btn btn-primary pull-right']) !!}
                        <!--<a href="javascript:history.back()" class="btn btn-primary pull-left">
                            <span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
                        </a>-->
                </div>
                {!! Form::close() !!}
            </div>
        </div>
    </article>
@stop