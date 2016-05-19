@extends('template')

@section('contenu')
    <div class="col-sm-offset-3 col-sm-5">
    	<br>
		<div class="panel panel-primary">	
			<div class="panel-heading">Details livre </div>
			<div class="panel-body">
				<center><img src="{{ $book->cover_url  }}" alt="" /></center>
					</br>
				<p>Titre : {{ $book->title  }}</p>
				<p>Auteur : {{ $book->author  }}</p>
				<p>Genre : {{ $book->genre  }}</p>
				<p>Langue : {{ $book->language  }}</p>
				<p>Date de Parution : {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
				<p>Note moyenne : {{ $book->stars_average  }}</p>
				<hr />
				<p><a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn bg-primary">Ouvrir</a></p>
				@if(Auth::check())
				<p>{!! link_to('createRating/'.$book->id, 'Evaluer', $attribute = array(), $secrure = null ) !!}</p>

				{!! link_to('bookshelf/add/'.$book->id, 'ajouter a sa bibliothèque', $attribute = array(), $secrure = null ) !!}
				@endif
				@foreach($data as $rating)
					@if(Auth::Check())
					@if(Auth::user()->id == $rating->user_id)
						{!! link_to('editRating/'.$rating->id, 'Modifier', $attribute = array(), $secrure = null ) !!}
					@endif
					@endif
					<p> Commentaire {{ $rating->comment }} </p>
					<p> Note {{ $rating->stars }} </p>
				@endforeach
			</div>
		</div>				
		<a href="javascript:history.back()" class="btn btn-primary">
			<span class="glyphicon glyphicon-circle-arrow-left"></span> Retour
		</a>


	</div>
@stop