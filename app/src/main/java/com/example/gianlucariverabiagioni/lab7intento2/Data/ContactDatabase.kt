package com.example.gianlucariverabiagioni.lab7intento2.Data


import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun query(): Query


    companion object {
        private var instance: ContactDatabase? = null

        //Logica para conectar a BD
        fun getInstance(context: Context): ContactDatabase? {
            if (instance == null) {
                synchronized(ContactDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java, "contact_database"
                    )
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }

        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance)
                    .execute()
            }
        }
    }

    class PopulateDbAsyncTask(db: ContactDatabase?) : AsyncTask<Unit, Unit, Unit>() {
        private val queryIns = db?.query()

        override fun doInBackground(vararg p0: Unit?) {
            //Contactos que se mostraran por defecto al iniciar la aplicacion
            queryIns?.insert(Contact("Willi", "52535456", "ros18676@uvg.edu.gt", 5))
            queryIns?.insert(Contact("Andy", "55565432", "cas18040@yvg.edu.gt", 4))
            queryIns?.insert(Contact("Luca", "56064796", "riv18049@uvg.edu.gt", 3))
        }
    }

}