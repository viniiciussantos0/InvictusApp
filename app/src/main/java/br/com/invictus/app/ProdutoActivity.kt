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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.produto_screen)

        val args = intent.extras
        val prod = args?.getInt("item_produto")

        val items: Array<Any> = getItems()
        val produto: LinkedTreeMap<Any, Any> = items[prod!!] as LinkedTreeMap<Any, Any>

        setSupportActionBar(toolbar_view)

        supportActionBar?.title = "Produto"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val codigoTexto: TextView = findViewById(R.id.codigoTexto) as TextView
        codigoTexto.setText(produto["codigo_produto"].toString())

        val textView3Texto: TextView = findViewById(R.id.textView3Texto) as TextView
        textView3Texto.setText(produto["nome"].toString())

        val quantidadeTexto: TextView = findViewById(R.id.quantidadeTexto) as TextView
        quantidadeTexto.setText(produto["quantidade"].toString())

        val precoTexto: TextView = findViewById(R.id.precoTexto) as TextView
        precoTexto.setText(produto["preco"].toString())

    }

    fun getItems(): Array<Any> {
        val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE)
        val gson = Gson()
        val arrayType = object : TypeToken<Array<Any>>() {}.type

        val items: Array<Any> =
            gson.fromJson(sharedPref.getString("produtos", "[]"), arrayType)

        return items
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item?.itemId

        if (id == R.id.action_buscar) {
            Toast.makeText(this, "Buscando...", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_atualizar) {
            Toast.makeText(this, "Atualizando...", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_add) {
            startActivity(Intent(this, AdicionarActivity::class.java))
        } else if (id == R.id.action_exit) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        return true
    }
}
