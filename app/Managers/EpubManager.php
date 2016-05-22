<?php

namespace App\Managers;
use Storage;

class EpubManager
{
    public function __construct()
    {

    }

    function get_session_id($book_id) {
        $ch = curl_init(); // create cURL handle (ch)
        if (!$ch) {
            die("Couldn't initialize a cURL handle");
        }
        // set some cURL options
        $ret = curl_setopt($ch, CURLOPT_URL, "http://www.gutenberg.org/ebooks/".$book_id);
        $ret = curl_setopt($ch, CURLOPT_HEADER,         1);
        $ret = curl_setopt($ch, CURLOPT_FOLLOWLOCATION, 1);
        $ret = curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
        $ret = curl_setopt($ch, CURLOPT_TIMEOUT,        30);

        // execute
        $ret = curl_exec($ch);

        if (empty($ret)) {
            // error encountered
            curl_close($ch);
            die(curl_error($ch));
        } else {
            // retrieve header
            $header_size = curl_getinfo($ch, CURLINFO_HEADER_SIZE);
            $header = substr($ret, 0, $header_size);
            $info = curl_getinfo($ch, CURLINFO_EFFECTIVE_URL);

            // extract cookie
            $res = preg_match('/Set-Cookie: session_id=([a-z0-9]+);/', $header, $cookie);
            //print($header);
            $session_id = '';
            if ($res) {
                $session_id = $cookie[1];
                // store session id on disk
                Storage::put('session_id', $session_id);
            }

            Storage::append('download.log', 'session_id get : '.$info);

            curl_close($ch);
            return $session_id;
        }
    }



    function download_book($book_id) {
        //This is the file where we save the    information
        $file_path = public_path().'\Books\Book'.$book_id.'.epub';
        $fp = fopen($file_path, 'w+');

        $session_id = $this->get_session_id($book_id);
//        if (!Storage::exists('session_id')) {
//            $session_id = $this->get_session_id($book_id);
//        } else {
//            $session_id = Storage::get('session_id');
//        }

        $ch = curl_init();
        if (!$ch) {
            die("Couldn't initialize a cURL handle");
        }

        $base_url = 'http://www.gutenberg.org/cache/epub/'.$book_id.'/';
        $url = $base_url.'pg'.$book_id.'-images.epub?session_id='.$session_id;
        $ret = curl_setopt($ch, CURLOPT_URL, $url);
        $ret = curl_setopt($ch, CURLOPT_TIMEOUT, 60);
        // write curl response to file
        $ret = curl_setopt($ch, CURLOPT_FILE, $fp);
        $ret = curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
        $ret = curl_setopt($ch, CURLOPT_HTTPHEADER, [
            'Referer: http://www.gutenberg.org/',
            'Cookie: session_id='.$session_id]);
        // get curl response
        $ret = curl_exec($ch);
        $info = curl_getinfo($ch, CURLINFO_EFFECTIVE_URL);

        if (empty($ret)) {
            print(curl_error($ch));
        }

        Storage::append('download.log', 'book download : '.$info);

        curl_close($ch);
        fclose($fp);
    }
}