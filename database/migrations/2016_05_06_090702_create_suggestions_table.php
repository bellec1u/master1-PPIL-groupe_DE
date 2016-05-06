<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSuggestionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
          Schema::create('suggestion', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idClient')->references('id')->on('client')->onDelete('cascade');
        $table->integer('idLivre')->references('id')->on('livre');
        $table->timestamps();

    }); 
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('suggestion');
    }
}
