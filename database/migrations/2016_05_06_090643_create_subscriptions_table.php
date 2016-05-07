<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateSubscriptionsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('subscriptions', function (Blueprint $table) {

            $table->increments('id');
            $table->integer('user_id')->unsigned();
            $table->integer('followed_user_id')->unsigned();
            $table->boolean('notifications_accepted')->default(true);
            $table->timestamps();

            $table->foreign('user_id')
                ->references('id')
                ->on('users')
                ->onDelete('cascade');
            $table->foreign('followed_user_id')
                ->references('id')
                ->on('users')
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

        Schema::table('subscriptions', function (Blueprint $table) {
            $table->dropForeign('subscriptions_user_id_foreign');
            $table->dropForeign('subscriptions_followed_user_id_foreign');
        });

        Schema::drop('subscriptions');
    }
}
