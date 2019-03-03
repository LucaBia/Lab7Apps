package com.example.gianlucariverabiagioni.lab7intento2.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


//Querys necesarios para realizar las operaciones entre la base de datos y el usuario
@Dao
interface Query {
    //Agrega un contacto nuevo
    @Insert
    fun insert(contact: Contact)

    //Actualiza un contacto ya guardado anteriormente
    @Update
    fun update(contact: Contact)

    //Elimina un correo ya guardado
    @Delete
    fun delete(contact: Contact)

    //Elimina todos los contactos registrados
    @Query("DELETE FROM contact_table")
    fun deleteAllContacts()

    //Captura todos los contactos, ordenados de manera seguns la prioridad del contacto
    @Query("SELECT * FROM contact_table ORDER BY priority DESC")
    fun getAllContacts(): LiveData<List<Contact>>
}