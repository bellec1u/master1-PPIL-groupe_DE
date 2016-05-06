<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateEvaluationTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
         Schema::create('evaluation', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idLivre')->unsigned();
        $table->integer('idClient')->unsigned();
        $table->longText('commentaire');
        $table->integer('note');
       
        $table->timestamps();
         $table->foreign('idLivre')
                ->references('id')
                ->on('livre')
                ->onDelete('cascade'); 
         $table->foreign('idClient')
                ->references('id')
                ->on('client')
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
        Schema::table('evaluation', function(Blueprint $table) {
            $table->dropForeign('evaluation_idLivre_foreign');
            $table->dropForeign('evaluation_idClient_foreign');
        });
       
        Schema::drop('evaluation');
    }
}
