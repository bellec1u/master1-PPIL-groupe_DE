<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateAnnotationTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('annotation', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('idLivre')->unsigned();
            $table->integer('idClient')->unsigned();
            $table->integer('numLigne');
            $table->integer('nbMotsDebutLigne');
            $table->longText('texteAnnote');
            $table->longText('commentaire');
            $table->integer('numeroDePage');
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
        Schema::table('annotation', function (Blueprint $table) {
            $table->dropForeign('annotation_idLivre_foreign');
            $table->dropForeign('annotation_idClient_foreign');
        });

        Schema::drop('annotation');
    }
}
