@extends('layouts.app')

@section('titre')
    Call Me Ishmael | Référencement et Lecture d&#039;Ebooks
@stop

@section('css')
	{!! Html::style('design/css/index.css') !!}
    {!! Html::style('design/css/font-awesome.min.css') !!}
    {!! Html::style('design/css/jcarousel.responsive.css') !!}
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
	 		<div class="row">
	 			<div class="col-lg-8 col-lg-offset-2">
		 			<h2>Tous vos livres.<br/> Partout avec vous.</h2>
		 			<p>CallMeIshmael, c'est la solution pour lire ou et quand vous voulez. Accéder à des centaines de livres depuis votre ordinateur, tablette ou mobile. Vivez et partagez vos livres préférés. </p>
	 			</div>
	 		</div><! --/row -->
	 	</div><! --/container -->
	 </div><! --/headerwrap -->

	 <!-- *****************************************************************************************************************
		 BEST SELLERS
		 ***************************************************************************************************************** -->

	<div id="bestSellers">
		<div class="container">
			<div class="row centered">
			 	<h1 class="titleStyle">Best<span class="sousTitle">Sellers</span></h1>
			</div>
	    </div>
		<div id="carroul">
            <div class="jcarousel-wrapper">
                <div class="jcarousel">
                    <ul>
                     	@foreach($liste as $book)
		                <li>
		                	<a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}">
		                		<img src="{{ $book->cover_url  }}" alt="" />
		                	</a>
		                </li>
		                @endforeach
                    </ul>
                </div>

                <a href="#" class="jcarousel-control-prev">&lsaquo;</a>
                <a href="#" class="jcarousel-control-next">&rsaquo;</a>

                <p class="jcarousel-pagination"></p>
            </div>
        </div>
    </div>

    <!-- *****************************************************************************************************************
	 NEW RELEASE
	 ***************************************************************************************************************** -->

    <div id="titleNewRelease">
    	<div class="container">
			<div class="row centered">
    			<h1 class="titleStyle">New<span class="sousTitle">Release</span></h1>
    		</div>
        </div>
    </div>

    <div id="newRelease">
        <div class="portfolio-centered">
            <div class="recentitems listBook">
            	@foreach($liste as $book)
				<div class="listBook-item graphic-design">
					<div class="he-wrap tpl6">
						<img src="{{ $book->cover_url  }}" alt="">
						<div class="he-view">
							<div class="bg a0" data-animate="fadeIn">
                                <h3 class="a1" data-animate="fadeInDown">{{ $book->title }}</h3>
                                <a href="{{ URL::route('bookReturn', array('id'=>$book->id))}}" class="dmbutton a2" data-animate="fadeInUp">
                                	<i class="fa fa-search"></i></a>
                                <a href="" class="dmbutton a2" data-animate="fadeInUp"><i class="fa fa-link"></i></a>
                        	</div><!-- he bg -->
						</div><!-- he view -->		
					</div><!-- he wrap -->
				</div><!-- end col-12 -->
				@endforeach           
            </div><!-- portfolio -->
        </div><!-- portfolio container -->
	</div><!--/Portfoliowrap -->

	@yield('footer')

	@section('javascript')
		{!! HTML::script('design/js/jquery.hoverex.min.js'); !!}
        {!! HTML::script('design/js/jquery.isotope.min.js'); !!}
        {!! HTML::script('design/js/jquery.jcarousel.min.js'); !!}
        {!! HTML::script('design/js/jcarousel.responsive.js'); !!}
        {!! HTML::script('design/js/configRun.js'); !!}
	@stop

@stop

