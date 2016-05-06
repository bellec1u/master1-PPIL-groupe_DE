<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAbonnementTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
         Schema::create('abonnement', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idClientSuiveur')->unsigned();
        $table->integer('idClientSuivi')->unsigned();
        $table->boolean('notification');
        $table->timestamps();
        $table->foreign('idClientSuiveur')
                ->references('id')
                ->on('client')
                ->onDelete('cascade'); 
        $table->foreign('idClientSuivi')
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
        
        Schema::table('abonnement', function(Blueprint $table) {
            $table->dropForeign('abonnement_idClientSuiveur_foreign');
            $table->dropForeign('abonnement_idClientSuivi_foreign');
        });
        
        Schema::drop('abonnement');
    }
}
