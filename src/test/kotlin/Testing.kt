import com.lowlevelsubmarine.ytm_api.core.YTMA

fun main() {
    val ytma = YTMA()
    val sr = ytma.search("thefatrat")
    println(sr.getSongs().first().getTitle())
}