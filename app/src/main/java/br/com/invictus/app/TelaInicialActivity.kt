package br.com.invictus.app

import ProdutosAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.navigation_menu.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class TelaInicialActivity : DebugActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val context: Context get() = this
    private var produtos = listOf<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_inicial)

        setSupportActionBar(toolbar_view)

        this.generic_layout = layoutMenuLateral
        supportActionBar?.title = "Estoque"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configuraMenuLateral()

        recyclerDisciplinas?.layoutManager = LinearLayoutManager(context)
        recyclerDisciplinas?.itemAnimator = DefaultItemAnimator()
        recyclerDisciplinas?.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        taskProdutos()
    }

    fun getItems(): List<Produto> {
        val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE)
        val gson = Gson()
        val arrayType = object : TypeToken<List<Produto>>() {}.type

        val items: List<Produto> =
            gson.fromJson<List<Produto>>(sharedPref.getString("items", ""), arrayType)

        return items
    }

    fun enviaNotificacao(produto: Produto) {
// Intent para abrir tela quando clicar na notificação
        val intent = Intent(this, ProdutoActivity::class.java)
// parâmetros extras
        intent.putExtra("produto", produto)
// Disparar notificação
        NotificationUtils.create(
            1,
            intent,
            "Invictus App",
            "Você tem nova atividade no ${produto.nome}"
        )
    }

    fun taskProdutos() {
        Thread {
            if (AndroidUtils.isInternetDisponivel(context)) {
                this.produtos = ProdutosService.getProdutos(context)
                val sharedPref = getSharedPreferences("PRODUTOS", Context.MODE_PRIVATE)
                val gson = Gson()
                val json = gson.toJson(this.produtos)

                with(sharedPref.edit()) {
                    putString("items", json)
                    commit()
                }
            } else {
                this.produtos = getItems()
            }


            runOnUiThread {
                recyclerDisciplinas?.adapter =
                    ProdutosAdapter(produtos) { onClickProduto(it) }

                if (this.produtos.isNotEmpty()) {
                    enviaNotificacao(this.produtos[0]);
                }

            }
        }.start()
    }

    private fun onClickProduto(produto: Produto) {
        Toast.makeText(context, "Clicou no produto ${produto.nome}", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, ProdutoActivity::class.java)
        intent.putExtra("produto", produto)
        startActivity(intent)
    }

    private fun configuraMenuLateral() {
        val toggle = ActionBarDrawerToggle(
            this,
            layoutMenuLateral,
            toolbar_view,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        layoutMenuLateral.addDrawerListener(toggle)
        toggle.syncState()
        menu_lateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.adicionar_menu -> {
                startActivity(Intent(this, AdicionarActivity::class.java))
            }

            R.id.nav_location -> {
                val intent = Intent(this, MapasActivity::class.java)
                Toast.makeText(this, "Menu Localização", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }

            R.id.logout -> {
                val sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE)

                with(sharedPref.edit()) {
                    putBoolean("isLogged", false)
                    commit()
                }

                startActivity(Intent(this, MainActivity::class.java))
            }

            android.R.id.home -> {
                finish()
            }
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_buscar) {
            Toast.makeText(this, "Buscando...", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_atualizar) {
            Toast.makeText(this, "Atualizando...", Toast.LENGTH_LONG).show()
        } else if (id == R.id.action_add) {
            startActivity(Intent(this, AdicionarActivity::class.java))
        } else if (id == R.id.action_exit) {
            startActivity(Intent(this, MainActivity::class.java))
        } else if (id == android.R.id.home) {
            finish()
        }

        return true
    }
}
