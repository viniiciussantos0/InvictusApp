package br.com.invictus.app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tela_inicial.*
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity() {
    private val context: Context get() = this
    private var isLogged: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        campoImagem.setImageResource(R.drawable.logo)
        val sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE)

        val isLogged: Boolean = sharedPref.getBoolean("isLogged", false)

        if (isLogged) {
            val intent = Intent(this, TelaInicialActivity::class.java)
            startActivity(intent)
        }

        botaoLogin.setOnClickListener { onClickLogin() }
    }

    fun onClickLogin() {
        val user = campoUsuario.text.toString()
        val password = campoSenha.text.toString()

        Thread {
            this.isLogged = UserService.login(context, user, password)

            if (this.isLogged) {
                val sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("isLogged", true)
                    commit()
                }

                val intent = Intent(this, TelaInicialActivity::class.java)

//            intent.putExtra("nome_usuario", user)
//            intent.putExtra("numero", 10)

                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            }
        }.start()


    }
}
