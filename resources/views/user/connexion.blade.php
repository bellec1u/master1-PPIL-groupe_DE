@extends('template')

@section('titre')
    Connexion | Call Me Ishmael
@stop

@section('active3')
    class="active"
@stop

@section('contenu')
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="panel panel-info">
                    <div class="panel-heading">Connectez-vous :</div>
                    <div class="panel-body">
                         @if (Auth::check())
                         <li>  {{ link_to('auth/logout', 'Deconnexion') }}</li>
                             @else                 
                        <li>{{ link_to('auth/login', 'Connexion', ($actif == -1)? array('class' => 'actif'): null) }}</li>
  @endif                
                        <form class="form-horizontal" role="form" method="POST" action="{{ url('/login') }}">
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
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-4">
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" name="remember"> Se souvenir de moi
                                        </label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-md-offset-4">
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-btn fa-sign-in"></i>Connexion
                                    </button>
                                    <a class="btn btn-link" href="{{ url('/password/reset') }}">Mot de passe oublié ?</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
@stop