<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="favicon.ico" />

    <!-- Bootstrap core CSS -->
    <link href="https://getbootstrap.com/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="https://getbootstrap.com/assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles -->
    <link href="style.css" rel="stylesheet">

    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <title>@yield('titre')</title>
</head>

<body>
<header class="page-header">
    <h1 style="text-align:center;"><a href="{{ url('/') }}" title="Retour à l&#039;accueil">Call Me Ishmael</a></h1>
</header>

<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="{{ url('/') }}">Call Me Ishmael</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <li><a href="{{ url('faq') }}">FAQ</a></li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Rechercher un Ebook...">
                </div>
                <button type="submit" class="btn btn-default">Recherche</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="{{ url('connexion') }}">Connectez-vous</a></li>
                <li><a href="{{ url('inscription') }}">Inscrivez-vous</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

@yield('contenu')

<footer class="footer">
    <div class="container">
        <p class="text-muted">Projet PPIL Université de Lorraine - <a href="{{ url('contact') }}" title="Contactez-nous !">Contact</a></p>
    </div>
</footer>

</body>
</html>