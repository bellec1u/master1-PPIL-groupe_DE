@extends('template')

@section('contenu')
    <div class="col-sm-offset-4 col-sm-4">
    	<br>
		<div class="panel panel-primary">	
			<div class="panel-heading">Fiche d'utilisateur</div>
			<div class="panel-body">
				<img src="{{ $book->cover_url  }}" alt="" />
				<p>Titre : {{ $book->title  }}</p>
				<p>Auteur : {{ $book->author  }}</p>
				<p>Genre : {{ $book->genre  }}</p>
				<p>Langue : {{ $book->language  }}</p>
				<p>Date de Parution : {{ $book->publication_date  }}</p>
				<p>Note moyenne : {{ $book->stars_average  }}</p>
				<p><a href="4/open" class="btn bg-primary">Ouvrir</a></p>
			</div>
		</div>				
		<a href="javascript:history.back()" class="btn btn-primary">
			<span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
		</a>
	</div>
@stop