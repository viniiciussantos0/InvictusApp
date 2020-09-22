package br.com.invictus.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.toolbar.*

class TelaInicialActivity : DebugActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        setSupportActionBar(toolbar_view)

        supportActionBar?.title = "Estoque"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadItems()
    }

    fun loadItems() {
        val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE)
        val gson = Gson()
        val arrayType = object : TypeToken<Array<Any>>() {}.type

        val items: Array<Any> =
            gson.fromJson(sharedPref.getString("produtos", "[]"), arrayType)

        val listItems = arrayOfNulls<String>(items.size)
        for ((index, value: Any) in items.withIndex()) {
            val valueLinked: LinkedTreeMap<Any, Any> = value as LinkedTreeMap<Any, Any>
            listItems[index] = valueLinked["nome"].toString()
        }

        val adapter = ArrayAdapter(
            this,
            R.layout.listview_item, listItems
        )

        val listView: ListView = findViewById(R.id.itens_produtos)
        listView.adapter = adapter

        listView.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE)
                val gson = Gson()
                val arrayType = object : TypeToken<Array<Any>>() {}.type

                val items: Array<Any> =
                    gson.fromJson(sharedPref.getString("produtos", "[]"), arrayType)

                val intent = Intent(
                    this,
                    ProdutoActivity::class.java
                )

                intent.putExtra("item_produto", position)

                startActivity(intent)
            }
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
            loadItems()
        } else if (id == R.id.action_add) {
            startActivity(Intent(this, AdicionarActivity::class.java))
        } else if (id == R.id.action_exit) {
            startActivity(Intent(this, MainActivity::class.java))
        }

        return true
    }
}
