<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateBooksTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('books', function (Blueprint $table) {
            $table->increments('id');
            $table->string('title', 512);
            $table->string('author');
            $table->date('publication_date');
            $table->longText('resume')->nullable();
            $table->string('genre');
            $table->string('language');
            $table->integer('stars_average')->default(0);
            $table->string('url');
            $table->string('cover_url')->nullable();
            $table->integer('page_number')->default(0);
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('books');
    }
}
