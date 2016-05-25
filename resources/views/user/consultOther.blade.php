@extends('template')

@section('titre')
    {{ $user->last_name }} {{ $user->first_name }}
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
        <h1 class="panel-heading">Profil de {{ $user->last_name }} {{ $user->first_name }}</h1>
        <div class="panel-body">

            <div class="row">
                <section class="col-sm-3">

                    @if($user->profile_image == '')
                        <img src="{{ URL('image_uploads/default.jpg') }}" alt="" width="50%" height="50%" />

                    @else

                        <img src="{{ URL($user->profile_image) }}" alt="" width="50%" height="50%" />
                    @endif
                </section>

                <section class="col-sm-9"><br>
                    @if(!$estSuivi)
                {!! Form::open(array('route'=>'addFollower', 'method'=>'POST')) !!}
                {{ Form::hidden("followed_user_id",$user->id ) }}

                    {!! Form::submit('Suivre', ['class' => 'btn btn-info pull-right']) !!}
                    {!! Form::close() !!}

                 @else
                        {{ Form::open(array('route' => array('deleteFollower', 'id'=>$idFollower), 'method' => 'delete', 'name'=>'desinscrire')) }}

                        {!! Form::submit('Ne plus suivre', ['class'=>"btn btn-danger pull-right"]) !!}
                @endif
                    <p><b>Nom :</b>  {{ $user->first_name }}</p>
                    <p><b>Email :</b>  {{ $user->email}}</p>
                    <p><b>Date de naissance :</b> {{ $user->birth_date}}</p>



                </section>

            </div><hr>
            <h2 align="center" color="'green"> Liste de lecture : </h2>
            <div class="row">

            @foreach($user->readings as $reading)
                <section class="col-sm-3">
                    <p><a href="{{ URL::route('bookReturn', array('id'=>$reading->book_id))}}"><img src="{{ $reading->book->cover_url  }}" alt="" /></a></p>
                </section>
                <section class="col-sm-9"><br>
                    <p><b>Titre :</b>  {{ $reading->book->title }}</p>
                    <p><b>Genre :</b>  {{ $reading->book->genre }}</p>
                    <p><b>Auteur :</b> {{ $reading->book->author }}</p>
                    <p><b>Langue :</b> {{ $reading->book->language }}</p>
                    <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($reading->book->publication_date))  }}</p>
                    <p><b>Note moyenne : </b> {{ $reading->book->stars_average  }}</p>

                </section>
            @endforeach
            </div><hr>
            <h2 align="center">Dernières Evaluations : </h2>

            @foreach($user->ratings as $rating)
                <p><a href="{{ URL::route('bookReturn', array('id'=>$rating->book_id))}}"> Livre : {{$rating->book->title  }}</a></p>
           <p></p>
            <?php echo makeRating($rating->stars)  ?>
            Commentaire :  {{ $rating->comment }}
            @endforeach
        </div>
    </article>
@stop