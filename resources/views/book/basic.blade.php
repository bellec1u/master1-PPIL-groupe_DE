
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
        #fixe-haut
        {

            height          : 70px;
            position        : fixed;
            top             : 5px;
            width           : 100%;
            right            : 10px;
        }
    </style>

    <script>
        "use strict";
        var book = ePub("{!! URL::asset('Books/Book'.$id_book.'.epub') !!}", { width: 1076, height: 588 });
    </script>

</head>
<body>



<div id="main">

    <div id="fixe-haut">
       <div align="center"><a href="#" align="center">{{$book->title}}</a></div>
        <div id="title-controls">

            <a href="#" onclick="test(document.images['exemple'])"><img src="{{ URL::asset('bookmark-empty.png')}}" name="exemple" ></a>

            <a href="{{ $_GET['path'] }}"> <img src="{{ URL::asset('fermer.png')}}"></a>

        </div>

    </div>
    <!--<a href="#" align="center">/a>-->


    <div id="prev" onclick="  isbookMark('prev');" class="arrow">‹</div>
    <div id="area"></div>
    <div id="next" onclick="isbookMark('next');" class="arrow">›</div>
    <div id="controls">
        <input id="current-percent" size="3" value="0" /> %
    </div>
</div>

<script>
    var full = true;
    function test(img){

        var bookPath = book.getCurrentLocationCfi();

        $.ajax({
            type: 'GET',
            url: '<?php echo  URL::route('addBookmarks', array('idBook'=>$book->id)); ?>',
            data: 'path='+ book.getCurrentLocationCfi(),
            time : 5000,
            success: function(data){

            },
            error : function(){
                alert(' Une erreur est survenu lors de l\'enregistrement du marque page');
            }
        });
        if(full){
            img.src="{{ URL::asset('bookmark-empty.png')}}";
            full = false;
        }
        else{
            img.src="{{ URL::asset('bookmark-full.png')}}";
            full = true;
        }

        // book.gotoCfi("epubcfi(/6/2[coverpage-wrapper]!4/1:0)");

    }

    function isbookMark( prevOrnext){
        if(prevOrnext == 'next'){
            book.nextPage();
        }
        if(prevOrnext == 'prev'){
            book.prevPage();
        }


        $.ajax({
            type: 'GET',
            url: '<?php echo  URL::route('isBookmarks', array('idBook'=>$book->id)); ?>',
            data: 'bookMark='+ book.getCurrentLocationCfi(),
            time : 5000,
            datatype : 'json',
            success: function(datas){
                if(datas.datas == false){
                    document.images['exemple'].src="{{ URL::asset('bookmark-empty.png')}}";
                    full = false;
                }
                else{
                    document.images['exemple'].src="{{ URL::asset('bookmark-full.png')}}";
                    full = true;
                }

            },
            error : function(){
                alert(' Une erreur est survenu lors de l\'enregistrement du marque page');
            }
        });
    }

    var controls = document.getElementById("controls");
    var currentPage = document.getElementById("current-percent");
    var slider = document.createElement("input");

    var rendered = book.renderTo("area");

</script>
@if($bookmark != null)

    <script>    book.gotoCfi("<?php echo $bookmark->page; ?>");
        document.images['exemple'].src="{{ URL::asset('bookmark-full.png')}}";
        full = true;
    </script>

@endif
</body>
</html>
