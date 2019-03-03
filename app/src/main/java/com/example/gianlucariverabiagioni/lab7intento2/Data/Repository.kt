package com.example.gianlucariverabiagioni.lab7intento2.Data

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class Repository(application: Application) {

    private var repQuery: Query

    private var allContacts: LiveData<List<Contact>>

    init {
        val database: ContactDatabase = ContactDatabase.getInstance(
            application.applicationContext
        )!!
        repQuery = database.query()
        allContacts = repQuery.getAllContacts()
    }

    fun insert(contact: Contact) {
        val insertContactAsyncTask = InsertContactAsyncTask(repQuery).execute(contact)
    }

    fun update(contact: Contact) {
        val updateContactAsyncTask = UpdateContactAsyncTask(repQuery).execute(contact)
    }

    fun delete(contact: Contact) {
        val deleteContactAsyncTask = DeleteContactAsyncTask(repQuery).execute(contact)
    }

    fun deleteAllContacts() {
        val deleteAllContactsAsyncTask = DeleteAllContactsAsyncTask(repQuery).execute()
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return allContacts
    }

    companion object {
        private class InsertContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                contactDao.insert(p0[0]!!)
            }
        }

        private class UpdateContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                contactDao.update(p0[0]!!)
            }
        }

        private class DeleteContactAsyncTask(contactDao: Query) : AsyncTask<Contact, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg p0: Contact?) {
                contactDao.delete(p0[0]!!)
            }
        }

        private class DeleteAllContactsAsyncTask(contactDao: Query) : AsyncTask<Unit, Unit, Unit>() {
            val contactDao = contactDao

            override fun doInBackground(vararg p0: Unit?) {
                contactDao.deleteAllContacts()
            }
        }
    }
}