@extends('layouts.app')

@section('titre')
    Connection | Call Me Ishmael
@stop

@section('css')
    {!! Html::style('design/css/login.css') !!}
@stop

@section('active3')
    class="active"
@stop

@section('content')
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
        <div class="container navbar-left">
            <div class="navbar-header">
                <a class="navbar-brand" href="{{ url('index') }}">CALLMEISMAEL</a>
            </div>
        </div>
    </div>

    <!-- *****************************************************************************************************************
     HEADER
     ***************************************************************************************************************** -->

    <div id="headerLogin">
        <div class="inner-bg">
            <div class="container">
                <div class="row">
                    <div id="myCarouselLogin col-sm-12" class="carousel container slide">
                        <div class="carousel-inner">
                            <div class="active item one"></div>
                            <div class="item two"></div>
                            <div class="item three"></div>
                            <div class="item four"></div>
                            <div class="item five"></div>
                            <div class="item six"></div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12  text">
                            <div class="span12">
                                <form class="form-signin" id="log" role="form" method="POST" action="{{ url('login') }}">
                                    <div class="clear-form two-col">
                                        <div class="form-heading">
                                            <h3 class="header">Se connecter</h3>
                                            <hr>
                                            {!! csrf_field() !!}
                                        </div>
                                        <div class="col1">                            
                                            <h4>Utiliser un autre compte</h4>
                                            <p>Vous pouvez également vous connecter avec un compte Facebook ou un compte Google</p>
                                            <a href="{{ url('redirect/facebook') }}" class="btn btn-large btn-block btn-fb">Facebook</a>
                                            <a href="{{ url('redirect/google') }}" class="btn btn-large btn-block btn-google">Google</a>
                                        </div>
                                        <div class="col2">
                                            <div class="form-heading">
                                                <h4 class="header">Information de votre compte</h4>                                                        
                                            </div>  
                                            <div class="form-body">                              
                                                <div class="pair-group">
                                                    <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                                                        <input class="form-control" name="email" placeholder="Adresse email" type="text" value="{{ old('email') }}"  required>
                                                        <small class="help-block"></small>
                                                    </div>
                                                    <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                                                        <input class="form-control" name="password" placeholder="Mot de passe" type="password" required>     
                                                        <small class="help-block"></small>   
                                                    </div>

                                                </div>
                                                <div class="checkbox">
                                                    <label>
                                                        <input type="checkbox" name="remember"> Se souvenir de moi
                                                    </label>
                                                </div>
                                           </div>                                 
                                            <div class="form-footer">     
                                                <button class="btn btn-large btn-blue btn-success" type="submit">
                                                    Se connecter
                                                </button> 
                                                <p class="center">
                                                    <a href="{{ url('/password/reset') }}">Mot de passe oublié ?</a>
                                                </p>
                                            </div>    
                                        </div>          
                                    </div>
                                </form>
                            </div>
                        </div>          
                    </div>
                </div>
            </div>
        </div>
    </div><! --/headerwrap -->

    @section('javascript')
        <div class="footerNewLogin navbar-fixed-bottom">
            <div class="container">
                <div class="row">
                    <div class="col-lg-12">
                        Toujours pas de compte ? <a href="{{ url('register') }}" class="btn btn-primary">S'inscrire</a>
                    </div>
                </div><! --/row -->
            </div><! --/container -->
        </div><! --/footerwrap -->
    @stop

@stop
