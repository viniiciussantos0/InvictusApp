package br.com.invictus.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.login.*

class MainActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        campoImagem.setImageResource(R.drawable.logo)

        //botaoLogin.setOnClickListener {
        //val valorUsuario = campoUsuario.text.toString()
        //val valorSenha = campoSenha.text.toString()
        //Toast.makeText(this, "Usuário: $valorUsuario; Senha: $valorSenha", Toast.LENGTH_LONG).show()
        //}

        botaoLogin.setOnClickListener { onClickLogin() }
    }

    fun onClickLogin() {
        val user = campoUsuario.text.toString()
        val password = campoSenha.text.toString()

        if (user == "aluno" && password == "impacta") {
            var intent = Intent(this, TelaInicialActivity::class.java)

//            intent.putExtra("nome_usuario", user)
//            intent.putExtra("numero", 10)

            startActivity(intent)
        } else {
            Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
        }
    }
}
