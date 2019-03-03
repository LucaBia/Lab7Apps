package com.example.gianlucariverabiagioni.lab7intento2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_contact.*

class AddContact : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        //Valor minimo que se le puede dar como prioridad
        prioritySelec.minValue = 1
        //Valr maximo que se le puede dar como prioridad a un contacto
        prioritySelec.maxValue = 10


        if (intent.hasExtra(MainActivity.EXTRA_ID)) {
            title = "Editar Contacto"
            //Se capturan los datos si eel caso es de editar un contacto
            edit_text_name.setText(intent.getStringExtra(MainActivity.EXTRA_NAME))
            edit_text_phone.setText(intent.getStringExtra(MainActivity.EXTRA_PHONE))
            edit_text_mail.setText(intent.getStringExtra(MainActivity.EXTRA_MAIL))
            //Se llena por defecto el valor de prioridad
            prioritySelec.value = intent.getIntExtra(MainActivity.EXTRA_PRIORITY, 5)
        } else {
            title = "Agregar Contacto"
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_contact_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.save_contact -> {
                saveContact()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun saveContact() {
        if (edit_text_name.text.toString().trim().isBlank() || edit_text_mail.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Error, verifica que todos los campos esten llenos.", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Intent().apply {
            putExtra(MainActivity.EXTRA_NAME, edit_text_name.text.toString())
            putExtra(MainActivity.EXTRA_PHONE, edit_text_phone.text.toString())
            putExtra(MainActivity.EXTRA_MAIL, edit_text_mail.text.toString())
            putExtra(MainActivity.EXTRA_PRIORITY, prioritySelec.value)
            if (intent.getIntExtra(MainActivity.EXTRA_ID, -1) != -1) {
                putExtra(MainActivity.EXTRA_ID, intent.getIntExtra(MainActivity.EXTRA_ID, -1))
            }
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }


}
