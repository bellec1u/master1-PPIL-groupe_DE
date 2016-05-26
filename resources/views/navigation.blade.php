@section('navigation') 

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Menu</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand " href="index.php">CALLMEISMAEL</a>
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav  menuCenter">
                        @if(Auth::check())
                            <li class="active"><a href="#">LISTE DE LECTURE</a></li>
                        @endif
                        <li><a href="#">FAQ</a></li>
                        <!--<li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">PARAMETRE<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="">BLOG</a></li>
                                <li><a href="">666666666666666666</a></li>
                                <li><a href="">6666666666666</a></li>
                                <li><a href="">6666666666666666T</a></li>
                            </ul>
                        </li>-->
                        <li><a href="#"><img src="{{url('design/img/icon-search.png')}}" alt="picture"></a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right  menuRight">
                        @if(Auth::check())
                            <li><a href="{{ url('logout') }}">Deconnexion</a></li>
                        @else
                            <li><a href="{{ url('/login') }}">Connexion</a></li>
                            <li><a href="{{ url('/register') }}">Inscription</a></li>
                        @endif
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
            @if (session('status'))
                <div class="alert alert-success text-center " id="messageAlert" style="margin-bottom: -10px;">
                    <a href="#" title="Fermer" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                    {{ session('status') }}
                </div>
            @endif
        </div>

@stop