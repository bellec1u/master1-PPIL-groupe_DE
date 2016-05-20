<!DOCTYPE html>
<html class="no-js">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title></title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/normalize.css')}}">
    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/main.css')}}">
    <link rel="stylesheet" href="{{ URL::asset('epub/reader/css/popup.css')}}">



    {!! Html::script('epub/reader/js/libs/jquery.min.js') !!}
    {!! Html::script('epub/reader/js/libs/zip.min.js') !!}


    <script>
        "use strict";
        document.onreadystatechange = function () {
            if (document.readyState == "complete") {
                EPUBJS.filePath = "js/libs/";
                EPUBJS.cssPath = window.location.href.replace(window.location.hash, '').replace('index.html', '') + "css/";
                // fileStorage.filePath = EPUBJS.filePath;
                window.reader = ePubReader("{!! URL::asset('Books/book'.$id_book.'.epub') !!}");
            }
        };
    </script>

    <!-- File Storage -->
    <!-- <script src="js/libs/localforage.min.js"></script> -->

    <!-- Full Screen -->

    {!! Html::script('epub/reader/js/libs/screenfull.min.js') !!}
    <!-- Render -->

    {!! Html::script('epub/reader/js/epub.min.js') !!}
    <!-- Hooks -->

    {!! Html::script('epub/reader/js/hooks.min.js') !!}
    <!-- Reader -->

    {!! Html::script('epub/reader/js/reader.min.js') !!}
    <!-- Plugins -->
    <!-- <script src="js/plugins/search.js"></script> -->

    <!-- Highlights -->
    <!-- <script src="js/libs/jquery.highlight.js"></script> -->
    <!-- <script src="js/hooks/extensions/highlight.js"></script> -->

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

    <div id="tocView" class="view">
    </div>
    <div id="searchView" class="view">
        <ul id="searchResults"></ul>
    </div>
    <div id="bookmarksView" class="view">
        <ul id="bookmarks"></ul>

    </div>
    <div id="notesView" class="view">
        <div id="new-note">
            <textarea id="note-text"></textarea>
            <button id="note-anchor">Anchor</button>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Basic ePubJS Example</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">
        <meta name="apple-mobile-web-app-capable" content="yes">


        <!-- EPUBJS Renderer -->

          {!! Html::script('epub/build/epub.js') !!}
           {!! Html::script('epub/build/libs/zip.min.js') !!}



        <style type="text/css">

          body {
            overflow: hidden;
          }

          #main {
            position: absolute;
            width: 100%;
            height: 100%;
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
        </style>

         <script>
            "use strict";

            var Book = ePub("{!! URL::asset('Books/book'.$id_book.'.epub') !!}",  { width : 400, heigth:600 });

        </script>
    </head>
    <body>
    <a href="{{ $_GET['path'] }}" class="btn btn-primary"> Quitter </a>
        <span class="glyphicon glyphicon-circle-arrow-left"></span>
    </a>
        <div id="main">
          <div id="prev" onclick="Book.prevPage();" class="arrow">‹</div>
            <div id="wrapper">
          <div id="area"></div>
            </div>
          <div id="next" onclick="Book.nextPage();" class="arrow">›</div>

        </div>

        <script>

            Book.renderTo("area");

        </script>
    </body>
</html>
