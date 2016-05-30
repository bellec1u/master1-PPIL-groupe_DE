@extends('layouts.app')

@section('titre')
    Call Me Ishmael
@stop

@section('css')
	{!! Html::style('design/css/book.css') !!}
	{!! Html::style('design/css/navigation.css') !!}
	{!! Html::style('design/css/comment.css') !!}
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
		 	<div id="headerwrap">
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
				 		<p class="rating3"><b>Note moyenne :</b> 
				 			<div class="rating justStart rating4">
                        		<?php 
                        			for ($i=1; $i<=5; $i++){
                        				if($i <= $book->stars_average){
                        					?><p class="isStart" href="#">★</p><?php 
                        				}else{
                        					?><p href="#">★</p><?php 
                        				}
                        			}
								?>
							</div></p>
				 		@if(Auth::check())
							@if(count($estEvalue) == 0)
				 				<a href="{{URL::route('createRating', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-large btn-blue btn-primary">Ouvrir</a>
				 			@endif
				 			<?php
								$count = Auth::user()->readings()->where('book_id', '=', $book->id)->count();
							?>
							@if($count == 0)
				 				<a href="{{URL::route('addReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-large btn-blue btn-primary">Ajouter à la liste de lecture</a>
				 			@else
				 				<a href="{{URL::route('deleteReading', array('id'=>$book->id, 'path'=>Request::url()))}}" class="btn btn-large btn-blue btn-danger">Supprimer de la liste de lecture</a>
				 			@endif
				 		@endif
				 	</div>
			 	</div>
			</div> 
		 	<div id="titleDetailComment">
		    	<div class="container">
					<div class="row centered col-sm-12 ">
		    			<h1 class="titleStyle">The<span class="sousTitle">Comments</span></h1>
		    		</div>
		        </div>
		    </div>
		 	<div id="detailBookComment">
				<div class="row centered contenuComm col-sm-12" id="test">
					@if(Auth::check())
						@if(count($estEvalue) == 0)
							<a href="#MonCollapse"  class="btn btn-large btn-blue btn-primary btComm" data-toggle="collapse" aria-expanded="false" aria-controls="MonCollapse">Evaluer le livre</a>
						@endif
					@endif
			 		<div id="MonCollapse" class="collapse col-sm-12">
		                {!! Form::open(array('route'=>'storeRating','method'=>'POST', 'files'=>'true')) !!}
		                {!! csrf_field() !!}
		                <div class="form-group {!! $errors->has('stars') ? 'has-error' : '' !!}">
		                    <label class="col-sm-12 col-xs-12">Votre note :
	                        	<div class="rating rating2">
									<a onclick="setStart(5)" href="#5">★</a>
									<a onclick="setStart(4)" href="#4">★</a>
									<a onclick="setStart(3)" href="#3">★</a>
									<a onclick="setStart(2)" href="#2">★</a>
									<a onclick="setStart(1)" href="#1">★</a>
								</div>
								{!! Form::hidden('stars', null,['id'=>'etoile'] ) !!}
							</label>
		                </div>
		                <div class="col-sm-12 form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
		                    <label>Votre commentaire :</label>
		                    {{ Form::hidden('book_id', $book->id) }}
		                        {!! Form::textarea ('comment', null, ['class' => 'form-control', 'placeholder' => 'Votre commentaire ici...']) !!}
		                        {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
		                </div>
		                <div class="col-sm-12 col-xs-12">
		                    {!! Form::submit('&Eacute;valuer', ['class' => 'btn btn-primary ']) !!}
		                </div>
		                {!! Form::close() !!}
		        	</div>

			        <div class="col-sm-12">
				        <div class="panel">
				    		<!--Widget body-->
				    		<div id="demo-chat-body" class="collapse in">
				    			<div class="nano has-scrollbar">
				    				<div class="nano-content pad-all" tabindex="0" style="right: -17px;">
				    					<ul class="list-unstyled media-block">
				    						@foreach($data as $rating) 
	    									<li class="mar-btm">
	    										<div class="media-left">
	    											<img src="{{ URL($rating->user->profile_image) }}" class="img-circle img-sm" alt="Profile Picture">
	    										</div>
		    									<div class="media-body pad-hor">
			    									<div class="speech">
			    									@if($rating->user != null)
				    									@if(Auth::check() && Auth::user()->id != $rating->user->id)
				    										<a href="{{URL::route('showOtherUser', array('id'=> $rating->user->id))}}" class="media-heading">{{ $rating->user->first_name }} {{ $rating->user->last_name }}</a>
				    									@else
				    										<a href="{{url('user/profile')}}" class="media-heading">
				    										{{ $rating->user->first_name }} {{ $rating->user->last_name }}</a>
				    									@endif

				    										
							    					@else
							                            Anonyme
							                        @endif
							                        	<div class="rating justStart">
						                        		<?php 
						                        			for ($i=1; $i<=5; $i++){
						                        				if($i <= $rating->stars){
						                        					?><p class="isStart" href="#">★</p><?php 
						                        				}else{
						                        					?><p href="#">★</p><?php 
						                        				}
						                        			}
														?>
														</div>
				    									<p>{{ $rating->comment }}</p>
				    									<p class="speech-time">
				    									<?php $test = true;
								                        ?>
				    									@if(Auth::check())
								                            @foreach($followers as $follower)
								                                @if($follower->followed_user_id == $rating->user_id)
								                                    {{ Form::open(array('route' => array('deleteFollower', 'id'=>$follower->id), 'method' => 'delete', 'name'=>'desinscrire')) }}
								                                    {!! Form::submit('Ne plus suivre', ['class'=>"btn btn-danger pull-right"]) !!}
								                                    {!! Form::close() !!}
								                                    <?php $test = false; ?>
								                                @endif
								                            @endforeach
								                            <?php 
								                                $userFollow = $rating->user;
								                            ?>
								                            @if($test && $rating->user_id!= Auth::user()->id && $rating->user != null  && $userFollow->following_allowed ==true)
								                                {!! Form::open(array('route'=>'addFollower', 'method'=>'POST')) !!}
								                                {!! Form::hidden("followed_user_id", $rating->user_id ) !!}
								                                {!! Form::submit('Suivre', ['class' => 'btn btn-success pull-right']) !!}
								                                {!! Form::close() !!}
								                            @endif
								                        @endif
								                        @if(Auth::check() && Auth::user()->id == $rating->user_id)
								                            {!! link_to('editRating/'.$rating->id, 'Modifier', $attribute = array(), $secrure = null ) !!}
								                        @endif
				    									</p>
			    									</div>
		    									</div>
		    								</li>
				    						@endforeach</p>
				    					</ul>
				    				</div>
				    			<div class="nano-pane"><div class="nano-slider" style="height: 141px; transform: translate(0px, 0px);"></div></div></div>
				    		</div>
				    	</div>
				    </div>
			 	</div>
		 	</div>
		 </div><! --/container -->
	</div>

	@yield('footer')

	@section('javascript')
        <script>
        	function setStart(id){
			    $('#etoile').val(id);
			}
            $(function(){
                var password = $("input[name='password']").parent();
                var email = $("input[name='email']").parent();
                $('form#log').submit(function(e) {
                    e.preventDefault();
                    password.removeClass('has-error');  
                    email.removeClass('has-error'); 
                    $.post($(this).attr('action'), $(this).serialize())
                    .done(function(data) {
                        if(data.ok) {
                        	location.reload(true);
                            $('ul.nav').removeClass('hidden');
                            $('form#log').addClass('hidden');
                            if(data.ok == 'admin') $('form#del').removeClass('hidden');
                            $('#myModal').modal('hide');
                        } else if(data.require) {
                            if(data.require.password)
                                password.addClass('has-error'); 
                            if(data.require.email) 
                                email.addClass('has-error');
                        } else if(data.response == 'fail') {
                            password.addClass('has-error'); 
                            email.addClass('has-error');
                        }
                    });
                });
            });
        </script>
    @stop

@stop