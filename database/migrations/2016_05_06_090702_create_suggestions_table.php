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
        $table->integer('idClient')->unsigned();
        $table->integer('idLivre')->unsigned();
        $table->timestamps();

        $table->foreign('idClient')
              ->references('id')
              ->on('client')
              ->onDelete('cascade');
        
        $table->foreign('idLivre')
              ->references('id')
              ->on('livre')
              ->onDelete('cascade');

    }); 
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('suggestion', function(Blueprint $table) {
            $table->dropForeign('suggestion_idClient_foreign');
            $table->dropForeign('suggestion_idLivre_foreign');
        });

        Schema::drop('suggestion');
    }
}
