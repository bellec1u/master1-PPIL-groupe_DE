@extends('layouts.app')

@section('titre')
    Call Me Ishmael
@stop

@section('css')
	{!! Html::style('design/css/comment.css') !!}

    {!! Html::style('design/css/jcarousel.responsive.css') !!}
	{!! Html::style('design/css/profile.css') !!}
	{!! Html::style('design/css/navigation.css') !!}
    {!! Html::style('design/css/font-awesome.min.css') !!}

@stop

@include('navigation')

@include('footer')


<!-- *****************************************************************************************************************
 BOOK
 ***************************************************************************************************************** -->

@section('content')

	@yield('navigation')


    <div id="detailProfile" class="detailProfile">
		<div class="container">
			@if($user->following_allowed == true || Auth::user()->id == $user->id)
			 	<div id="headerwrap">
				 	<div class="row detailGroupProfile col-sm-12">
					 	<div class="detailProfileImg col-sm-12" id="detailProfileImg">
							<img src="{{ URL($user->profile_image) }}" class="img-circle" alt="">
					 	</div>
					 	{!! Form::open(array('route'=>'userUpdate', 'method'=>'PUT', 'files'=>'true')) !!}
					 	<div class="descriptionProfile col-sm-5" id="descriptionProfile">
					 		<p><b>Nom :</b> {{ $user->first_name }}</p>
					 		<p><b>Email :</b> {{ $user->email}}</p>
					 		<p><b>Date de naissance :</b> {{ $user->birth_date}}</p>
					 		<p><b>Etat du profil :</b>  
	                        @if($user->following_allowed)
	                            publique
	                        @else 
	                            privé 
	                        @endif </p>
					 	</div>
					 	<div class="descriptionProfile col-sm-5" id="descriptionProfileRight"></div>
					 	{!! Form::close() !!}
					 	<div class="descriptionProfileBtn col-sm-2" id="descriptionProfileBtn">
					 		@if(Auth::user()->id == $user->id)
		                        <a href="#" class="btn btn-primary" id="clickProfilBt" onclick="clikEditProfile()">Modifier</a>
		                        @if($user->following_allowed)
		                            {{ Form::open(array('route' => 'following_allowed', 'method' => 'post', 'name'=>'following_allowed')) }}
		                            {!! Form::submit('Rendre profil privé', ['class' => 'btn btn-warning']) !!}
		                            {{ Form::close()}} 
		                        @else
		                            {{ Form::open(array('route' => 'following_allowed', 'method' => 'post', 'name'=>'following_allowed')) }}
		                            {!! Form::submit('Rendre profil publique', ['class' => 'btn btn-success']) !!}
		                            {{ Form::close()}}
		                        @endif
		                        {{ Form::open(array('route' => 'userDelete', 'method' => 'delete', 'name'=>'desinscrire')) }}
		                        {!! Form::button('Désinscription', ['class' => 'btn btn-danger', 'onclick'=>"Desinscription()"]) !!}
		                        {{ Form::close()}}
		                    @else	
								@if(!$estSuivi)
			                        {!! Form::open(array('route'=>'addFollower', 'method'=>'POST')) !!}
			                        {{ Form::hidden("followed_user_id",$user->id ) }}

			                        {!! Form::submit('Suivre', ['class' => 'btn btn-success pull-right']) !!}
			                        {!! Form::close() !!}

			                    @else
			                        {{ Form::open(array('route' => array('deleteFollower', 'id'=>$idFollower), 'method' => 'delete', 'name'=>'desinscrire')) }}

			                        {!! Form::submit('Ne plus suivre', ['class'=>"btn btn-danger pull-right"]) !!}
			                    @endif
		                    @endif
					 	</div>
					 	
				 	</div>
				</div> 
				<div id="titleNewRelease">
			    	<div class="container">
						<div class="row centered">
			    			<h1 class="titleStyle">Liste<span class="sousTitle">de Lecture</span></h1>
			    		</div>
			        </div>
			    </div>

			    <div id="newRelease">
			        <div class="portfolio-centered">
			            <div class="recentitems listBook">
			            	@foreach($user->readings as $reading)
							<div class="listBook-item graphic-design">
								<div class="he-wrap tpl6">
									<img src="{{ $reading->book->cover_url  }}" alt="">
									<div class="he-view">
										<div class="bg a0" data-animate="fadeIn">
			                                <h3 class="a1" data-animate="fadeInDown">{{ $reading->book->title }}</h3>
			                                <a href="{{ URL::route('bookReturn', array('id'=>$reading->book_id))}}" class="dmbutton a2" data-animate="fadeInUp">
			                                	<i class="fa fa-search"></i></a>
			                                <a href="" class="dmbutton a2 lookNote" data-animate="fadeInUp"><i class="fa fa-book"></i></a>
			                                @if(Auth::user()->id == $user->id)
			                                	{{ Form::open(array('route' => array('deleteReading', 'id'=>$reading->book_id), 'method' => 'get', 'name'=>'desinscrireLivre', 'style' => 'display:inline')) }}
			                                	<a href="" class="dmbutton a2 delNote" id="Supprimer" data-animate="fadeInUp" onclick="DesinscriptionLivre()"><i class="glyphicon glyphicon-remove"></i></a>
			                                	{{ Form::close()}}
			                                @endif
			                        	</div><!-- he bg -->
									</div><!-- he view -->		
								</div><!-- he wrap -->
							</div><!-- end col-12 -->
							@endforeach           
			            </div><!-- portfolio -->
			        </div><!-- portfolio container -->
				</div><!--/Portfoliowrap -->

				<div id="titleDetailComment">
			    	<div class="container">
						<div class="row centered col-sm-12 ">
			    			<h1 class="titleStyle">Dernières<span class="sousTitle">Evaluations</span></h1>
			    		</div>
			        </div>
			    </div>
				@if(Auth::user()->id != $user->id)
				    <div id="detailBookComment">
						<div class="row centered contenuComm col-sm-12" id="test">
							<div class="col-sm-12">
						        <div class="panel">
						    		<!--Widget body-->
						    		<div id="demo-chat-body" class="collapse in">
						    			<div class="nano has-scrollbar">
						    				<div class="nano-content pad-all" tabindex="0" style="right: -17px;">
						    					<ul class="list-unstyled media-block">
						    						@foreach($user->ratings as $rating)
			    									<li class="mar-btm">
			    										<div class="media-left">
			    											<a href="{{ URL::route('bookReturn', array('id'=>$rating->book_id))}}">
			    												<img src="{{ URL($rating->book->cover_url) }}" class="img-sm" alt="Profile Picture">
			    											</a>
			    										</div>
				    									<div class="media-body pad-hor">
					    									<div class="speech">
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
				@endif
			</div><! --/container -->
		</div>
    @else
        <div id="privateProfile">
			<div class="container">
				<div class="row centered">
        			<h1 align="center"></h1>
        		</div>
			</div
        </div>
    @endif

	@yield('footer')

	@section('javascript')
        {!! HTML::script('design/js/jquery-birthday-picker.js'); !!}
        {!! HTML::script('design/js/jquery.hoverex.min.js'); !!}
        {!! HTML::script('design/js/jquery.isotope.min.js'); !!}
        {!! HTML::script('design/js/configRun.js'); !!}
        <script type="text/javascript">
            function clikEditProfile(){
            	$("#detailProfileImg").attr('class', 'detailProfileImg col-sm-12 img-sm');
            	$("#descriptionProfile").replaceWith(
            		'<div class=" col-sm-6" id="descriptionProfile">'+
            			"<div class=\"form-group {!! $errors->has('email') ? 'has-error' : '' !!}\">"+
		            		'<p><b>Adresse mail :</b>'+ 
						 		'<input class="form-control" name="email" type="email" value={{$user->email}} required>'+
		                        "{!! $errors->first('email', '<small class=\"help-block\">:message</small>') !!}"+
		                    '</p>'+
	                    '</div>'+
	                    "<div class=\"form-group {!! $errors->has('email_confirmation') ? 'has-error' : '' !!}\">"+
					 		"<p><b>Confirmation de l'Adresse mail :</b>"+
					 			'<input class="form-control" name="email_confirmation" type="email" value={{$user->email}} required>'+
		                    	"{!! $errors->first('email_confirmation', '<small class=\"help-block\">:message</small>') !!}"+
					 		'</p>'+
					 	'</div>'+
	                    "<div class=\"form-group {!! $errors->has('password') ? 'has-error' : '' !!}\">"+
					 		'<p><b>Mot de passe :</b>'+
					 			'<input class="form-control" name="password" type="password" placeholder="8 caractères minmum..." required>'+
		                    	"{!! $errors->first('password', '<small class=\"help-block\">:message</small>') !!}"+
					 		'</p>'+
					 	'</div>'+
	                    "<div class=\"form-group\">"+
					 		'<p><b>Confirmation du Mot de passe :</b>'+
					 			'<input class="form-control" name="password_confirmation" type="password" required>'+
		                    	"{!! $errors->first('password_confirmation', '<small class=\"help-block\">:message</small>') !!}"+
					 		'</p>'+
					 	'</div>'+
					'</div>'
				);

				$("#descriptionProfileRight").replaceWith(
					'<div class=" col-sm-6" id="descriptionProfileRight">'+
	                    "<div class=\"form-group {!! $errors->has('first_name') ? 'has-error' : '' !!}\">"+
					 		'<p><b>Prénom :</b>'+ 
						 		'<input class="form-control" name="first_name" type="text" value={{$user->first_name}} required>'+
		                        "{!! $errors->first('first_name', '<small class=\"help-block\">:message</small>') !!}"+
		                    '</p>'+
		                '</div>'+
	                    "<div class=\"form-group {!! $errors->has('last_name') ? 'has-error' : '' !!}\">"+
					 		'<p><b>Nom :</b>'+ 
						 		'<input class="form-control" name="last_name" type="text" value={{$user->last_name}} required>'+
		                        "{!! $errors->first('last_name', '<small class=\"help-block\">:message</small>') !!}"+
		                    '</p>'+
		                '</div>'+
	                    "<div class=\"form-group {!! $errors->has('birth_date') ? 'has-error' : '' !!}\">"+
		                    '<div class="profilLeftDate col-sm-7">'+
							 	'<p><b>Date de naissance :</b></p>'+
							 	'<div id="birthdayPicker"></div>'+
							 	"{!! $errors->first('birth_date', '<small class=\"help-block\">:message</small>') !!}"+
						 	'</div>'+
						'</div>'+
	                    "<div class=\"form-group {!! $errors->has('sex') ? 'has-error' : '' !!}\">"+
						 	'<div class="profilRightGenre col-sm-5">'+
							 	'<p><b>Genre :</b></p>'+
							 	'<div class="radio radio-primary radio-inline">'+
		                            '<input type="radio" id="inlineRadio1" value="H" name="sex" checked="">'+
		                            '<label for="inlineRadio1"> Homme </label>'+
		                        '</div>'+
		                        '<div class="radio radio-danger radio-inline">'+
		                            '<input type="radio" id="inlineRadio2" value="F" name="sex">'+
		                            '<label for="inlineRadio2"> Femme </label>'+
		                        '</div>'+
	                        '</div>'+
	                    '</div>'+
	                    
                        '<button class=" col-sm-12 btn btn-large btn-blue btn-success" type="submit">Modification</button>'+
                	'</div>'+
                	'<div class="input-group input-file col-sm-12" style="height:20px;">'+
                        '<input type="text" class="form-control" placeholder="Choisir une photo de profile..." />'+
                        '<span class="input-group-btn ">'+
                            '<button class="btn btn-primary btn-choose" type="button">'+
                                '<span class="glyphicon glyphicon-picture"></span>'+
                            '</button>'+
                        '</span>'+
                    '</div>'
				);
				$("#descriptionProfileBtn").remove();
				$(document).ready(function() {
                	$("#birthdayPicker").birthdayPicker();
            	});
            	$("#birthDay").val('{{$user->birth_date}}');

            	function bs_input_file() {
	                $(".input-file").before(
	                    function() {
	                        if ( ! $(this).prev().hasClass('input-ghost') ) {
	                            var element = $("<input type='file' class='input-ghost' name='profile_image' style='visibility:hidden; height:0'>");
	                            element.attr("name",$(this).attr("name"));
	                            element.change(function(){
	                                element.next(element).find('input').val((element.val()));
	                            });
	                            $(this).find("button.btn-choose").click(function(){
	                                element.click();
	                            });
	                            $(this).find("button.btn-reset").click(function(){
	                                element.val(null);
	                                $(this).parents(".input-file").find('input').val('');
	                            });
	                            $(this).find('input').css("cursor","pointer");
	                            $(this).find('input').mousedown(function() {
	                                $(this).parents('.input-file').prev().click();
	                                return false;
	                            });
	                            return element;
	                        }
	                    }
	                );
	            }
	            $(function() {
	                bs_input_file();
	            });
            }

            function Desinscription() {
	            if (confirm ('Etes vous sûr de vouloir vous désinscrire ?')){
	                document.forms["desinscrire"].submit();
	            }
	        }

	        function DesinscriptionLivre() {
	            if (confirm ('Etes vous sûr de vouloir supprimer votre livre de votre bibliothèque personnel ?')){
	                document.forms["desinscrireLivre"].submit();
	            }
	        }

        </script>
        <?php 
		 	if($errors->first('email') != "" || $errors->first('email_confirmation') != "" || $errors->first('password') != "" || $errors->first('password_confirmation') != "" || $errors->first('first_name') != "" || $errors->first('last_name') != "" || $errors->first('birth_date') != "" ){ echo "<script>clikEditProfile();</script>";}
		?>
    @stop

@stop