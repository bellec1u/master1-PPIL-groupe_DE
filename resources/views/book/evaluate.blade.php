@extends('template')

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
@section('contenu')



@section('contenu')
    <br>
    <div class="col-sm-offset-3 col-sm-6">
        <div class="panel panel-info">
            <div class="panel-heading">Ajout d'un commentaire </div>
            <div class="panel-body">
                {!! Form::open(array('route'=>'storeRating','method'=>'POST', 'files'=>'true')) !!}
                {!! csrf_field() !!}
                <div class="form-group {!! $errors->has('titre') ? 'has-error' : '' !!}">
                    <div id='A1'><script type='text/javascript'>CreateListeEtoile('A1',5);</script>

                    {!! Form::number('stars', null,['class' => 'form-control', 'placeholder' => '0', 'max'=>'5', 'min'=>'0'] ) !!}
                    {!! $errors->first('stars', '<small class="help-block">:message</small>') !!}
                </div>

                        {{ Form::hidden('book_id', $book) }}
                <div class="form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
                    {!! Form::textarea ('comment', null, ['class' => 'form-control', 'placeholder' => 'Votre commentaire ici']) !!}
                    {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
                </div>
                {!! Form::submit('Envoyer !', ['class' => 'btn btn-info pull-right']) !!}
                {!! Form::close() !!}
            </div>
        </div>
        <a href="javascript:history.back()" class="btn btn-primary">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
        </a>
    </div>











@stop