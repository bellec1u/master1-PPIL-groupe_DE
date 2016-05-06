<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateLivreTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('livre', function(Blueprint $table) {

        $table->increments('id');
        $table->string('titre');
        $table->string('auteur');
        $table->date('dateDeParution');
        $table->longText('resume');
        $table->string('genre');
        $table->string('langue');
        $table->integer('noteMoyenne');
        $table->string('imageCouverture');
        $table->integer('nombreDePage');
        $table->timestamps();
    }); 
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
         Schema::drop('livre');
    }
}
