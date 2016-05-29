@extends('template')

@section('titre')
    Profil de {{ $user->first_name }}
@stop

@section('contenu')
    <SCRIPT language="Javascript">
        function Desinscription() {
            if (confirm ('Etes vous sûr de vouloir vous désinscrire ?')){
                document.forms["desinscrire"].submit();
            }
        }
    </script>
<article class="panel panel-info">
    <h1 class="panel-heading">Profil</h1>
        <div class="panel-body">
            <div class="col-sm-3">
                    @if($user->profile_image == '')
                        <p class="text-center"></p><img src="{{ URL('image_uploads/default.jpg') }}" alt="" width="50%" height="50%" /></p>
                    @else
                        <p class="text-center"><img class="text-center" src="{{ URL($user->profile_image) }}" alt="" width="50%" height="50%" /></p>
                    @endif
            </div>
            <div class="col-sm-9">
                <p><b>Nom :</b>  {{ $user->first_name }}</p>
                <p><b>Email :</b>  {{ $user->email}}</p>
                <p><b>Date de naissance :</b> {{ $user->birth_date}}</p>
                <p><b>Etat du profil :</b> 
                        @if($user->following_allowed)
                            publique
                        @else 
                            privé
                        @endif </p>
                <table>
                    <tr>
                        <td><a href="{{URL::route('userEdit')}}" class="btn btn-primary">Modifier</a></td>
                        @if($user->following_allowed)
                            <td>{{ Form::open(array('route' => 'following_allowed', 'method' => 'post', 'name'=>'followAllowed')) }}
                                {!! Form::submit('Rendre profil privé', ['class' => 'btn btn-warning']) !!}
                                {{ Form::close()}} 
                            </td>
                        @else
                            <td>{{ Form::open(array('route' => 'following_allowed', 'method' => 'post', 'name'=>'followAllowed')) }}
                                {!! Form::submit('Rendre profil publique', ['class' => 'btn btn-success']) !!}
                                {{ Form::close()}} </td>
                        @endif
                        <td>{{ Form::open(array('route' => 'userDelete', 'method' => 'delete', 'name'=>'desinscrire')) }}
                            {!! Form::button('Désinscription', ['class' => 'btn btn-danger', 'onclick'=>"Desinscription()"]) !!}
                            {{ Form::close()}}
                        </td>
                    </tr>
                </table>
            </div>
        </div>
</article>
@stop