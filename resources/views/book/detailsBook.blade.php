@extends('layouts.app')

@section('titre')
    Call Me Ishmael
@stop

@section('css')
	{!! Html::style('design/css/book.css') !!}
	{!! Html::style('design/css/navigation.css') !!}
	{!! Html::style('design/css/styleLoginBook.css') !!}
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
				 		<p><b>Note moyenne :</b> {{ $book->stars_average  }}</p>
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
					@else
						<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">Lancer la modal</button>
					@endif
			 		<div id="MonCollapse" class="collapse col-sm-12">
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
		                <div class="col-sm-12 form-group {!! $errors->has('comment') ? 'has-error' : '' !!}">
		                    <label>Votre commentaire :</label>
		                     <label>{{ $book->book_id }}</label>
		                        {!! Form::textarea ('comment', null, ['class' => 'form-control', 'placeholder' => 'Votre commentaire ici...']) !!}
		                        {!! $errors->first('comment', '<small class="help-block">:message</small>') !!}
		                </div>
		                <div class="col-sm-12 col-xs-12">
		                    {!! Form::submit('&Eacute;valuer', ['class' => 'btn btn-primary ']) !!}
		                </div>
		                {!! Form::close() !!}
		        	</div>

		        	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			        	<div class="modal-dialog">
			        		<div class="modal-content">
			        			<div class="modal-header">
			        				<button type="button" class="close" data-dismiss="modal">
			        					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			        				</button>
			        				<h3 class="modal-title" id="modal-login-label">Se connecter</h3>
			        			</div>
			        			
			        			<div class="modal-body">
				                    <form class="form-signin" role="form" id="log" method="POST" action="{{ url('login') }}">
				                    {!! csrf_field() !!}
				                    	<div class="form-group{{ $errors->has('email') ? ' has-error' : '' }}">
				                    		<input class="form-control" name="email" placeholder="Adresse email" type="text" value="{{ old('email') }}"  required>
				                        	<small class="help-block"></small>
				                        </div>
				                        <div class="form-group{{ $errors->has('password') ? ' has-error' : '' }}">
                                            <input class="form-control" name="password" placeholder="Mot de passe" type="password" required>     
                                            <small class="help-block"></small>
				                        </div>

			                            <div class="checkbox">
			                                <label>
			                                    <input type="checkbox" name="remember"> Se souvenir de moi
			                                </label>
			                                <a href="{{ url('/password/reset') }}">Mot de passe oublié ?</a>
			                            </div>

			                            <button type="submit" class="btn btn-blue btn-success">Se connecter</button>
			                            <div class="division">
			                                <div class="line l"></div>
			                                <span>OU</span>
			                                <div class="line r"></div>
			                            </div>

			                            <div class="social-login-buttons">
			                                <a class="btn btn-fb" href="#">Facebook</a>
			                                <a class="btn btn-google" href="#">Google</a>
			                            </div>
				                    </form>
			        			</div>
			        		</div>
			        	</div>
			        </div>

			        <div class="col-sm-12">
				        <div class="panel">
				    		<!--Widget body-->
				    		<div id="demo-chat-body" class="collapse in">
				    			<div class="nano has-scrollbar">
				    				<div class="nano-content pad-all" tabindex="0" style="right: -17px;">
				    					<ul class="list-unstyled media-block">
				    						<li class="mar-btm">
				    							<div class="media-left">
				    								<img src="http://bootdey.com/img/Content/avatar/avatar1.png" class="img-circle img-sm" alt="Profile Picture">
				    							</div>
				    							<div class="media-body pad-hor">
				    								<div class="speech">
				    									<a href="#" class="media-heading">John Doe</a>
				    									<p>Hello Lucy, how can I help you today ?</p>
				    									<p class="speech-time">
				    									<i class="fa fa-clock-o fa-fw"></i>09:23AM
				    									</p>
				    								</div>
				    							</div>
				    						</li>
				    						<li class="mar-btm">
				    							<div class="media-left">
				    								<img src="http://bootdey.com/img/Content/avatar/avatar2.png" class="img-circle img-sm" alt="Profile Picture">
				    							</div>
				    							<div class="media-body pad-hor">
				    								<div class="speech">
				    									<a href="#" class="media-heading">Lucy Doe</a>
				    									<p>Hi, I want to buy a new shoes.</p>
				    									<p class="speech-time">
				    										<i class="fa fa-clock-o fa-fw"></i> 09:23AM
				    									</p>
				    								</div>
				    							</div>
				    						</li>
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