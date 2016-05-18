@extends('user.template')





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
            <div class="panel-heading">Ajout d'un article</div>
            <div class="panel-body">
                <A href="javascript:;" onClick="supprimer();">Supprimer</A>


                {!! Form::open(array('route'=>'updateRating','method'=>'POST', 'files'=>'true')) !!}
                {!! csrf_field() !!}
                <div class="form-group {!! $errors->has('titre') ? 'has-error' : '' !!}">
                    {!! Form::number('stars', $ratings->stars,['class' => 'form-control', 'max'=>'5', 'min'=>'0' ] ) !!}
                    {!! $errors->first('stars', '<small class="help-block">:message</small>') !!}
                </div>

                {{ Form::hidden('id', $ratings->id) }}
                {{ Form::hidden('book_id', $ratings->book_id) }}
                <div class="form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
                    {!! Form::textarea ('comment', $ratings->comment, ['class' => 'form-control']) !!}
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


