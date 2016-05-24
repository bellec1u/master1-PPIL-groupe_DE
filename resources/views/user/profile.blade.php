@extends('template')

@section('titre')
Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
    <SCRIPT language="Javascript">
        function Desinscription() {
            if (confirm ('Etes vous sûr de vous désinscrire ')){
                document.forms["desinscrire"].submit();
            }
        }

    </script>
<article class="panel panel-info">
    <h1 class="panel-heading">Profil</h1>
    <div class="panel-body">

        <div class="row">
            <section class="col-sm-3">
                <table>
                    @if($user->profile_image == '')
                        <tr> <img src="{{ URL('image_uploads/default.jpg') }}" alt="" width="75%" height="50%" /></tr>

                    @else

                              <tr> <img src="{{ URL($user->profile_image) }}" alt="" width="75%" height="50%" /></tr>
                    @endif
            </section>
            <section class="col-sm-9"><br>
                <p><b>Nom :</b>  {{ $user->first_name }}</p>
                <p><b>Email :</b>  {{ $user->email}}</p>
                <p><b>Date de naissance :</b> {{ $user->birth_date}}</p>
                </table>


                {{ Form::open(array('route' => 'userDelete', 'method' => 'delete', 'name'=>'desinscrire')) }}
                <a href="{{URL::route('userEdit')}}" class="btn bg-primary">modifier</a>
                {!! Form::button('Desisncription', ['class' => 'btn btn-primary pull-right', 'onclick'=>"Desinscription()"]) !!}
                {{ Form::close()}}

            </section>

        </div><hr>

    </div>
</article>
@stop