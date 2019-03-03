package com.example.gianlucariverabiagioni.lab7

import androidx.room.Entity
import androidx.room.PrimaryKey

//Atributos que posee un contacto
@Entity(tableName = "contactTable")
class Contact(
    var name: String,
    var email: String,
    var phone: String,
    var priority: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}