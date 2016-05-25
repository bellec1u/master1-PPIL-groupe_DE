@extends('template')

@section('titre')
    Notifications
@stop

@section('contenu')
    <article class="panel panel-info">
        <h1 class="panel-heading">Notifications</h1>
        <div class="panel-body">
            @if(count($notifications) > 0 )
                @foreach($notifications as $notification)
                    <div class="row">
                        <section class="col-sm-9"><br>
                            <p><b>Date : </b> {{ $notification->created_at}} </p>
                            <p><b>Type :</b>   {{ $notification->type}}         </p>
                            <p><b>Détails :</b>{{ $notification->details}}      </p>
                    </section>
                </div><hr>
                @endforeach
            @else
                <p>Pas de notifications en cours.</p>
            @endif
        </div>
    </article>
@stop