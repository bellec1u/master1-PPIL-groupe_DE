<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateLectureTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('lecture', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('idClient')->unsigned();
            $table->integer('idLivre')->unsigned();
            $table->integer('pageCourante');
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
        Schema::table('lecture', function (Blueprint $table) {
            $table->dropForeign('lecture_idLivre_foreign');
            $table->dropForeign('lecture_idClient_foreign');
        });

        Schema::drop('lecture');
    }
}
