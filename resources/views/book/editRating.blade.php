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
    <SCRIPT language="Javascript">
        function supprimer() {
            var r = confirm("voulez-vous vraiment suprimer  ce comentaire");
            if (r == true) {
                window.location = "{{URL::to('destroyRating/'.$ratings->id.'/'.$ratings->book_id)}}";
            }
        }

        </script>
    <br>
    <div class="col-sm-offset-3 col-sm-6">
        <div class="panel panel-info">
            <div class="panel-heading">Modifier note </div>
            <div class="panel-body">


                <div class="panel-heading"> </div>
                <div class="panel-body">
                {!! Form::open(array('route'=>'updateRating','method'=>'POST', 'files'=>'true')) !!}
                {!! csrf_field() !!}

                    <div id='A1'><script type='text/javascript'>CreateListeEtoile('A1',5);</script></div>

                    {!! Form::hidden('stars', $ratings->stars,['id'=>'etoile'] ) !!}

                </div>
            </div>
        </div>
            <div class="panel panel-info">
                <div class="panel-heading">Modifier un commentaire </div>
                <div class="panel-body">
                {{ Form::hidden('id', $ratings->id) }}
                {{ Form::hidden('book_id', $ratings->book_id) }}
                <div class="form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
                    {!! Form::textarea ('comment', $ratings->comment, ['class' => 'form-control']) !!}
                    {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
                </div>
                    <A href="javascript:;" onClick="supprimer();">Supprimer</A>
                {!! Form::submit('Envoyer !', ['class' => 'btn btn-info pull-right']) !!}
                {!! Form::close() !!}
            </div>
        </div>

        <a href="javascript:history.back()" class="btn btn-primary">
            <span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
        </a>
    </div>
@stop