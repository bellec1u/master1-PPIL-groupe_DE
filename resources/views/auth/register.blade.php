@extends('layouts.app')

@section('titre')
    Inscription | Call Me Ishmael
@stop

@section('css')
    {!! Html::style('design/css/login.css') !!}
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
                                {!! Form::open(array('route'=>'user.store', 'method'=>'POST', 'files'=>'true')) !!}
                                    <div class="clear-form two-col">
                                        <div class="form-heading">
                                            <h3 class="header">S'incrire</h3>
                                            <hr>
                                            {{ csrf_field() }} 
                                        </div>
                                        <div class="col1">                            
                                            <h4>Utiliser un autre compte</h4>
                                            <p>Vous pouvez également vous inscrire avec un compte Facebook ou un compte Google</p>
                                            <a href="{{ url('redirect/facebook') }}" class="btn btn-large btn-block btn-fb">Facebook</a>
                                            <a href="{{ url('redirect/google') }}" class="btn btn-large btn-block btn-google">Google</a>
                                        </div>
                                        <div class="col2"> 
                                            <div class="form-body">                              
                                                <div class="pair-group">
                                                    <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
                                                        <input type="email" class="form-control" name="email" placeholder="Adresse email" value="{{ old('email') }}" required>
                                                        @if ($errors->has('email'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('email') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div>
                                                    <div class="form-group">
                                                        <input class="form-control" name="email_confirmation" placeholder="Confirmation email" type="text" required>
                                                        @if ($errors->has('email_confirmation'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('email_confirmation') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div>
                                                    
                                                    <div class="form-group {!! $errors->has('first_name') ? 'has-error' : '' !!}">
                                                        <input type="text" class="form-control" name="first_name" placeholder="Prenom" value="{{ old('first_name') }}" required>
                                                        @if ($errors->has('first_name'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('first_name') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div>
                                                    <div class="form-group {!! $errors->has('last_name') ? 'has-error' : '' !!}">       
                                                        <input type="text" class="form-control" name="last_name" placeholder="Nom" value="{{ old('last_name') }}" required>
                                                        @if ($errors->has('last_name'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('last_name') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div> 

                                                    <div class="form-group {!! $errors->has('password') ? 'has-error' : '' !!}">
                                                        <input type="password" class="form-control" name="password"  placeholder="Mot de passe" required>
                                                        @if ($errors->has('password'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('password') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div>
                                                    <div class="form-group {!! $errors->has('password_confirmation') ? 'has-error' : '' !!}">        
                                                        <input type="password" class="form-control" name="password_confirmation" placeholder="Confirmation mot de passe" required>
                                                        @if ($errors->has('password_confirmation'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('password_confirmation') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div> 
                                                    <div class="form-group">
                                                        <div class="radio radio-primary radio-inline">
                                                            <input type="radio" id="inlineRadio1" value="H" name="sex" checked="">
                                                            <label for="inlineRadio1"> Homme </label>
                                                        </div>
                                                        <div class="radio radio-danger radio-inline">
                                                            <input type="radio" id="inlineRadio2" value="F" name="sex">
                                                            <label for="inlineRadio2"> Femme </label>
                                                        </div>
                                                    </div>
                                                    <div class="form-group">
                                                        <div id="birthdayPicker"></div>
                                                        @if ($errors->has('birth_date'))
                                                            <span class="help-block">
                                                                <strong>{{ $errors->first('birth_date') }}</strong>
                                                            </span>
                                                        @endif
                                                    </div>
                                                    <!--{!! Form::file('profile_image') !!}-->
                                                    <div class="input-group input-file">
                                                        <input type="text" class="form-control" placeholder='Choisir une photo de profile...' />
                                                        <span class="input-group-btn ">
                                                            <button class="btn btn-primary btn-choose" type="button">
                                                                <span class="glyphicon glyphicon-picture"></span>
                                                            </button>
                                                        </span>
                                                    </div>
                                                    <div class="form-group checkboxCond {!! $errors->has('condition') ? 'has-error' : '' !!}">
                                                        <label class="col-sm-10 control-label ">Conditions Générales d'Utilisation</label>
                                                        <div class="col-sm-2 checkboxCond2">
                                                            {!! Form::checkbox('condition') !!}
                                                        </div>
                                                        @if ($errors->has('condition'))
                                                            <span class="help-block col-sm-12">
                                                                <small>Vous devez accepter les conditions d'utilisation</small>
                                                            </span>
                                                        @endif
                                                    </div>                        
                                                </div>
                                            </div>                                 
                                            <div class="form-footer">     
                                                <button class="btn btn-large btn-blue btn-success" type="submit">S'inscrire</button> 
                                            </div>    
                                        </div>          
                                    </div>
                                {!! Form::close() !!}
                            </div>
                        </div>          
                    </div>
                </div>
            </div>
        </div>
    </div><! --/headerwrap -->


    <div class="footerNewLogin navbar-fixed-bottom">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    Déja un compte ? <a href="{{ url('login') }}" class="btn btn-primary">Se connecter</a>
                </div>
            </div><! --/row -->
        </div><! --/container -->
    </div><! --/footerwrap -->
     
    <!-- Bootstrap core JavaScript
    ================================================== -->
    @section('javascript')
        
        {!! HTML::script('design/js/jquery-birthday-picker.js'); !!}
        <script type="text/javascript">
            $(document).ready(function() {
                $('.carousel').carousel({interval: 5000});
                $("#birthdayPicker").birthdayPicker();
            });

            function bs_input_file() {
                $(".input-file").before(
                    function() {
                        if ( ! $(this).prev().hasClass('input-ghost') ) {
                            var element = $("<input type='file' class='input-ghost' name='profile_image' style='visibility:hidden; height:0'>");
                            element.attr("name",$(this).attr("name"));
                            element.change(function(){
                                element.next(element).find('input').val((element.val()));
                            });
                            $(this).find("button.btn-choose").click(function(){
                                element.click();
                            });
                            $(this).find("button.btn-reset").click(function(){
                                element.val(null);
                                $(this).parents(".input-file").find('input').val('');
                            });
                            $(this).find('input').css("cursor","pointer");
                            $(this).find('input').mousedown(function() {
                                $(this).parents('.input-file').prev().click();
                                return false;
                            });
                            return element;
                        }
                    }
                );
            }
            $(function() {
                bs_input_file();
            });
        </script>
    @stop


@stop

