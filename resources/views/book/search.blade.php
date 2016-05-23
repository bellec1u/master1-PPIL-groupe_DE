@extends('template')

@section('titre')
    Recherche
@stop

@section('head')
    <style>
        .cover {
            width: 70%;
        }

    </style>
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Rechercher un livre</h1>
        <div class="panel-body">

            <div class="row">
                {!! Form::open(array('route'=>'bookSearch', 'method'=>'GET')) !!}
                    <div class="form-group">
                        <div class="input-group col-lg-7">
                            <input type="text" class="form-control" name="query" placeholder="Rechercher un Ebook..." />
                            <div class="input-group-btn">
                                <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
                            </div>
                        </div>
                    </div>
                {!! Form::close() !!}
            </div>
            @foreach($books as $book)
                @if($book != '')
                    <div class="row">
                        <section class="col-sm-2">
                            <p class="text-center"><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img class="cover" src="{{ $book->cover_url  }}" alt="img_cover" /></a></p>
                        </section>
                        <section class="col-sm-5">
                            <p><b>Titre :</b>  {{ $book->title }}</p>
                            <p><b>Genre :</b>  {{ $book->genre }}</p>
                            <p><b>Auteur :</b> {{ $book->author }}</p>
                        </section>
                        <section class="col-sm-3">
                            <p><b>Langue :</b> {{ $book->language }}</p>
                            <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
                            <p><b>Note moyenne : </b> {{ $book->stars_average }}/5</p>
                            <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn bg-primary">Ouvrir</a>
                        </section>

                    </div><hr>
                @endif
            @endforeach

        </div>
    </article>
@stop