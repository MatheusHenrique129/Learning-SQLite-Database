package com.example.roombancodedados

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import com.example.roombancodedados.dao.AppDataBase
import com.example.roombancodedados.model.Contato
import kotlinx.android.synthetic.main.activity_main.*

private lateinit var buttonNovoContato: ImageButton
    private lateinit var editNome: EditText
    private  lateinit var editTelefone: EditText
    private lateinit var  buttonSalvar: Button
    private lateinit var buttonCancelar: Button
    private lateinit var dialog: AlertDialog
    private lateinit var txtLista: TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonNovoContato = findViewById(R.id.button_add_contato)
        buttonNovoContato.setOnClickListener(this)
        txtLista = findViewById(R.id.text_lista)
        txtLista.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        if (v.id == R.id.button_add_contato){
        abrirCadastroContato()
        } else if (v.id == R.id.button_cancelar){
            dialog.dismiss()
        } else if (v.id == R.id.button_salvar) {
            salvarContato()
        } else {
            exibirContatos()
        }

    }

    private fun exibirContatos() {
        val db = Room.databaseBuilder(
            this,
            AppDataBase::class.java,
            "db_contato").allowMainThreadQueries().build()

        val contatoDao = db.ContatoDao()

        val contatos = contatoDao.listarTodos()
        val separador = " - "

        txtLista.text = ""

        for (contato in contatos) {
            txtLista.text = "${txtLista.text} $separador ${contato.nomeContato}"
        }

    }

    private fun salvarContato() {
        var contato = Contato(nomeContato = editNome.text.toString(), telefoneContato = editTelefone.text.toString())

        //Obtendo o banco, ou caso não exista o banco ele cria um!
        val db = Room.databaseBuilder(
            this,
            AppDataBase::class.java,
            "db_contato").allowMainThreadQueries().build()

        val contatoDao = db.ContatoDao()
        contatoDao.salvar(contato)

    }

    private fun abrirCadastroContato() {
        val alertDialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.cadastro_contato_dialog, null)
        alertDialog.setView(view)

        editNome = view.findViewById(R.id.edit_nome)
        editTelefone = view.findViewById(R.id.edit_telefone)

        buttonSalvar = view.findViewById(R.id.button_salvar)
        buttonCancelar = view.findViewById(R.id.button_cancelar)

        buttonCancelar.setOnClickListener(this)
        buttonSalvar.setOnClickListener(this)

        dialog = alertDialog.create()
        dialog.setCancelable(false)
        dialog.show()

        buttonCancelar.setOnClickListener(this)


        //alertDialog.setCancelable(false).create().show()
    }
}