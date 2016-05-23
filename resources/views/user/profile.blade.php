@extends('template')

@section('titre')
Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('contenu')
<article class="panel panel-info">
    <h1 class="panel-heading">Liste de Lectures</h1>
    <div class="panel-body">

        <div class="row">
            <section class="col-sm-3">
                <table>
                <tr> <img src="{{ URL($user->profile_image) }}" alt="" /></tr>
            </section>
            <section class="col-sm-9"><br>
                <p><b>Nom :</b>  {{ $user->first_name }}</p>
                <p><b>Email :</b>  {{ $user->email}}</p>
                <p><b>Date de naissance :</b> {{ $user->birth_date}}</p>
                </table>
                <a href="{{URL::route('userEdit')}}" class="btn bg-primary">modifier</a>

            </section>

        </div><hr>

    </div>
</article>
@stop