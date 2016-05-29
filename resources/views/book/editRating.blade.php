@extends('template')

@section('head')
    {!! Html::script('etoile/ListeEtoile.js') !!}
    <style type="text/css">
        .listeEtoile ul {
            margin: 0;
            padding: 0;
            list-style-type: none;
        }
        .listeEtoile ul li {
            display:inline-block;
            width: 16px;
            text-align: left;
            list-style-type: none;
        }
        .listeEtoile ul li img {
            border: 0;
            margin: 0;
            padding: 0;
        }

    </style>
@stop
@section('contenu')
    <SCRIPT language="Javascript">
        function supprimer() {
            var r = confirm("voulez-vous vraiment suprimer  ce comentaire");
            if (r == true) {
                window.location = "{{URL::to('destroyRating/'.$ratings->id.'/'.$ratings->book_id)}}";
            }
        }

        </script>
    <br>

        <div class="panel panel-info">

            <h1 class="panel-heading">Modification de votre commentaire </h1>
            <div class="panel-body">



                {!! Form::open(array('route'=>'updateRating','method'=>'POST', 'files'=>'true')) !!}
                {!! csrf_field() !!}
                <div class="form-group {!! $errors->has('stars') ? 'has-error' : '' !!}">
                    <label class="col-sm-2 col-xs-4">Votre note :</label>
                    <div class="col-sm-10 col-xs-8">

                    <div id='A1'><script type='text/javascript'>CreateListeEtoile('A1',5);</script></div>

                    {!! Form::hidden('stars', $ratings->stars,['id'=>'etoile'] ) !!}
                    </div>
                </div>




                {{ Form::hidden('id', $ratings->id) }}
                {{ Form::hidden('book_id', $ratings->book_id) }}
                <div class="col-lg-10 col-xs-12 form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
                <label>Votre commentaire :</label>
                    {!! Form::textarea ('comment', $ratings->comment, ['class' => 'form-control']) !!}
                    {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
                </div>
                <div class="col-lg-10 col-xs-12">
                    <A href="javascript:;" onClick="supprimer();" class="btn btn-danger pull-right">Supprimer</A>
                {!! Form::submit('Envoyer !', ['class' => 'btn btn-info pull-right']) !!}
                {!! Form::close() !!}
                </div>

        </div>
    </div>

@stop