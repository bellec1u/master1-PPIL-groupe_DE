<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateRatingsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('ratings', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('book_id')->unsigned();
            $table->integer('user_id')->unsigned()->nullable();
            $table->longText('comment');
            $table->integer('stars');
            $table->timestamps();
            
            $table->foreign('book_id')
                ->references('id')
                ->on('books')
                ->onDelete('cascade');
            $table->foreign('user_id')
                ->references('id')
                ->on('users')
                ->onDelete('set null');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('ratings', function (Blueprint $table) {
            $table->dropForeign('ratings_book_id_foreign');
            $table->dropForeign('ratings_user_id_foreign');
        });

        Schema::drop('ratings');
    }
}
