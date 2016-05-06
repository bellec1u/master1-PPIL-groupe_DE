<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateClientTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('client', function (Blueprint $table) {

            $table->increments('id');
            $table->string('adresseMail');
            $table->string('motDePasse');
            $table->string('nom');
            $table->string('prenom');
            $table->char('sexe', 1);
            $table->date('dateNaissance');
            $table->string('imageProfil');
            $table->boolean('etreSuivi');
            $table->boolean('mailValide');
            $table->date('dateInscription');
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
        Schema::drop('client');
    }
}
