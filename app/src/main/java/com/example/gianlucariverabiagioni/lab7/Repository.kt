package com.example.gianlucariverabiagioni.lab7

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class Repository(application: Application) {
    private var query: Query

    private var Contacts: LiveData<List<Contact>>

    init {
        val database: DataBase = DataBase.getInstance(
            application.applicationContext
        )!!
        query = database.query()
        Contacts = query.getAllContacts()
    }

    fun insert(contact: Contact) {
        val insertContactAsync = InsertContactAsyncTask(query).execute(contact)
    }

    fun update(contact: Contact) {
        val updateContactAsyncTask = UpdateContactAsyncTask(query).execute(contact)
    }

    fun delete(contact: Contact) {
        val deleteContactAsyncTask = DeleteContactAsyncTask(query).execute(contact)
    }

    fun deleteAllContacts() {
        val deleteAllContactsAsyncTask = DeleteAllContactsAsyncTask(query).execute()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return Contacts
    }

    companion object {
        private class InsertContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.insert(params[0]!!)
            }
        }

        private class UpdateContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.update(params[0]!!)
            }
        }

        private class DeleteContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.delete(params[0]!!)
            }
        }

        private class DeleteAllContactsAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg params: Contact?) {
                contactDao.deleteAllContacts()
            }
        }
    }
}
