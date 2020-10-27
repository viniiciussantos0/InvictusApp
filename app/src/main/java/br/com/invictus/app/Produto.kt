package br.com.invictus.app

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Serializable

class Produto : Serializable {

    var codigo: Int = 0
    var nome = ""
    var qtd = ""
    var preco = ""

    override fun toString(): String {
        return "Produto(nome='$nome', qtd='$qtd', preco='$preco', codigo='$codigo')"
    }

    fun toJson(): String {
        return GsonBuilder().create().toJson(this)
    }
}

inline fun <reified T> parserJson(json: String): T {
    val type = object : TypeToken<T>() {}.type
    return Gson().fromJson<T>(json, type)
}

data class Response(val status: String, val msg: String) {
    // verificar se o chamada foi executada com sucesso
    fun isOK() = "OK".equals(status, ignoreCase = true)
}

object ProdutosService {
    private const val host = "http://invictusapp2.pythonanywhere.com"

    fun getProdutos(context: Context): List<Produto> {
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/produtos"
            val json = HttpHelper.get(url)
            return parserJson(json)
        } else {
            return ArrayList<Produto>()
        }
    }

    fun save(produto: Produto): Response {
        val json = HttpHelper.post("$host/produtos", produto.toJson())
        return parserJson<Response>(json)
    }

    fun delete(produto: Produto): Response {
        val url = "$host/produtos/${produto.codigo}"
        val json = HttpHelper.delete(url)
        return parserJson<Response>(json)
    }
}