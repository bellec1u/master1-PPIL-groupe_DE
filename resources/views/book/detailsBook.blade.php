@extends('template')

@section('titre')
	 {{$book->title}}Call Me Ishmael
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
	<?php
	///////////////////////////////////////////////////////////////////////////////
	//  Fonction de génération de note en étoiles
	//  Les microdata et microformat sont inclus partout où nécessaire
	//  La note chiffrée est également présente mais masquée (et visible au survol)
	//  Le maximum peut être indiqué, par défaut c'est 5
	//  Les valeurs décimales sont gérées (le séparateur doit être le point)
	//  Aucune image n'est nécessaire, elles sont générées par le code en svg
	///////////////////////////////////////////////////////////////////////////////
	//  Utilisation
	//  makeRating(note à afficher [, note maximale])
	//  si la note maximale n'est pas spécifiée elle sera de 5 par défaut
	//  Exemples : makeRating(3.42); (note sur 5) ou makeRating(3.42, 10); (sur 10)
	///////////////////////////////////////////////////////////////////////////////

	function makeRating($rate, $bestvalue = 5) {
		// extraction de la partie entière de la note (qu'elle soit décimale ou non)
		$intrate=intval($rate);
		// calcul de la partie décimale éventuelle en %
		$decrate=(floatval($rate) - $intrate) * 100;
		//  insertion des microformats et microdatas
		$ratingBox  = '						<!-- item AggregateRating -->' . PHP_EOL;
		$ratingBox .= '				<p class="ratingBox" itemprop="aggregateRating" itemscope itemtype="http://schema.xyz/AggregateRating">' . PHP_EOL;
		$ratingBox .= '				<span title="'. $rate .' / '. $bestvalue .'">' . PHP_EOL;
		// génération du nombre d'étoiles correspondant au maximum possible
		for($i=0; $i<$bestvalue; ++$i) {
			$ratingBox .= '				<svg height="16" width="16">' . PHP_EOL;
			// étoile(s) totalement jaune(s) calculée(s) sur la valeur entière de la note
			if($i<$intrate) {
				$ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5" fill="Yellow" stroke="DarkKhaki" stroke-width=".5" />' . PHP_EOL;
			}
			elseif($i==$intrate && $decrate>0 ) {
				// étoile partiellement jaune basée sur la valeur décimale de la note
				$ratingBox .= '				  <defs>' . PHP_EOL;
				$ratingBox .= '				     <linearGradient id="starGradient">' . PHP_EOL;
				$ratingBox .= '				       <stop offset="'. $decrate .'%" stop-color="Yellow"/>' . PHP_EOL;
				$ratingBox .= '				       <stop offset="'. $decrate .'%" stop-color="LightGrey"/>' . PHP_EOL;
				$ratingBox .= '				     </linearGradient>' . PHP_EOL;
				$ratingBox .= '				  </defs>' . PHP_EOL;
				$ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5" fill="url(#starGradient)" stroke="DarkKhaki" stroke-width=".5" />' . PHP_EOL;
			}
			else {
				// étoile(s) grise(s) pour la fin
				$ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5"  fill="LightGrey" stroke="DimGray" stroke-width=".5" />' . PHP_EOL;
			}
			$ratingBox .= '				</svg>' . PHP_EOL;
		}
		$ratingBox .= '				</span>' . PHP_EOL;
		//  insertion de la note en texte clair mais masqué, avec microformat et microdata - supprimer style="display:none;" pour l'afficher
		$ratingBox .= '				<span style="display:none;"><span itemprop="ratingValue" class="rating" title="'. $rate .'">'. $rate .'</span>';
		$ratingBox .= '<span > / </span><span itemprop="bestRating">'. $bestvalue .'</span></span>' . PHP_EOL;
		$ratingBox .= '				</p>' . PHP_EOL;
		$ratingBox .= '						<!-- end of item -->' . PHP_EOL;
		return $ratingBox;
	}
	//  echo(makeRating(3.42));		// code de test
	?>



	<article class="panel panel-info">
		<h1 class="panel-heading">Détails du Livre</h1>
			<div class="panel-body">
				<div class="row">
                    <section class="col-sm-3">
                        <p><img src="{{ $book->cover_url  }}" alt="" /></p>
                    </section>
                    <section class="col-sm-9"><br>
                        <p><b>Titre :</b>  {{ $book->title }}</p>
                        <p><b>Genre :</b>  {{ $book->genre }}</p>
                        <p><b>Auteur :</b> {{ $book->author }}</p>
                        <p><b>Langue :</b> {{ $book->language }}</p>
                        <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}</p>
                        <p><b>Note moyenne : </b> {{ $book->stars_average  }}</p>
                        <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-primary">Ouvrir</a>

                		@if(Auth::check())
							@if(count($estEvalue) == 0)
							<a href="{{URL::route('createRating', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-primary">Évaluer</a>

							@endif

							<?php
								$count = Auth::user()->readings()->where('book_id', '=', $book->id)->count();
							?>
							@if($count == 0)
								<a href="{{URL::route('addReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-primary">Ajouter à la liste de lecture</a>
							@else
									<a href="{{URL::route('deleteReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-danger">Supprimer de la liste de lecture</a>
								@endif
						@endif
                    </section>
				</div>
				<section class="col-sm-3">

				</section>
				<section class="col-sm-6">
                    <p>@foreach($data as $rating)
						<p>	
							@if($rating->user != null)
								
								@if($rating->user->profile_image == '')
	                        		<p class="text-center"></p><img src="{{ URL('image_uploads/default.jpg') }}" alt="" width="10%" height="10%" /></p>
	                    		@else
	                        		<img src="{{ URL($rating->user->profile_image) }}" alt="" width="10%" height="10%" />	
	                        	@endif
								@if(Auth::check())
									<a href="{{URL::route('showOtherUser', array('id'=> $rating->user->id))}}" > {{ $rating->user->first_name }} {{ $rating->user->last_name }}</a>
								@else
									{{ $rating->user->first_name }} {{ $rating->user->last_name }}
								@endif
							@else
								Anonyme
							@endif

							<?php echo makeRating($rating->stars);
								$test = true;
							?>
								@if(Auth::check())
									@foreach($followers as $follower)
										@if($follower->followed_user_id == $rating->user_id)
											{{ Form::open(array('route' => array('deleteFollower', 'id'=>$follower->id), 'method' => 'delete', 'name'=>'desinscrire')) }}
											{!! Form::submit('Ne plus suivre', ['class'=>"btn btn-danger pull-right"]) !!}
											<?php $test =false; ?>
										@endif
									@endforeach
									@if($test && $rating->user_id!= Auth::user()->id && $rating->user != null )
										{!! Form::open(array('route'=>'addFollower', 'method'=>'POST')) !!}
										{{ Form::hidden("followed_user_id", $rating->user_id ) }}
										{!! Form::submit('Suivre', ['class' => 'btn btn-info pull-right']) !!}
										{!! Form::close() !!}
									@endif
								@endif
								@if(Auth::check() && Auth::user()->id == $rating->user_id)
								{!! link_to('editRating/'.$rating->id, 'Modifier', $attribute = array(), $secrure = null ) !!}
								@endif
									Commentaire :  {{ $rating->comment }}
						</p><hr>
					@endforeach </p>
				</section>
			</div>
	</article>
@stop