package com.example.gianlucariverabiagioni.lab7

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//Interfaz de los querys

@Dao
interface Query {
    @Insert
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("DELETE FROM contactTable")
    fun deleteAllContacts()

    @Query("SELECT * FROM contactTable ORDER BY prioridad DESC")
    fun getAllContacts(): LiveData<List<Contact>>
}