@extends('layouts.app')

@section('titre')
    Call Me Ishmael
@stop

@section('css')
	{!! Html::style('design/css/book.css') !!}
	{!! Html::style('design/css/navigation.css') !!}
@stop

@include('navigation')

@include('footer')


<!-- *****************************************************************************************************************
 BOOK
 ***************************************************************************************************************** -->

@section('content')

	@yield('navigation')

    <div id="detailBook" class="detailBook">
		 <div class="container">
		 	<div class="row detailGroupBook">
			 	<div class="detailBookImg col-sm-6">
					<img src="{{ $book->cover_url  }}" alt="">
			 	</div>
			 	<div class="descriptionBook col-sm-6">
			 		<h4>{{ $book->title }}</h4>
			 		<div class="hline"></div>
			 		<p><b>Genre :</b> {{ $book->genre }}</p>
			 		<p><b>Auteur :</b> {{ $book->author }}</p>
			 		<p><b>Langue :</b> {{ $book->language }}</p>
			 		<p><b>Date de parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
			 		<p><b>Note moyenne :</b> {{ $book->stars_average  }}</p>
			 		<a href="" class="btn btn-large btn-blue btn-primary">Ouvrir</a>
			 		<a href="" class="btn btn-large btn-blue btn-primary">Ajouter à la liste de lecture</a>
			 	</div>
		 	</div><! --/row -->
				<div class="row centered contenuComm">
				<div class="CommentaireGroupBook">
			 		<h1>Commentaire</h1>
			 		<a href="#MonCollapse" class="btn btn-large btn-blue btn-primary" data-toggle="collapse" aria-expanded="false" aria-controls="MonCollapse">Ajouter un commentaire</a>
			 		<div class="hline"></div>
			 		<div id="MonCollapse" class="collapse">
		                {!! Form::open(array('route'=>'storeRating','method'=>'POST', 'files'=>'true')) !!}
		                {!! csrf_field() !!}
		                <div class="form-group {!! $errors->has('stars') ? 'has-error' : '' !!}">
		                    <label class="col-sm-12 col-xs-12">Votre note :
	                        	<div class="rating rating2">
									<a href="#5" title="Give 5 stars">★</a>
									<a href="#4" title="Give 4 stars">★</a>
									<a href="#3" title="Give 3 stars">★</a>
									<a href="#2" title="Give 2 stars">★</a>
									<a href="#1" title="Give 1 stars">★</a>
								</div>
							</label>
		                </div>
		                <div class="col-lg-10 col-xs-12 form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
		                    <label>Votre commentaire :</label>
		                   {{ Form::hidden('book_id', $book) }}
		                        {!! Form::textarea ('comment', null, ['class' => 'form-control', 'placeholder' => 'Votre commentaire ici...']) !!}
		                        {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
		                </div>
		                <div class="col-lg-10 col-xs-12">
		                    {!! Form::submit('&Eacute;valuer', ['class' => 'btn btn-primary pull-right']) !!}
		                </div>
		                {!! Form::close() !!}
		        	</div>
		        </div>
		 	</div>
		 </div><! --/container -->
	</div>

	@yield('footer')

	@section('javascript')
	@stop

@stop