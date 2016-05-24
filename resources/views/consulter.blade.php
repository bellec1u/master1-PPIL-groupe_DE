@extends('template')

@section('titre')
    Liste de lecture
@stop

@section('contenu')
        <article class="panel panel-info">
            <h1 class="panel-heading">Liste de Lectures</h1>
            <div class="panel-body">
                @if(count($data) > 1 )
                    @foreach($data as $book)
                        @if($book != '')
                            <div class="row">
                                <section class="col-sm-3">
                                    <p><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"><img src="{{ $book->cover_url  }}" alt="" /></a></p>
                                </section>
                                <section class="col-sm-9"><br>
                                    <p><b>Titre :</b>  {{ $book->title }}</p>
                                    <p><b>Genre :</b>  {{ $book->genre }}</p>
                                    <p><b>Auteur :</b> {{ $book->author }}</p>
                                    <p><b>Langue :</b> {{ $book->language }}</p>
                                    <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
                                    <p><b>Note moyenne : </b> {{ $book->stars_average  }}</p>
                                    <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-primary">Ouvrir</a>
                                    <a href="{{URL::route('createRating', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-primary">Évaluer</a>
                                    <a href="{{URL::route('deleteReading', array('id'=> $book->id))}}" class="btn btn-primary">Supprimer</a>
                                </section>
                            </div><hr>
                        @endif
                    @endforeach
                @else
                    Aucun livre actuellement.
                @endif
            </div>
        </article>
@stop