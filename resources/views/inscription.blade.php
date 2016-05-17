@extends('template')

@section('titre')
    Inscription | Call Me Ishmael
@stop

@section('active2')
    class="active"
@stop

@section('contenu')
    <div class="container">
        <div class="row">
                <div class="panel panel-info">
                    <div class="panel-heading">Inscrivez-vous :</div>
                    <div class="panel-body">
                        {!! Form::open(array('route'=>'storeUser','method'=>'POST', 'files'=>'true')) !!}
                        {!! csrf_field() !!}
                        <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                            {!! Form::email('email', null, ['class' => 'form-control', 'placeholder' => 'Email...']) !!}
                            {!! $errors->first('email', '<small class="help-block">:message</small>') !!}
                        </div>
                        <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                            {!! Form::email('email_confirmation', null, ['class' => 'form-control', 'placeholder' => 'Confirmation du mail...']) !!}
                            {!! $errors->first('email', '<small class="help-block">:message</small>') !!}
                        </div>
                        <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                            {!! Form::text('first_name', null, ['class' => 'form-control', 'placeholder' => 'Prénom...']) !!}
                            {!! $errors->first('name', '<small class="help-block">:message</small>') !!}
                        </div>

                        <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                            {!! Form::text('last_name', null, ['class' => 'form-control', 'placeholder' => 'Nom...']) !!}
                            {!! $errors->first('name', '<small class="help-block">:message</small>') !!}
                        </div>
                        <div class="form-group {!! $errors->has('password') ? 'has-error' : '' !!}">
                            {!! Form::password('password', ['class' => 'form-control', 'placeholder' => 'Mot de passe...']) !!}
                            {!! $errors->first('password', '<small class="help-block">:message</small>') !!}
                        </div>
                        <div class="form-group">
                            {!! Form::password('password_confirmation', ['class' => 'form-control', 'placeholder' => 'Confirmation du mot de passe...']) !!}
                        </div>
                        <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
                            {!! Form::radio('sex', 'F') !!} Femme
                            {!! Form::radio('sex', 'M') !!} Homme
                            {!! $errors->first('name', '<small class="help-block">:message</small>') !!}
                        </div>
                        <div class="form-group">
                            <div class="date">
                                <label>
                                    Date de naissance : {!! Form::date('birth_date',  \Carbon\Carbon::now()) !!}
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="file">
                                <label>
                                    Image de Profil : {!! Form::file('profile_image') !!}
                                </label>
                            </div>
                        </div>
                        

                        {!! Form::submit('Inscription', ['class' => 'btn btn-primary pull-right']) !!}
                        <a href="javascript:history.back()" class="btn btn-primary pull-left">
                            <span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
                        </a>
                        {!! Form::close() !!}


                        <!--<form class="form-horizontal" role="form" method="POST" action="{{ url('/login') }}">
                            {!! csrf_field() !!}
                            <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                                <label class="col-md-4 control-label">Pseudo</label>
                                <div class="col-md-6">
                                    <input type="text" class="form-control" name="pseudo" value="{{ old('pseudo') }}">
                                    @if ($errors->has('pseudo'))
                                        <span class="help-block">
                                        <strong>{{ $errors->first('pseudo') }}</strong>
                                    </span>
                                    @endif
                                </div>
                            </div>
                            <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                                <label class="col-md-4 control-label">Adresse mail</label>
                                <div class="col-md-6">
                                    <input type="email" class="form-control" name="email" value="{{ old('email') }}">
                                    @if ($errors->has('email'))
                                        <span class="help-block">
                                        <strong>{{ $errors->first('email') }}</strong>
                                    </span>
                                    @endif
                                </div>
                            </div>
                            <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                                <label class="col-md-4 control-label">Mot de passe</label>
                                <div class="col-md-6">
                                    <input type="password" class="form-control" name="password">
                                    @if ($errors->has('password'))
                                        <span class="help-block">
                                        <strong>{{ $errors->first('password') }}</strong>
                                    </span>
                                    @endif
                                </div>
                            </div>
                            <div class="form-group{{ $errors->has('password2') ? ' has-error' : '' }}">
                                <label class="col-md-4 control-label">Confirmer mot de passe</label>
                                <div class="col-md-6">
                                    <input type="password" class="form-control" name="password2">
                                    @if ($errors->has('password2'))
                                        <span class="help-block">
                                        <strong>{{ $errors->first('password2') }}</strong>
                                    </span>
                                    @endif
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-4">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-btn fa-sign-in"></i> Inscription
                                    </button>
                                </div>
                            </div>
                        </form> -->
                    </div>
                </div>
        </div>
    </div>
@stop