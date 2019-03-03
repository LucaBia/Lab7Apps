package com.example.gianlucariverabiagioni.lab7intento2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gianlucariverabiagioni.lab7intento2.Adapter.ContactAdapter
import com.example.gianlucariverabiagioni.lab7intento2.Data.Contact
import com.example.gianlucariverabiagioni.lab7intento2.ShowContact.Companion.EDIT_CONTACT_REQUEST
import com.example.gianlucariverabiagioni.lab7intento2.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        // constantes para el adaptador
        const val EXTRA_ID = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_ID"
        const val EXTRA_NAME = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_NAME"
        const val EXTRA_PHONE = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_PHONE"
        const val EXTRA_MAIL = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_MAIL"
        const val EXTRA_PRIORITY = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_PRIORITY"
        const val ADD_CONTACT_REQUEST = 1
        const val SHOW_CONTACT_REQUEST = 2
        lateinit var adapter: ContactAdapter
    }
    private lateinit var contactViewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddContact.setOnClickListener {
            //Guardar contacto
            startActivityForResult(
                Intent(this, AddContact::class.java),
                ADD_CONTACT_REQUEST
            )
        }
        //Se pasan los datos al recyclerview
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        adapter = ContactAdapter()
        //Se le pasa el adaptador para que funcione
        recycler_view.adapter = adapter
        contactViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        contactViewModel.getAllContacts().observe(this, Observer<List<Contact>> {
            adapter.submitList(it)



        })

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                contactViewModel.delete(adapter.getContactAt(viewHolder.adapterPosition))
                Toast.makeText(baseContext, "El contacto ha sido eliminado", Toast.LENGTH_SHORT).show()
            }
        }
        ).attachToRecyclerView(recycler_view)


        adapter.setOnItemClickListener(object : ContactAdapter.OnItemClickListener {
            override fun onItemClick(contact: Contact) {
                //Estos son los cardview, y al presionarlo se redirecciona a la vista de la informacion del contacto
                var intent = Intent(baseContext, ShowContact::class.java)
                intent.putExtra(EXTRA_ID, contact.id)
                intent.putExtra(EXTRA_NAME, contact.name)
                intent.putExtra(EXTRA_PHONE, contact.phone)
                intent.putExtra(EXTRA_MAIL, contact.email)
                intent.putExtra(EXTRA_PRIORITY, contact.priority)

                startActivityForResult(intent, SHOW_CONTACT_REQUEST)
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete_all_contacts -> {
                contactViewModel.deleteAllContacts()
                Toast.makeText(this, "Se han eliminado todos los contactos", Toast.LENGTH_SHORT).show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            //Se solicitan los datos para agregar un nuevo contacto (Todos obligatorios)
            val newContact = Contact(
                data!!.getStringExtra(EXTRA_NAME),
                data!!.getStringExtra(EXTRA_PHONE),
                data!!.getStringExtra(EXTRA_MAIL),
                data!!.getIntExtra(EXTRA_PRIORITY, 1)
            )
            contactViewModel.insert(newContact)

            Toast.makeText(this, "El contacto ha sido guardado", Toast.LENGTH_SHORT).show()
        } else if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(EXTRA_ID, -1)

            if (id == -1) {
                Toast.makeText(this, "Error, los datos del contacto no se han podido actualizar", Toast.LENGTH_SHORT).show()
            }

            val updateContact = Contact(
                data!!.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_PHONE),
                data.getStringExtra(EXTRA_MAIL),
                data.getIntExtra(EXTRA_PRIORITY, 1)
            )
            updateContact.id = data.getIntExtra(EXTRA_ID, -1)

        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }


    }


}