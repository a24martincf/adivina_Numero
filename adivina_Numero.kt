
import kotlin.random.Random
import kotlin.system.exitProcess
import java.io.File
// Declaramos como constantes variables con secuencias de escape ANSI para modificar a saída de texto.
// Son declaradas como constantes, declarándose no momento de compilación, nada que ver cas variables que o fan en tempo de execución
const val BG_YELLOW = "\u001B[43m"
const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val BOLD = "\u001B[1m"
const val UNDERLINE = "\u001B[4m"
//  Declaramos distintas variables
var numAccion: String = ""
var cifraRand = 0
var numAdivinar = ""
var numUsuario = ""
var coincidencias = 0
var acertos = 0
//                          ****Con esta función imprimimos as posibles accións
fun TextAccion() {
    println("Indique mediante o número á súa esquerda, a acción que desexa realizar")
    println("$PURPLE${UNDERLINE}1$RESET.$PURPLE Xogar")
    println("$PURPLE${UNDERLINE}2$RESET.$PURPLE Ver o último intento")
    println("$PURPLE${UNDERLINE}3$RESET.$PURPLE Saír")
    println(RESET)
}
fun Accion() {               //  Aquí temos unha función para obter a acción
    TextAccion()
    numAccion = readln()
    while (numAccion != "1" && numAccion != "2" && numAccion != "3") {
        Accion()
    }
}
fun NumRandom() {               // Función que obtén un número aleatorio gardado na variable NumAdivinar
    numAdivinar = ""
    while (numAdivinar.length < 4) {           // *** Obtemos o número aleatorio de 4 cifras, con díxitos entre 1 e 6 sen repetir cifras
        cifraRand = Random.nextInt(1, 7)
        if (numAdivinar.contains(cifraRand.toString())) continue
        numAdivinar += cifraRand.toString()
    }
}
fun PedirNum() {        //  Función para pedir o número a comprobar ao usuario
    println("${RESET}${CYAN}Introduza o número que crea escondo. Cabe destacar, que as cifras van dende o ${RED}1 ${CYAN}ao ${RED}6,$CYAN ambos incluídos.\n" +
            "O número é de 4 cifras sen repetir. Que a ${BOLD}sorte$RESET${CYAN} o acompañe! ;-)$RESET")
    numUsuario = readln()
}

fun main() {
    Accion()    // Solicitamos unha acción

    while (numAccion == "1") {         // Corpo da aplicación
        acertos = 0
        coincidencias = 0       // Establecemos as coincidencias e acertos a 0 ao comezalo xogo
        val intentos=10         // Establecemos o número máximo de intentos
        NumRandom()             // Chamamos á función para obtelo número a adiviñar
        //println(numAdivinar)
        println("")
        println("$CYAN Ten vostede $intentos intentos. Sorte!$RESET")
        forIntentos@
        for (i in 1..intentos+1){       // Comeza o xogo, restando os intentos, establecemos acertos e coincidencias a 0, impedindo que se acumulen
            acertos = 0
            coincidencias = 0
            if ( i == intentos+1){              // Ao esgotalos intentos, forzamos o peche do bucle for, mandamos unha mensaxe e solicitamos unha nova acción
                println("${RED}Sentímolo, esgotou os intentos. O número era $numAdivinar. Máis sorte a próxima vez! \nSe quere, a continuación, pode volver tentalo :-)$RESET")
                println()
                println()
                println()
                Accion()
                break@forIntentos
            }
            println("Restan $BLACK$BG_YELLOW${intentos - (i-1)}$RESET intentos")
            PedirNum()
    //         Xa temos ambos números, o número a adiviñar e o intento do usuario
    //         Comparamos números e obtemos acertos e coincidencias ou felicitamos no caso de acertar o número.
    //         ************ PRECISA MELLORA OU SOLICITAR QUE NON REPITA CIFRAS!!!!!! **********

            if (numUsuario == numAdivinar){ // No caso de acertar o número, mandamos unha mensaxe, pedimos unha nova acción e pechamos o bucle for
                println("${GREEN}Noraboa. Acertou o número! Gustaríalle volver a xogar?$RESET")
                Accion()
                break@forIntentos
            }
            for (j in numUsuario) {     // Comparamos ambos números, obtendo coincidencias e acertos
                when {
                    numAdivinar.contains(j) && numAdivinar.indexOf(j) == numUsuario.indexOf(j) -> acertos += 1
                    numAdivinar.contains(j) -> coincidencias += 1
                }
            }
//                  Imprimimos as coincidencias e acertos
            println("Coincidencias:$BOLD $YELLOW$coincidencias$RESET")
            println("Acertos:$BOLD $GREEN$acertos$RESET")
            if (i == 1){                                                  // Se é o primiero intento, sobreescribimos o arquivo de texto
                File("IntentoAnterior.txt").writeText("O meu número: $numAdivinar")
                File("IntentoAnterior.txt").appendText("\nIntento ${i}: $numUsuario, Acertos: $acertos, Coincidencias: $coincidencias")
                continue
            }
            //            Engadimos os demáis intentos ao arquivo txt
            File("IntentoAnterior.txt").appendText("\nIntento ${i}: $numUsuario, Acertos: $acertos, Coincidencias: $coincidencias")
        }
    }
//          Se solicita a opción de ver intento, mostramos o contido do arquivo "IntentoAnterior.txt" no caso de existir e solicitamos unha nova accion
    if (numAccion == "2"){
        val ficheiro = File("IntentoAnterior.txt")
        if (ficheiro.exists()) {
            val contidoFicheiro = File("IntentoAnterior.txt").readText()
            println(BLUE)
            println(contidoFicheiro)
            println(RESET)
            Accion()
        }
    }
//          No caso de solicitar a opción 3, paramos o programa
    if (numAccion == "3") {
        println("${YELLOW}Grazas por xogar. Esperamos que volte pronto por aquí! :-)$RESET")
        exitProcess(0)
    }
}