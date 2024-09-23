package com.example.otchallenge

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import com.example.otchallenge.data.Book
import com.example.otchallenge.databinding.ViewBookListItemBinding
import com.example.otchallenge.ui.booklist.BookListRecyclerViewAdapter
import org.junit.Rule
import org.junit.Test

class BookListItemSnapshotTest {
    private val longText =
        """
        The ancient, weathered map crackled in Elara's hands, its faded ink depicting a forgotten 
        city nestled amidst towering peaks and shrouded in swirling mists, a place whispered about 
        in hushed tones by firelight, a place where legends were born and dreams turned to dust, a 
        place she now felt an irresistible pull towards, a yearning in her heart echoing the 
        whispers of the wind that seemed to sing of forgotten magic and the promise of adventure.
        """.trimIndent()

    @get:Rule
    val paparazzi =
        Paparazzi(
            theme = "Theme.Material3.DayNight.NoActionBar",
        )

    @Test
    fun capture_phone_day() {
        paparazzi.unsafeUpdateConfig(deviceConfig = DeviceConfig.PIXEL_6_PRO)
        doTest()
    }

    @Test
    fun capture_phone_day_fontScale_2f() {
        paparazzi.unsafeUpdateConfig(
            deviceConfig =
                DeviceConfig.PIXEL_6_PRO.copy(
                    fontScale = 2f,
                ),
        )
        doTest()
    }

    @Test
    fun capture_phone_night() {
        paparazzi.unsafeUpdateConfig(
            deviceConfig =
                DeviceConfig.PIXEL_6_PRO.copy(
                    nightMode = NightMode.NIGHT,
                ),
        )
        doTest()
    }

    @Test
    fun capture_tablet() {
        paparazzi.unsafeUpdateConfig(deviceConfig = DeviceConfig.NEXUS_10)
        doTest()
    }

    private fun doTest() {
        val book = Book("Short title", "Author", "Short Description", null)
        paparazzi.snapshot(updateAndGetLayout(book), "simple")
        val book2 = Book(longText, longText, longText, null)
        paparazzi.snapshot(updateAndGetLayout(book2), "long")
    }

    private fun updateAndGetLayout(book: Book): FrameLayout {
        val context = paparazzi.context
        val frameLayout =
            FrameLayout(context).apply {
                layoutParams =
                    ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, // Width
                        ViewGroup.LayoutParams.WRAP_CONTENT, // Height
                    )
                setPadding(16, 16, 16, 16)
            }
        val viewHolder =
            BookListRecyclerViewAdapter.ViewHolder(
                ViewBookListItemBinding.inflate(
                    LayoutInflater.from(context),
                    frameLayout,
                    false,
                ),
            )
        viewHolder.bind(book)
        frameLayout.addView(viewHolder.itemView)
        return frameLayout
    }
}
