<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateNotificationTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
         Schema::create('notification', function(Blueprint $table) {

        $table->increments('id');
        $table->integer('idLivre')->unsigned();
        $table->integer('idClientNotifieur')->unsigned();
        $table->integer('idClientNotifie')->unsigned();
        $table->string('type');
        $table->text('details');
        $table->timestamps();
       
        $table->foreign('idClientNotifieur')
                ->references('id')
                ->on('client')
                ->onDelete('cascade'); 
        
        $table->foreign('idClientNotifie')
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
         Schema::table('notification', function(Blueprint $table) {
            $table->dropForeign('notification_idLivre_foreign');
            $table->dropForeign('notification_idClientNotifie_foreign');
            $table->dropForeign('notification_idClientNotifieur_foreign');
        });
        
        Schema::drop('notification');
    }
}
