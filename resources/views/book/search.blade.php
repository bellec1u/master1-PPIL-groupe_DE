@extends('layouts.app')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('css')
	{!! Html::style('design/css/comment.css') !!}
	{!! Html::style('design/css/jcarousel.responsive.css') !!}
	{!! Html::style('design/css/search.css') !!}
	{!! Html::style('design/css/navigation.css') !!}
    {!! Html::style('design/css/font-awesome.min.css') !!}
@stop

@include('navigation')

@include('footer')

@section('content')

	@yield('navigation')

    <!-- *****************************************************************************************************************
	 HEADER
	***************************************************************************************************************** -->

    <div id="headerwrap">
	 	<div class="container centered">
	 		<div class="row cadreSearch">
	 			<h1 class="titleStyle">Rechercher un livre</h1>
	 			<div class="row">
	                <div class="col-sm-10 col-md-offset-1">
	                    {!! Form::open(array('route'=>'bookSearch', 'method'=>'GET')) !!}
	                        <div class="form-group row">
	                            <div class="input-group col-sm-12 searchPrincipal">
	                                <input type="text" class="form-control " value="{{ old('query') }}" name="query" placeholder="Rechercher un Ebook..." />
	                                <div class="input-group-btn">
	                                    <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-search"></span></button>
	                                </div>
	                            </div>
	                        </div>
	                        <div class="form-group row">
	                            <div class="col-sm-8">
	                                {!! Form::text('author', old('author'), ['class' => 'form-control', 'placeholder' => 'Auteur']) !!}
	                            </div>
	                            <div class="form-group form-horizontal">
	                            <label for="lang" class="col-sm-2 control-label">Langue : </label>
	                            <div class="col-sm-2">
	                                {!! Form::select('lang', $langs, old('lang'), ['class' => 'form-control', 'id' => 'lang']) !!}
	                            </div>
	                            </div>
	                        </div>
	                        <div class="form-group row">
	                            <div class="col-sm-12">
	                                {!! Form::text('genre', old('genre'), ['class' => 'form-control', 'placeholder' => 'Genre']) !!}
	                            </div>
	                        </div>
	                    {!! Form::close() !!}
	                </div>
	            </div>
	 		</div><! --/row -->
	 	</div><! --/container -->
	 </div><! --/headerwrap -->


    <!-- *****************************************************************************************************************
	 Resultat
	 ***************************************************************************************************************** -->

	
    <div id="newRelease">
		{!! $books->appends(Request::except('page'))->links() !!}
        <div class="portfolio-centered">
            <div class="recentitems listBook">
            	@foreach($books as $book)
				<div class="listBook-item graphic-design">
					<div class="he-wrap tpl6">
						<img src="{{ $book->cover_url  }}" alt="">
						<div class="he-view">
							<div class="bg a0" data-animate="fadeIn">
                                <h3 class="a1" data-animate="fadeInDown">{{ $book->title }}</h3>
                                <a href="{{ URL::route('bookReturn', array('id'=>$book->book_id))}}" class="dmbutton a2" data-animate="fadeInUp">
                                	<i class="fa fa-search"></i></a>
                                <a href="" class="dmbutton a2 lookNote" data-animate="fadeInUp"><i class="fa fa-book"></i></a>
                        	</div><!-- he bg -->
						</div><!-- he view -->		
					</div><!-- he wrap -->
				</div><!-- end col-12 -->
				@endforeach           
            </div><!-- portfolio -->
        </div><!-- portfolio container -->
        {!! $books->appends(Request::except('page'))->links() !!}
	</div><!--/Portfoliowrap -->

	@yield('footer')

	@section('javascript')
		{!! HTML::script('design/js/jquery.hoverex.min.js'); !!}
        {!! HTML::script('design/js/jquery.isotope.min.js'); !!}
        {!! HTML::script('design/js/configRun.js'); !!}
	@stop

@stop

