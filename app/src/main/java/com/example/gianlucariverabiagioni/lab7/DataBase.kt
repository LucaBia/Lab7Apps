package com.example.gianlucariverabiagioni.lab7

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 1)
abstract class DataBase : RoomDatabase(){

    abstract fun query(): Query

    companion object {
        private var instance: DataBase?=null

        //Obtiene la instancia de la base de datos
        fun getInstance(context: Context):DataBase?{
            if (instance == null){
                synchronized(DataBase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataBase::class.java, "contactDB"
                    ).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun  destroyInstance(){
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }
    }

    class PopulateDbAsyncTask (db:DataBase?): AsyncTask<Unit, Unit, Unit>(){
        private val query = db?.query()

        //Contactos colocados por default en la vista
        override fun doInBackground(vararg param: Unit?) {
            query?.insert((Contact("Willi", "ros18676@uvg.edu.gt", "54535251", 10 )))
            query?.insert((Contact("Andy", "cas18040@uvg.edu.gt", "54465251", 9 )))
            query?.insert((Contact("Luca", "riv18049@uvg.edu.gt", "56064796", 1 )))
        }
    }
}