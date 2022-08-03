<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('jobs', function (Blueprint $table) {
            $table->id();
            $table->foreignId('user_id')
                ->constrained('users')
                ->onUpdate('cascade')
                ->onDelete('cascade');
            $table->foreignId('kategori_id')
                ->constrained('kategoris')
                ->onUpdate('cascade')
                ->onDelete('cascade');
            $table->string('name');
            $table->text('deskripsi');
            $table->integer('fee');
            $table->date('deadline');
            $table->string('bidder')->nullable();
            $table->tinyInteger('status')->default(1); //1 = tersedia //2 = on proses //3 = complete //4=void
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
        Schema::dropIfExists('jobs');
    }
};
