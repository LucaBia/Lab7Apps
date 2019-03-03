package com.example.gianlucariverabiagioni.lab7intento2.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.gianlucariverabiagioni.lab7intento2.Data.Contact
import com.example.gianlucariverabiagioni.lab7intento2.Data.Repository

class ViewModel(application: Application) : AndroidViewModel(application) {
    private var repository: Repository =
        Repository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    // Create - Read - Update - Delete  (CRUD)
    //Agrega un contacto
    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    //Actualiza la info de un contacto
    fun update(contact: Contact) {
        repository.update(contact)
    }

    //Elimina un contacto
    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    //Elimina todos los contactos registrados en la BD
    fun deleteAllContacts() {
        repository.deleteAllContacts()
    }

    //Captura todos los contactos de la base de datos
    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}