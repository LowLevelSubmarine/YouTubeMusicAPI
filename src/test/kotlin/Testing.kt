import com.lowlevelsubmarine.ytma.core.YTMA
import org.junit.Test
import kotlin.test.assertTrue

class Testing {
    val TEST_MUSIC = listOf(
        "i am the doctor",
        "sigrid strangers",
        "gangsters paradise",
        "eminem slim shady",
    )

    val ytma = YTMA()

    @Test
    fun YTMATestSongSearch() {
        TEST_MUSIC.forEach {
            val sr = ytma.songPager(it)
            assertTrue(sr.getResults().isNotEmpty())
        }
    }

    @Test
    fun testBaseYTMAVideoParse() {
        TEST_MUSIC.forEach {
            val sr = ytma.videoPager(it)
            assertTrue(sr.getResults().isNotEmpty())
        }
    }
}