import com.lowlevelsubmarine.ytma.core.YTMA

fun main() {
    val ytma = YTMA()
    val sr = ytma.search("awolnation").getSongPager()
    sr.fetchNext()
    sr.fetchNext()
    sr.fetchNext()
    println(sr.getResults())
}