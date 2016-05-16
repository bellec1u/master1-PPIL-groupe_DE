<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="favicon.ico" />

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link rel="stylesheet" type="text/css" href="https://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css">

    <!-- Custom styles -->
    <link rel="stylesheet" type="text/css" href="style.css">
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
            <form class="nav navbar-nav navbar-form" role="search">
                <div class="input-group">
                    <input type="text" name="search" class="form-control" placeholder="Rechercher un Ebook..." >
                    <div class="input-group-btn">
                        <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                    </div>
                </div>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li @yield('active1')><a href="{{ url('faq') }}"><span class="glyphicon glyphicon-list-alt"></span> FAQ</a></li>
                <li @yield('active2')><a href="{{ url('inscription') }}"><span class="glyphicon glyphicon-pencil"></span> Inscription</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <b>Connexion</b> <span class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    Connectez-vous via :
                                    <div class="social-buttons">
                                        <a href="#" class="btn btn-fb"><i class="fa fa-facebook"></i> Facebook</a>
                                        <a href="#" class="btn btn-gp"><i class="fa fa-google-plus"></i> Google+</a>
                                    </div>
                                    <hr />
                                    <form class="form" role="form" method="post" action="login" accept-charset="UTF-8" id="login-nav">
                                        <div class="form-group">
                                            <label class="sr-only" for="exampleInputEmail2">Email address</label>
                                            <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Adresse mail..." required>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="passe">Password</label>
                                            <input type="password" class="form-control" id="passe" placeholder="Mot de passe..." required>
                                            <div class="help-block text-right"><a href="">Mot de passe oublié ?</a></div>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary btn-block">Connexion</button>
                                        </div>
                                        <div class="checkbox">
                                            <label>
                                                <input type="checkbox"> Rester connecté
                                            </label>
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

<footer class="navbar navbar-default" role="contentinfo">
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
</footer>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>