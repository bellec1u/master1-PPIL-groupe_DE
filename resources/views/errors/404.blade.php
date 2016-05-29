<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="{{ url('design/favicon.ico') }}" />

    <title>404 | Call Me Ishmael</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">

    {!! Html::style('design/style.css') !!}

    <style>
        .panel {
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <section class="container">
        <div class="panel panel-info">
            <h1 class="panel-heading">404</h1>
            <div class="panel-body">
                <p>
                    Oops, cette page n'existe pas.<br>
                </p>
                <a href="{{ url('/') }}" class="btn btn-primary">Revenir au site</a>
            </div>
        </div>
    </section>

</body>

</html>