package br.com.invictus.app

import android.R.array
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.adicionar_screen.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ProdutoActivity : DebugActivity() {


    var produto: Produto? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produto_screen)

        // recuperar objeto de Disciplina da Intent
        produto = intent.getSerializableExtra("produto") as Produto

        // configurar título com nome da Disciplina e botão de voltar da Toolbar
        // colocar Toolbar
        setSupportActionBar(toolbar_view)

        // alterar título da ActionBar
        supportActionBar?.title = produto?.nome

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val codigoTexto: TextView = findViewById<TextView>(R.id.codigoTexto)
        codigoTexto.setText(produto?.codigo?.toString())

        val textView3Texto: TextView = findViewById<TextView>(R.id.textView3Texto)
        textView3Texto.setText(produto?.nome)

        val quantidadeTexto: TextView = findViewById<TextView>(R.id.quantidadeTexto)
        quantidadeTexto.setText(produto?.qtd?.toString())

        val precoTexto: TextView = findViewById<TextView>(R.id.precoTexto)
        val preco = produto?.preco?.toDouble()?.div(100);

        precoTexto.setText(preco.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        if (id == android.R.id.home) {
            finish()
        }


        return true
    }
}
