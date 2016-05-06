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
        Schema::create('cheminAcces', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('idClient')->unsigned();
            $table->integer('idLivre')->unsigned();
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
        Schema::table('cheminAcces', function (Blueprint $table) {
            $table->dropForeign('cheminAcces_idLivre_foreign');
            $table->dropForeign('cheminAcces_idClient_foreign');
        });

        Schema::drop('cheminAcces');
    }
}
