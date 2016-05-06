<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSignetTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
          Schema::create('signet', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idLivre')->references('id')->on('livre')->onDelete('cascade');
        $table->integer('idClient')->references('id')->on('client')->onDelete('cascade');
        $table->integer('page');
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
           Schema::table('annotation', function(Blueprint $table) {
            $table->dropForeign('annotation_idLivre_foreign');
        });
        Schema::table('lecture', function(Blueprint $table) {
            $table->dropForeign('annotation_idClient_foreign');
        });
         Schema::drop('signet');
    }
}
