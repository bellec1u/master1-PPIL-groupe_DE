@extends('template')

@section('titre')
    Liste de Membres Suivis
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Liste de Membres Suivis</h1>
        <div class="panel-body">
            @if(count($listeFolower) > 0 )
                @foreach($listeFolower as $folower)

                        <div class="row">
                            <section class="col-sm-3">
                            @if( $folower->followee->profile_image == '')
                                <tr> 	<a href="{{URL::route('showOtherUser', array('id'=>  $folower->followee->id))}}" > <img src="{{ URL('image_uploads/default.jpg') }}" alt="" width="50%" height="50%" /></a></tr>

                            @else

                                <tr> <a href="{{URL::route('showOtherUser', array('id'=>  $folower->followee->id))}}" > <img src="{{ URL($folower->followee->profile_image) }}" alt="" width="50%" height="50%" /></a></tr>
                            @endif
                            </section>
                            <section class="col-sm-9"><br>
                                <div align="'right">
                                    <table>
                                        <tr>


                                            {{ Form::open(array('route' => array('deleteFollower', 'id'=>$folower->id), 'method' => 'delete', 'name'=>'desinscrire')) }}

                                            {!! Form::submit('Ne plus suivre', ['class'=>"btn btn-danger pull-right"]) !!}



                                            {{ Form::close()}}

                                            {{ Form::open(array('route' => array('updateFollower', 'id'=>$folower->id), 'method' => 'put', 'name'=>'desinscrire')) }}
                                            @if($folower->notifications_accepted)
                                                {!! Form::submit('Ne plus notifier', ['class'=>"btn btn-danger pull-right"]) !!}
                                            @else

                                                {!! Form::submit('Notifier', ['class'=>"btn btn-info pull-right"]) !!}
                                            @endif
                                            {{ Form::close()}}





                                        </tr>


                                    </table>

                                </div>
                                <p><b>Nom :</b>  {{ $folower->followee->last_name}}</p>
                                <p><b>Prenom :</b>  {{ $folower->followee->first_name}}</p>
                                <p><b>Date de naissance :</b>  {{ $folower->followee->birth_date}}</p>
                                @if($folower->followee->sex == 'M')
                                    <p><b> Sex :</b> Homme </p>
                                    @elseif($folower->followee->sex == '')
                                    <p><b> Sex :</b></p>
                                    @else
                                    <p><b> Sex :</b> Femme </p>
                                @endif

                            </section>


                        </div><hr>

                @endforeach
            @else
                <p>Aucun membre suivi actuellement.</p>
            @endif
        </div>
    </article>
@stop