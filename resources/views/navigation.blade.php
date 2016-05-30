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
            <a class="navbar-brand " href="{{ url('/') }}">CALLMEISMAEL</a>
        </div>
        <div id="testMenu" class="navbar-collapse collapse">
            <ul class="nav navbar-nav  menuCenter">
                <li><a href="#">FAQ</a></li>
                <li><a href="#"><img src="{{url('design/img/icon-search.png')}}" alt="picture"></a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right  menuRight">
                @if(Auth::check())
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><span class="glyphicon glyphicon-user"></span>Utilisateur<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="{{url('user/profile')}}"><span class="glyphicon glyphicon-cog"></span> Profil</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="{{URL::route('showReading', array())}}"><span class="glyphicon glyphicon-book"></span> Liste de Lectures</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="{{URL::route('ConsultFollower', array())}}"><span class="glyphicon glyphicon-user"></span> Membres Suivis</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="{{ url('logout') }}"><span class="glyphicon glyphicon-off"></span> Déconnexion</a></li>
                        </ul>
                    </li>
                @else
                    <li><a href="{{ url('/login') }}">Connexion</a></li>
                    <li><a href="{{ url('/register') }}">Inscription</a></li>
                @endif
            </ul>
        </div><!--/.nav-collapse -->
    </div>
    @if (session('status'))
        <div class="alert alert-success text-center " id="messageAlert">
            <a href="#" title="Fermer" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            {{ session('status') }}
        </div>
    @endif
    @if (session('statusInvalidMail'))
        <div class="alert alert-danger text-center " id="messageAlert">
            <a href="#" title="Fermer" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            Veuillez valider votre mail avant de vous connecter. Vérifiez votre boîte mail.  
            <a href="{{ route('resendEmail', session('statusInvalidMail')) }}" class="btn btn-link btnRenvoiMail">
                <span class="glyphicon glyphicon-send"></span> Renvoyer le mail de validation
            </a>
        </div>
    @endif
</div>

@stop

