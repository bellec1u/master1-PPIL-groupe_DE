
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title>Basic ePubJS Example</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width">
    <meta name="apple-mobile-web-app-capable" content="yes">
    {!! Html::script('epub/reader/js/libs/zip.min.js') !!}
    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/normalize.css')}}">
    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/main.css')}}">
    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/popup.css')}}">
    {!! Html::script('epub/build/epub.js') !!}
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>


    {!! Html::script('epub/hooks/default/smartimages.js') !!}




    {!! Html::script('epub/reader/js/libs/jquery.min.js') !!}
    {!! Html::script('epub/reader/js/libs/zip.min.js') !!}
<!-- File Storage -->
    <!-- <script src="js/libs/localforage.min.js"></script> -->

    <!-- Full Screen -->
    {!! Html::script('epub/reader/js/libs/screenfull.min.js') !!}

<!-- Render -->
    {!! Html::script('epub/reader/js/epub.min.js') !!}

<!-- Hooks -->
    {!! Html::script('epub/reader/js/hooks.min.js') !!}

<!-- Reader -->
    {!! Html::script('epub/reader/js/reader.js') !!}
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
    <!-- Plugins -->
    <!-- <script src="js/plugins/search.js"></script> -->

    <!-- Highlights -->
    <!-- <script src="js/libs/jquery.highlight.js"></script> -->
    <!-- <script src="js/hooks/extensions/highlight.js"></script> -->


    <script>
        EPUBJS.filePath = "{{ URL('epub/reader/js/libs/')}}";
        EPUBJS.cssPath = "{{ URL('epubreader/css/')}}";
    </script>

   <style type="text/css">
        body {
        }
        #main {
            position: absolute;
            width: 100%;
            height: 100%;
            /* overflow: hidden; */
        }
        #area {
            width: 80%;
            height: 80%;
            margin: 5% auto;
            max-width: 1250px;
        }
        #area iframe {
            border: none;
        }
        #prev {
            left: 40px;
        }
        #next {
            right: 40px;
        }
        .arrow {
            position: absolute;
            top: 50%;
            margin-top: -32px;
            font-size: 64px;
            color: #E2E2E2;
            font-family: arial, sans-serif;
            font-weight: bold;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            user-select: none;
        }
        .arrow:hover {
            color: #777;
        }
        .arrow:active {
            color: #000;
        }
        #controls {
            position: absolute;
            bottom: 16px;
            left: 50%;
            width: 400px;
            margin-left: -200px;
            text-align: center;
            display: none;
        }
        #controls > input[type=range] {
            width: 400px;
        }
    </style>

    <script>
        "use strict";
        var book = ePub("{!! URL::asset('Books/Book'.$id_book.'.epub') !!}", { width: 1076, height: 588 });
    </script>
</head>
<body>

<div id="sidebar">
    <div id="panels">
        <input id="searchBox" placeholder="search" type="search">

        <a id="show-Search" class="show_view icon-search" data-view="Search">Search</a>
        <a id="show-Toc" class="show_view icon-list-1 active" data-view="Toc">TOC</a>
        <a id="show-Bookmarks" class="show_view icon-bookmark" data-view="Bookmarks">Bookmarks</a>
        <a id="show-Notes" class="show_view icon-edit" data-view="Notes">Notes</a>


    </div>
    </div>


<div id="main">
    <div id="title-controls">
        <a href="<?php URL::route('addBookmarks', array('idBook'=>$id_book, 'input'=>'tt'));?>" class="btn btn-primary">Ouvrir</a>
        <button href="{{URL::route('addBookmarks', array('idBook'=>$id_book, 'input'=>'<script>book.getCurrentLocationCfi()</script>'))}}"> clique</button>
        <a href="#" onclick="test()"><img src="{{ URL::asset('bookmarks.png')}}"></a>

        <a href="{{ $_GET['path'] }}"> <img src="{{ URL::asset('fermer.png')}}"></a>

    </div>
    <div id="prev" onclick="book.prevPage();" class="arrow">‹</div>
    <div id="area"></div>
    <div id="next" onclick="book.nextPage();" class="arrow">›</div>
    <div id="controls">
        <input id="current-percent" size="3" value="0" /> %
    </div>
</div>

<script>

    function test(){

        var bookPath = book.getCurrentLocationCfi();

        $.ajax({
            type: 'GET',
            url: '<?php echo  URL::route('addBookmarks', array('idBook'=>$id_book)); ?>',
            data: 'path='+ book.getCurrentLocationCfi(),
            time : 5000,
            success: function(data){

            },
            error : function(){
                alert(' Une erreur est survenu lors de l\'enregistrement du marque page');
            }
        });

        // book.gotoCfi("epubcfi(/6/2[coverpage-wrapper]!4/1:0)");

    }

    var controls = document.getElementById("controls");
    var currentPage = document.getElementById("current-percent");
    var slider = document.createElement("input");

    var rendered = book.renderTo("area");

</script>
</body>
</html>
