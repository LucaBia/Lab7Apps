package com.example.gianlucariverabiagioni.lab7

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ViewModel (application: Application): AndroidViewModel(application){
    private var repository: Repository= Repository(application)
    private var allContacts: LiveData<List<Contact>> = repository.getAllContacts()

    //Query para insertar contacto
    fun insert (contact: Contact){
        repository.insert(contact)
    }

    //Query para actualizar un contacto
    fun update(contact: Contact){
        repository.update(contact)
    }

    //Query para eliminar un contacto
    fun delete (contact: Contact){
        repository.delete(contact)
    }

    //Query para eliminar todos los contactos registrados
    fun deleteAllContacts(){
        repository.deleteAllContacts()
    }

    //Query para capturar todos los contactos registrados
    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }
}