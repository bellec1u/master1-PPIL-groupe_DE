<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="favicon.ico" />

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link rel="stylesheet" type="text/css" href="https://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css">

    <!-- Custom styles -->
<<<<<<< HEAD
<<<<<<< HEAD
    {!! Html::asset('style.css') !!}
=======
    {!! Html::style('style.css') !!}
>>>>>>> parent of 9aaebbc... changement de reader plus complet et avec book mark
=======
    {!! Html::style('style.css') !!}
>>>>>>> parent of 9aaebbc... changement de reader plus complet et avec book mark
    <link rel="stylesheet" type="text/css" href="https://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css">

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <title>@yield('titre')</title>
</head>

<body>
<header class="page-header" role="banner">
    <div class="container">
        <h1 style="text-align:center;"><a href="{{ url('/') }}">Call Me Ishmael</a></h1>
    </div>
</header>

<nav class="navbar navbar-default navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="{{ url('/') }}">Call Me Ishmael</a>
        </div>
        <div class="navbar-collapse collapse">
            <form class="nav navbar-nav navbar-form" role="search" method="GET" action="{{ route('bookSearch') }}">
                <div class="input-group">
                    <input type="text" name="query" class="form-control" placeholder="Rechercher un Ebook..." >
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                    </div>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li @yield('active1')><a href="{{ url('faq') }}"><span class="glyphicon glyphicon-list-alt"></span> FAQ</a></li>
                @if(Auth::check())
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span> <b>Utilisateur</b> <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#"><span class="glyphicon glyphicon-cog"></span> Paramètres</a></li>
                            <li><a href="{{URL::route('showReading', array())}}"><span class="glyphicon glyphicon-list-alt"></span> Ouvrir sa bibliothèque</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="{{ url('logout') }}"><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
                        </ul>
                    </li>
                    <li><a href="{{ url('logout') }}"><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
                @else
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <b>Connexion</b> <span class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    Connectez-vous via :
                                    <div class="social-buttons">
                                        <a href="{{ url('redirect/facebook') }}" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                                        <a href="{{ url('redirect/google') }}" class="btn btn-gp"><i class="fa fa-google-plus"></i> Google+</a>
                                    </div>
                                    <hr />
                                    <form class="form" role="form" method="POST" action="{{ url('/login') }}">
                                        {!! csrf_field() !!}
                                        <div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
                                            <label class="sr-only">Adresse mail</label>
                                            <input type="email" class="form-control" name="email" value="{{ old('email') }}" placeholder="Adresse mail..." required>
                                                @if ($errors->has('email'))
                                                    <span class="help-block">
                                                        <strong>{{ $errors->first('email') }}</strong>
                                                    </span>
                                                @endif
                                        </div>
                                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                                            <label class="sr-only">Mot de passe</label>
                                            <input type="password" class="form-control" name="password" placeholder="Mot de passe..." required>
                                            @if ($errors->has('password'))
                                                <span class="help-block">
                                                    <strong>{{ $errors->first('password') }}</strong>
                                                </span>
                                            @endif
                                        </div>
                                        <div class="help-block text-right"><a href="{{ url('/password/reset') }}">Mot de passe oublié ?</a></div>
                                        <div class="form-group">
                                            <div class="checkbox">
                                                <label>
                                                <input type="checkbox" name="remember"> Rester connecté
                                                </label>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary btn-block">
                                                <i class="fa fa-btn fa-sign-in"></i> Connexion
                                            </button>
                                        </div>
                                    </form>
                                </div>
                                <div class="bottom text-center">
                                    Nouveau ? <a href="{{ url('inscription') }}"><b>Inscription</b></a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </li>
                @endif
            </ul>
        </div>
    </div>
</nav>
<main role="main">
    <section>
        <article>
            @yield('contenu')
        </article>
    </section>
</main>

<!--<footer class="navbar navbar-default" role="contentinfo">
    <div class="container">
        <p class="navbar-text">Projet PPIL Université de Lorraine - <a href="{{ url('contact') }}" title="Contactez-nous !">Contact</a>
            <a href="" title="Rejoignez-nous sur Facebook !"><span class="fa-stack fa-lg">
                <i class="fa fa-square-o fa-stack-2x"></i>
                <i class="fa fa-facebook fa-stack-1x"></i>
            </span></a>
            <a href="" title="Rejoignez-nous sur Twitter !"><span class="fa-stack fa-lg">
                <i class="fa fa-square-o fa-stack-2x"></i>
                <i class="fa fa-twitter fa-stack-1x"></i>
            </span></a>
        </p>
    </div>
</footer>-->

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>