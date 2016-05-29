
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




    {!! Html::script('screenfull/src/screenfull.js') !!}
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

        #container {
            width: 500px;
            padding: 30px 20px;
            margin: 0 auto 50px auto;
            background: #fcfcfc;
            text-align: center;
            border: 1px solid #b3b3b3;
            border-radius: 4px;
            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;
        }

        #container ul {
            padding: 0;
            margin: 40px 0 0 0;
            list-style: none;
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
            <table>
                <tr>
                    <td id="Mark"><a href="#" onclick="goToMark()">Acceder au marque page</a></td>
                   <td><a href="#" onclick="test(document.images['exemple'])"><img src="{{ URL::asset('bookmark-empty.png')}}" name="exemple" ></a></td>
                   <td><a href="#" id="request"><img src="{{ URL::asset('fullscreen.png')}}" name="fullscreen" ></a></td>

                   <td><a href="{{ $_GET['path'] }}"> <img src="{{ URL::asset('fermer.png')}}"></a></td>
                </tr>
            </table>
            <div id="Mark"></div>


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
        if(full == true){
           document.getElementById('Mark').style.visibility = 'hidden';
        }
        else{
            document.getElementById('Mark').style.visibility = 'visible';
            MarkPage = book.getCurrentLocationCfi();
        }
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

    function goToMark(){
        book.gotoCfi(MarkPage);
        document.images['exemple'].src="{{ URL::asset('bookmark-full.png')}}";
        full = true;
    }
    function isbookMark( prevOrnext){
        if(prevOrnext == 'next'){
            book.nextPage();
        }
        else{
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
    var fullscreen = false;
    $('#request').click(function () {
        if(fullscreen == false){
            screenfull.request($('#container')[0]);
            fullscreen = true;

            document.images['fullscreen'].src="{{ URL::asset('exitFullscreen.png')}}";
        }
        else{
            screenfull.exit();
            fullscreen = false;
            document.images['fullscreen'].src="{{ URL::asset('fullscreen.png')}}";
        }

// does not require jQuery, can be used like this too:
// screenfull.request(document.getElementById('container'));
    });
    $('#exit').click(function () {
        screenfull.exit();
    });
</script>

@if($bookmark != null)

    <script>    book.gotoCfi("<?php echo $bookmark->page; ?>");
    document.images['exemple'].src="{{ URL::asset('bookmark-full.png')}}";
    full = true;
        var MarkPage = "{{$bookmark->page}}";

    </script>
    @else
    <script>    document.getElementById('Mark').style.visibility = 'hidden';</script>
@endif
</body>
</html>
