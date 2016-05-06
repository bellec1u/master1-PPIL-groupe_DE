<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateReseauSocialTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
          Schema::create('reseauSocial', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idClient')->references('id')->on('client')->onDelete('cascade');
        $table->string('reseauSocial');
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
         Schema::drop('reseauSocial');
    }
}
