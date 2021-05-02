package juniojsv.zork

import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import com.zaxsoft.zax.zmachine.ZCPU
import com.zaxsoft.zax.zmachine.ZUserInterface
import com.zaxsoft.zax.zmachine.util.Dimension
import com.zaxsoft.zax.zmachine.util.Point
import com.zaxsoft.zax.zmachine.util.ZColors
import juniojsv.zork.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity(), ZUserInterface, MultiAutoCompleteTextView.Tokenizer {
    private lateinit var binding: ActivityMainBinding
    private lateinit var zcpu: ZCPU

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        with(binding) {
            setContentView(root)
            mZOutput.movementMethod = ScrollingMovementMethod()
            mZStatusBar.text = StringBuilder("Zork I")
            mZInput.apply {
                setAdapter(
                    ArrayAdapter(
                        context,
                        android.R.layout.simple_list_item_1,
                        resources.getStringArray(R.array.commands)
                    )
                )
                threshold = 1
            }.setTokenizer(this@MainActivity)
            zcpu = ZCPU(this@MainActivity).apply {
                initializeFromAsset("zork1.z5", applicationContext)
                start()
            }
        }
    }

    private fun handlerInput(timeout: Long): String? {
        var string: String? = null

        binding.mZInput.setOnEditorActionListener { mZInput, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                string = mZInput.text.toString()
                true
            } else {
                false
            }
        }

        Thread.sleep(timeout)

        while (string == null) {
            if (timeout != 0L && string == null)
                break
            Thread.sleep(500L)
            continue
        }

        runOnUiThread {
            binding.mZInput.text.clear()
        }

        return string?.trim()
    }

    override fun fatal(message: String?) = showString(message)

    override fun initialize(version: Int) {
        runOnUiThread {
            binding.mZOutput.append("Android Port by Jeovane Santos.\n")
        }
    }

    override fun setTerminatingCharacters(characters: Vector<*>?) {

    }

    override fun hasStatusLine(): Boolean = true

    override fun hasUpperWindow(): Boolean = false

    override fun defaultFontProportional(): Boolean = false

    override fun hasColors(): Boolean = true

    override fun hasBoldface(): Boolean = true

    override fun hasItalic(): Boolean = true

    override fun hasFixedWidth(): Boolean = false

    override fun hasTimedInput(): Boolean = true

    override fun getScreenCharacters(): Dimension = Dimension(25, 25)

    override fun getScreenUnits(): Dimension = Dimension(25, 25)

    override fun getFontSize(): Dimension = Dimension(10, 10)

    override fun getWindowSize(window: Int): Dimension = Dimension(30, 30)

    override fun getDefaultForeground(): Int = 6

    override fun getDefaultBackground(): Int = 9

    override fun getCursorPosition(): Point = Point(0, 0)

    override fun showStatusBar(string: String?, a: Int, b: Int, flag: Boolean) = runOnUiThread {
        binding.mZStatusBar.text = string;
    }

    override fun splitScreen(lines: Int) {

    }

    override fun setCurrentWindow(window: Int) {

    }

    override fun setCursorPosition(x: Int, y: Int) {

    }

    override fun setColor(foreground: Int, background: Int) = runOnUiThread {
        with(binding.mZOutput) {
            setTextColor(ZColors.get(foreground))
            setBackgroundColor(ZColors.get(background))
        }
    }

    override fun setTextStyle(style: Int) {

    }

    override fun setFont(font: Int) {

    }

    override fun readLine(buffer: StringBuffer?, time: Int): Int {
        val line = handlerInput(time * 1000L)?.also { buffer?.append(it) }
        return if (line != null) '\n'.toInt() else 0
    }

    override fun readChar(time: Int): Int {
        return 0
    }

    override fun showString(string: String?) {
        with(binding.mZOutput) {
            val value = if (text.lines().size > TEXT_LIMIT) text.lines().drop(NUM_LINES_DROP)
                .fold("") { acc: String, string: String -> "$acc\n$string" } else text
            val buffer = StringBuilder(value)
            buffer.appendLine((if (buffer.isNotEmpty()) "\n" else "") + string)
            runOnUiThread {
                text = buffer
            }
        }
    }

    override fun scrollWindow(lines: Int) {

    }

    override fun eraseLine(size: Int) {

    }

    override fun eraseWindow(window: Int) = runOnUiThread {
        binding.mZOutput.text = String()
    }

    override fun getFilename(title: String?, suggested: String?, saveFlag: Boolean): String {
        return "Zork"
    }

    override fun quit() {
        finish()
    }

    override fun restart() {
        eraseWindow(0)
    }

    override fun findTokenStart(text: CharSequence, cursor: Int): Int {
        var i = cursor

        while (i > 0 && text[i - 1] != ' ') {
            i--
        }
        while (i < cursor && text[i] == ' ') {
            i++
        }

        return i
    }

    override fun findTokenEnd(text: CharSequence, cursor: Int): Int {
        var i = cursor
        val len = text.length

        while (i < len) {
            if (text[i] == ' ') {
                return i
            } else {
                i++
            }
        }

        return len
    }

    override fun terminateToken(text: CharSequence): CharSequence {
        return "$text "
    }

    companion object {
        const val TEXT_LIMIT = 35
        const val NUM_LINES_DROP = 10
    }

}