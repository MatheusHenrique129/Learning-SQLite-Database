package com.example.roombancodedados.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.roombancodedados.model.Contato

@Database(entities = [Contato::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun ContatoDao(): ContatoDao
}