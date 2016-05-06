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
        $table->integer('idLivre')->unsigned();
        $table->integer('idClient')->unsigned();
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
        Schema::table('signet', function(Blueprint $table) {
            $table->dropForeign('signet_idLivre_foreign');
            $table->dropForeign('signet_idClient_foreign');
        });

        Schema::drop('signet');
    }
}
