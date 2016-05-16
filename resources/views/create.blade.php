@extends('templateForm')

@section('contenu')
	<div class="col-sm-offset-4 col-sm-4">
		<br>
		<div class="panel panel-primary">	
			<div class="panel-heading">Création d'un utilisateur</div>
			<div class="panel-body"> 
				<div class="col-sm-12">

					{!! Form::open(array('route'=>'storeUser','method'=>'POST', 'files'=>'true')) !!}
					  {!! csrf_field() !!}
					<div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
						{!! Form::email('email', null, ['class' => 'form-control', 'placeholder' => 'Email']) !!}
						{!! $errors->first('email', '<small class="help-block">:message</small>') !!}
					</div>
                                        <div class="form-group {!! $errors->has('email') ? 'has-error' : '' !!}">
						{!! Form::email('email_confirmation', null, ['class' => 'form-control', 'placeholder' => 'confirmation Email']) !!}
						{!! $errors->first('email', '<small class="help-block">:message</small>') !!}
					</div>
                                        <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
						{!! Form::text('first_name', null, ['class' => 'form-control', 'placeholder' => 'Prenom']) !!}
						{!! $errors->first('name', '<small class="help-block">:message</small>') !!}
					</div>
                                        
                                        <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
						{!! Form::text('last_name', null, ['class' => 'form-control', 'placeholder' => 'Nom']) !!}
						{!! $errors->first('name', '<small class="help-block">:message</small>') !!}
					</div>
					<div class="form-group {!! $errors->has('password') ? 'has-error' : '' !!}">
						{!! Form::password('password', ['class' => 'form-control', 'placeholder' => 'Mot de passe']) !!}
						{!! $errors->first('password', '<small class="help-block">:message</small>') !!}
					</div>
					<div class="form-group">
						{!! Form::password('password_confirmation', ['class' => 'form-control', 'placeholder' => 'Confirmation mot de passe']) !!}
					</div>
                                           <div class="form-group {!! $errors->has('name') ? 'has-error' : '' !!}">
						{!! Form::checkbox('sex', 'F') !!} F
                                                {!! Form::checkbox('sex', 'M') !!} M
						{!! $errors->first('name', '<small class="help-block">:message</small>') !!}
					</div>
					<div class="form-group">
						<div class="date">
							<label>
							{!! Form::date('birth_date',  \Carbon\Carbon::now()) !!} date de naissance	
							</label>
						</div>
					</div>
                                        <div class="form-group">
						<div class="file">
							<label>
							 {!! Form::file('profile_image') !!} image profil
                                                           
							</label>
						</div>
					</div>
					{!! Form::submit('Envoyer', ['class' => 'btn btn-primary pull-right']) !!}
                                      

						<a href="redirect/facebook"> <input type="button" value="FB Login"> </a>
						<?php echo '     OR' ;?>
						<a href="redirect/google"> <input type="button" value="G+ Login"> </a>


					{!! Form::close() !!}
				</div>
			</div>
		</div>
		<!--<a href="javascript:history.back()" class="btn btn-primary">
			<span class="glyphicon glyphicon-circle-arrow-left"></span> Retour-->
		</a>
	</div>
@stop