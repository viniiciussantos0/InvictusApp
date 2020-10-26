package br.com.invictus.app

import android.content.Context
import java.io.Serializable

class Disciplina : Serializable {

    var id:Long = 0
    var nome = ""
    var ementa = ""
    var foto = ""
    var professor = ""

    override fun toString(): String {
        return "Disciplina(nome='$nome')"
    }
}

object DisciplinaService {
    fun getDisciplinas (context: Context): List<Disciplina> {
        val disciplinas = mutableListOf<Disciplina>()
// criar 10 disciplinas
        for (i in 1..10) {
            val d = Disciplina()
            d.nome = "Disciplina $i"
            d.ementa = "Ementa Disciplina $i"
            d.professor = "Professor Disciplina $i"
            d.foto = "https://dummyimage.com/600x400/000/fff"
            disciplinas.add(d)
        }
        return disciplinas
    }
}