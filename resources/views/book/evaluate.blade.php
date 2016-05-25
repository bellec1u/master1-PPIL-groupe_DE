@extends('template')

@section('titre')
    &Eacute;valuation
@stop

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
    <article class="panel panel-info">
        <h1 class="panel-heading">&Eacute;valuer le livre </h1>
        <div class="panel-body">
                {!! Form::open(array('route'=>'storeRating','method'=>'POST', 'files'=>'true')) !!}
                {!! csrf_field() !!}
                <div class="form-group {!! $errors->has('stars') ? 'has-error' : '' !!}">
                    <label class="col-sm-2">Votre note :</label>
                    <div class="col-sm-12">
                        <div id='A1'><script type='text/javascript'>CreateListeEtoile('A1',5);</script></div>
                        {!! Form::hidden('stars', null,['id'=>'etoile'] ) !!}
                    </div>
                </div>
                <div class="col-xs-12 form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
                    <label>Votre commentaire :</label>
                        {!! Form::textarea ('comment', null, ['class' => 'form-control', 'placeholder' => 'Votre commentaire ici...']) !!}
                        {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
                </div>
                <div class="col-xs-12">
                    {!! Form::submit('&Eacute;valuer', ['class' => 'btn btn-primary pull-right']) !!}
                </div>
                {!! Form::close() !!}
        </div>
    </article>
@stop