package com.example.gianlucariverabiagioni.lab7intento2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.gianlucariverabiagioni.lab7intento2.Data.Contact
import com.example.gianlucariverabiagioni.lab7intento2.MainActivity.Companion.EXTRA_MAIL
import com.example.gianlucariverabiagioni.lab7intento2.ViewModels.ViewModel
import kotlinx.android.synthetic.main.activity_show_contact.*

class ShowContact : AppCompatActivity() {

    companion object {
        const val EDIT_CONTACT_REQUEST = 3
        const val EXTRA_MAIL = "com.example.gianlucariverabiagioni.lab7intento2.EXTRA_MAIL"
    }

    lateinit var  contactViewModel: ViewModel
    //lateinit var  txtcorreo: TextView
    private var currentId:Int= -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_contact)
        // Se conecta con el ViewModel
        contactViewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        //Se rellenan los datos
        fillName.text = intent.getStringExtra(MainActivity.EXTRA_NAME)
        fillPhone.text = intent.getStringExtra(MainActivity.EXTRA_PHONE)
        fillMail.text = intent.getStringExtra(MainActivity.EXTRA_MAIL)
        fillPriority.text = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 1).toString()
        currentId = intent.getIntExtra(MainActivity.EXTRA_ID, 1)

        val correo=intent.getStringExtra(EXTRA_MAIL)

        //intent para poder llamar
        fillPhone.setOnClickListener{
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${intent.getStringExtra(MainActivity.EXTRA_PHONE)}"))
            startActivity(intent)
        }

        //Broadcast para enviar email
        fillMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type = "text/html"
            intent.data= Uri.parse("mailto:"+correo)
            intent.putExtra(Intent.EXTRA_EMAIL,correo)
            startActivity(Intent.createChooser(intent, "Send Email"))
            intent.setAction("com.example.gianlucariverabiagioni.lab7intento2.CUSTOM_INTENT");
            sendBroadcast(intent)

        }
    }

    //Funcion para agregar al boton y volver al main
    fun back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Funcion que regresa a editar contacto
    fun editContact(view: View){
        var intent = Intent(baseContext, AddContact::class.java)
        //Se capturan los valores ya guardados
        intent.putExtra(MainActivity.EXTRA_ID, currentId)
        intent.putExtra(MainActivity.EXTRA_NAME, fillName.text.toString())
        intent.putExtra(MainActivity.EXTRA_PHONE, fillPhone.text.toString())
        intent.putExtra(MainActivity.EXTRA_MAIL, fillMail.text.toString())
        intent.putExtra(MainActivity.EXTRA_PRIORITY, fillPriority.text.toString())
        startActivityForResult(intent, EDIT_CONTACT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == EDIT_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            val id = data?.getIntExtra(MainActivity.EXTRA_ID, -1)
            val updateContact = Contact(
                data!!.getStringExtra(MainActivity.EXTRA_NAME),
                data.getStringExtra(MainActivity.EXTRA_PHONE),
                data.getStringExtra(MainActivity.EXTRA_MAIL),
                data.getIntExtra(MainActivity.EXTRA_PRIORITY, 1)
            )

            updateContact.id = id!!
            contactViewModel.update(updateContact)

            //Se agregan los campos para el update
            fillName.text = updateContact.name
            fillPhone.text = updateContact.phone
            fillMail.text = updateContact.email
            fillPriority.text = updateContact.priority.toString()

            currentId = updateContact.id
            Toast.makeText(this, "El contacto se ha actualizado exitosamente", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error, no se ha podido actualizar el contacto", Toast.LENGTH_SHORT).show()
        }
    }
}

