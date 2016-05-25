<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateNotificationsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('notifications', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('notifier_user_id')->unsigned();
            $table->integer('notified_user_id')->unsigned();
            $table->integer('book_id')->unsigned();
            $table->string('type');
            $table->text('details');
            $table->timestamps();

            $table->foreign('notifier_user_id')
                ->references('id')
                ->on('users')
                ->onDelete('cascade');

            $table->foreign('notified_user_id')
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
        Schema::table('notifications', function (Blueprint $table) {
            $table->dropForeign('notifications_notifier_user_id_foreign');
            $table->dropForeign('notifications_notified_user_id_foreign');
            $table->dropForeign('notifications_book_id_foreign');
        });

        Schema::drop('notifications');
    }
}
