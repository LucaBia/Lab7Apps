package com.example.gianlucariverabiagioni.lab7intento2.Data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Blob

//Data class con los atributos que posee un contacto
@Entity(tableName = "contact_table")
data class Contact(
    var name: String,
    var phone: String,
    var email: String,
    var priority: Int
    //var image: Blob
){
    //Llave primaria de la tabla de DB
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}