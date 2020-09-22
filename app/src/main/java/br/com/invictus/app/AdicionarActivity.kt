package br.com.invictus.app

import android.R.array
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.adicionar_screen.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class AdicionarActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.adicionar_screen)

        setSupportActionBar(toolbar_view)

        supportActionBar?.title = "Estoque"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        botaoAdicionar.setOnClickListener { onAddItem() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun onAddItem() {
        val cod = campoCodigo.text.toString()
        val nome = campoNome.text.toString()
        val quantidade = campoQuantidade.text.toString()
        val preco = campoPreco.text.toString()

        val hashMap: HashMap<String, String> = HashMap<String, String>();
        hashMap["codigo_produto"] = cod
        hashMap["nome"] = nome
        hashMap["quantidade"] = quantidade
        hashMap["preco"] = preco

        val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE) ?: return
        val gson = Gson()
        val arrayType = object : TypeToken<Array<Any>>() {}.type

        val mutableItems: MutableList<Any> = mutableListOf<Any>()

        val items: Array<Any> =
            gson.fromJson(sharedPref.getString("produtos", "[]"), arrayType)
        items.forEachIndexed  { idx, tut -> mutableItems.add(tut) }
        mutableItems.add(hashMap);
        mutableItems.forEachIndexed  { idx, tut -> println(tut) }

        val json = Gson().toJson(mutableItems)

        with(sharedPref.edit()) {
            putString("produtos", json)
            commit()
        }

        startActivity(Intent(this, TelaInicialActivity::class.java))
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
