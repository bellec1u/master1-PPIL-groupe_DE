@extends('template')

@section('titre')
    Recherche
@stop

@section('head')
    <style>
        .cover {
            width: 70%;
        }

        .center-div {
            margin: 0 auto;
        }

    </style>
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Rechercher un livre</h1>
        <div class="panel-body">

            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    {!! Form::open(array('route'=>'bookSearch', 'method'=>'GET')) !!}
                        <div class="form-group row">
                            <div class="input-group">
                                <input type="text" class="form-control" value="{{ old('query') }}" name="query" placeholder="Rechercher un Ebook..." />
                                <div class="input-group-btn">
                                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-7">
                                {!! Form::text('author', old('author'), ['class' => 'form-control', 'placeholder' => 'Auteur']) !!}
                            </div>
                            <div class="form-group form-horizontal">
                            <label for="lang" class="col-sm-2 control-label">Langue : </label>
                            <div class="col-sm-2">
                                {!! Form::select('lang', $langs, old('lang'), ['class' => 'form-control', 'id' => 'lang']) !!}
                            </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <div class="col-sm-7">
                                {!! Form::text('genre', old('genre'), ['class' => 'form-control', 'placeholder' => 'Genre']) !!}
                            </div>
                        </div>
                    {!! Form::close() !!}
                </div>
            </div>
            <hr>
            <div class="row">
                <div class="col-md-6" style="margin-bottom: 20px;">
                    {{ $books->total() }} résultat(s) trouvé(s)
                </div>
            </div>
            <div class="row">
                <div class="col-md-8">
                    {!! $books->appends(Request::except('page'))->links() !!}
                </div>
            </div>

            @foreach($books as $book)
                <div class="row">

                    <section class="col-sm-2">
                        <p class="text-center"><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img class="cover" src="{{ $book->cover_url  }}" alt="img_cover" /></a></p>
                    </section>
                    <section class="col-sm-4">
                        <p><b>Titre :</b>  {{ $book->title }}</p>
                        <p><b>Genre :</b>  {{ $book->genre }}</p>
                        <p><b>Auteur :</b> {{ $book->author }}</p>
                    </section>
                    <section class="col-sm-3">
                        <p><b>Langue :</b> {{ $book->language }}</p>
                        <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
                        <p><b>Note moyenne : </b> {{ $book->stars_average }}/5</p>

                    </section>
                    <section class="col-sm-3 text-center">
                        <div class="btn-group-vertical">
                            <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-default">Ouvrir</a>
                            <?php
                                $count = -1;
                                if(Auth::check()){
                                    $count = Auth::user()->readings()->where('book_id', '=', $book->id)->count();
                                }
                            ?>

                            @if($count == 0 || Auth::guest())
                                <a href="{{URL::route('addReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-success">Ajouter à la liste de lecture</a>
                            @elseif($count > 0)
                                <a href="{{URL::route('deleteReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-danger">Supprimer de la liste de lecture</a>
                            @endif
                            <a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"  class="btn btn-info">Détails</a>
                        </div>
                    </section>


                </div><hr>
            @endforeach

            {!! $books->appends(Request::except('page'))->links() !!}

        </div>
    </article>
@stop