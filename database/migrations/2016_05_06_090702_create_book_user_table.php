<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateBookUserTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        /**
         * Pivot table for book suggestions
         */
        Schema::create('book_user', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('user_id')->unsigned();
            $table->integer('book_id')->unsigned();

            $table->foreign('user_id')
                ->references('id')
                ->on('users')
                ->onDelete('cascade');

            $table->foreign('book_id')
                ->references('id')
                ->on('books')
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
        Schema::table('book_user', function (Blueprint $table) {
            $table->dropForeign('book_user_user_id_foreign');
            $table->dropForeign('book_user_book_id_foreign');
        });
        Schema::drop('book_user');
    }
}
