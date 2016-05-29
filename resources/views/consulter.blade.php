@extends('template')

@section('titre')
    Liste de lectures
@stop
@section('head')


@stop
@section('contenu')
    <SCRIPT language="Javascript">
        function Desinscription() {
            if (confirm ('Etes vous sûr de vouloir supprimer votre livre de votre bibliothèque personnel ?')){
                document.forms["desinscrire"].submit();
            }
        }
    </script>

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

    function makeRating($rate, $bestvalue = 5)
    {
        // extraction de la partie entière de la note (qu'elle soit décimale ou non)
        $intrate = intval($rate);
        // calcul de la partie décimale éventuelle en %
        $decrate = (floatval($rate) - $intrate) * 100;
        //  insertion des microformats et microdatas
        $ratingBox = '						<!-- item AggregateRating -->' . PHP_EOL;
        $ratingBox .= '				<p class="ratingBox" itemprop="aggregateRating" itemscope itemtype="http://schema.xyz/AggregateRating">' . PHP_EOL;
        $ratingBox .= '				<span title="' . $rate . ' / ' . $bestvalue . '">' . PHP_EOL;
        // génération du nombre d'étoiles correspondant au maximum possible
        for ($i = 0; $i < $bestvalue; ++$i) {
            $ratingBox .= '				<svg height="16" width="16">' . PHP_EOL;
            // étoile(s) totalement jaune(s) calculée(s) sur la valeur entière de la note
            if ($i < $intrate) {
                $ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5" fill="Yellow" stroke="DarkKhaki" stroke-width=".5" />' . PHP_EOL;
            } elseif ($i == $intrate && $decrate > 0) {
                // étoile partiellement jaune basée sur la valeur décimale de la note
                $ratingBox .= '				  <defs>' . PHP_EOL;
                $ratingBox .= '				     <linearGradient id="starGradient">' . PHP_EOL;
                $ratingBox .= '				       <stop offset="' . $decrate . '%" stop-color="Yellow"/>' . PHP_EOL;
                $ratingBox .= '				       <stop offset="' . $decrate . '%" stop-color="LightGrey"/>' . PHP_EOL;
                $ratingBox .= '				     </linearGradient>' . PHP_EOL;
                $ratingBox .= '				  </defs>' . PHP_EOL;
                $ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5" fill="url(#starGradient)" stroke="DarkKhaki" stroke-width=".5" />' . PHP_EOL;
            } else {
                // étoile(s) grise(s) pour la fin
                $ratingBox .= '				  <polygon points="8,0 10.5,5 16,6 12,10 13,16 8,13 3,16 4,10 0,6 5.5,5"  fill="LightGrey" stroke="DimGray" stroke-width=".5" />' . PHP_EOL;
            }
            $ratingBox .= '				</svg>' . PHP_EOL;
        }
        $ratingBox .= '				</span>' . PHP_EOL;
        //  insertion de la note en texte clair mais masqué, avec microformat et microdata - supprimer style="display:none;" pour l'afficher
        $ratingBox .= '				<span style="display:none;"><span itemprop="ratingValue" class="rating" title="' . $rate . '">' . $rate . '</span>';
        $ratingBox .= '<span > / </span><span itemprop="bestRating">' . $bestvalue . '</span></span>' . PHP_EOL;
        $ratingBox .= '				</p>' . PHP_EOL;
        $ratingBox .= '						<!-- end of item -->' . PHP_EOL;
        return $ratingBox;
    }
    //  echo(makeRating(3.42));		// code de test
    ?>

    <article class="panel panel-info">
        <h1 class="panel-heading">Liste de Lectures</h1>
        <div class="panel-body">
            @if(count($data) > 1 )
                @foreach($data as $book)
                    @if($book != '')
                        <div class="row">
                            <section class="col-sm-3">
                                <p><a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}">
                                        <img src="{{ $book->cover_url  }}" alt=""/></a>
                                </p>
                            </section>
                            <section class="col-sm-9"><br>
                                <p><b>Titre :</b> {{ $book->title }}</p>
                                <p><b>Genre :</b> {{ $book->genre }}</p>
                                <p><b>Auteur :</b> {{ $book->author }}</p>
                                <p><b>Langue :</b> {{ $book->language }}</p>
                                <p><b>Date de Parution :</b> {{ date('d-m-Y', strtotime($book->publication_date))  }}
                                </p>
                                <table>
                                    <tr>
                                        <td valign="top"><b> Note moyenne :</b></td>
                                        <td valign="bottom"><?php echo makeRating($book->stars_average);?></td>
                                    </tr>
                                </table>
                                <div class="pull-right">
                                    <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}"
                                       class="btn btn-primary">Ouvrir</a>

                                <a href="{{URL::route('bookOpen', array('id'=>$book->id, 'path'=>Request::url()))}}"
                                   class="btn btn-primary pull-right">Ouvrir</a>

                                <a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}"
                                   class="btn btn-info pull-right"> Détails</a>
                                {{ Form::open(array('route' => array('deleteReading', 'id'=>$book->id), 'method' => 'get', 'name'=>'desinscrire')) }}
                                {!! Form::button('Supprimer', ['class' => 'btn btn-danger pull-right', 'onclick'=>"Desinscription()"]) !!}
                                {{ Form::close()}}
                                </div>

                            </section>
                        </div>
                        <hr>
                    @endif
                @endforeach
            @else
                <p>Aucun livre actuellement.</p>
            @endif
        </div>
    </article>
@stop