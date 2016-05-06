<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateCheminAccesTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
         Schema::create('CheminAcces', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idClient')->references('id')->on('client')->onDelete('cascade');
        $table->integer('idLivre')->references('id')->on('livre');
        $table->string('url');
         $table->string('device');
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
         Schema::table('CheminAcces', function(Blueprint $table) {
            $table->dropForeign('CheminAcces_idLivre_foreign');
        });
        Schema::table('lecture', function(Blueprint $table) {
            $table->dropForeign('CheminAcces_idClient_foreign');
        });
         Schema::drop('CheminAcces');
    }
}
