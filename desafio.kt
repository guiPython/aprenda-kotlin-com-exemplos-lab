enum class Nivel { BASICO, INTERMEDIARIO, DIFICIL }
sealed class Time(val value: Int)
class Minutos(value: Int) : Time(value)
class Horas(value: Int) : Time(value)

data class Usuario(val id: Int, val nome: String) {
    override fun hashCode() = id.hashCode()
    override fun equals(other: Any?): Boolean = when (other) {
        is Usuario -> other.id == this.id
        else -> false
    }
}

data class ConteudoEducacional(val instrutor: String, val nome: String, val duracao: Time) {
    override fun toString(): String = when (duracao) {
            is Minutos -> "O conteúdo $nome tem duração de ${duracao.value} minuto(s)"
            is Horas ->  "O conteúdo $nome tem duração de ${duracao.value} hora(s)"
    }
}

data class Formacao(val nome: String, val nivel: Nivel, val conteudos: Set<ConteudoEducacional>) {

    private val inscritos = mutableSetOf<Usuario>()

    fun matricular(vararg usuarios: Usuario) {
        for(usuario in usuarios) inscritos.add(usuario).let {
            if(it) println("\n${usuario.nome} adicionado na formação $nome")
            else println("\n${usuario.nome} já adicionado")
        }
    }
    
    override fun toString(): String {
        val result = StringBuilder().run { 
            append("$nome\n")
            append("Conteudos:\n${conteudos.joinToString(separator="\n\t", prefix="\t")}") 
        }
        
        if (inscritos.size > 0) {
            result.run { append("\nInscritos:\n${inscritos.joinToString(separator="\n\t", prefix="\t")}") } 
        } else {
            result.run { append("\nSem inscritos.") }
        }
        return result.toString()
    }
}

fun main() {
    val alunos = listOf(Usuario(1, "Guilherme"), Usuario(2, "João"), Usuario(3, "Matheus"))
    val conteudos = arrayOf(
        ConteudoEducacional("Venilton Falvo Jr", "Introdução Prática à Linguagem de Programação Kotlin", Horas(2)),
        ConteudoEducacional("Rodrigo Tavares", "Arquitetura Orientada a Eventos com Java", Horas(3)),
        ConteudoEducacional("Venilton Falvo Jr", "Conhecendo o Kotlin e Sua Documentação Oficial", Minutos(60))
    )

    val formacao = Formacao("Code Update TQI - Backend com Kotlin e Java", Nivel.INTERMEDIARIO, setOf(*conteudos))
    println("\nAntes das matriculas:\n")
    println(formacao)
    
    println("\nMatriculas iniciadas:")
    formacao.matricular(alunos[0])
    formacao.matricular(alunos[0])
    formacao.matricular(alunos[1], alunos[2])
    
    println("\nApós matriculas:\n")
    println(formacao)
}