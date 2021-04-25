package juniojsv.zork

import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.inputmethod.EditorInfo
import com.zaxsoft.zax.zmachine.ZCPU
import com.zaxsoft.zax.zmachine.ZUserInterface
import com.zaxsoft.zax.zmachine.util.Dimension
import com.zaxsoft.zax.zmachine.util.Point
import com.zaxsoft.zax.zmachine.util.ZColors
import juniojsv.zork.databinding.ActivityMainBinding
import java.util.*

class MainActivity : Activity(), ZUserInterface {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mZOutput.movementMethod = ScrollingMovementMethod()
        ZCPU(this).apply {
            initializeFromAsset("zork1.z5", applicationContext)
        }.start()
    }

    private fun handlerInput() : String {
        var string: String? = null

        binding.mZInput.setOnEditorActionListener { mZInput, action, _ ->
            if (action == EditorInfo.IME_ACTION_DONE) {
                string = mZInput.text.toString()
                true
            } else {
                false
            }
        }

        while (string == null) {
            Thread.sleep(700)
            continue
        }

        binding.mZInput.text.clear()

        return string!!
    }

    override fun fatal(message: String?) = showString(message)

    override fun initialize(version: Int) {
        runOnUiThread {
            binding.mZOutput.append("Android Port by Jeovane Santos.\n")
        }
    }

    override fun setTerminatingCharacters(characters: Vector<*>?) {

    }

    override fun hasStatusLine(): Boolean = false

    override fun hasUpperWindow(): Boolean = false

    override fun defaultFontProportional(): Boolean = true

    override fun hasColors(): Boolean = true

    override fun hasBoldface(): Boolean = true

    override fun hasItalic(): Boolean = true

    override fun hasFixedWidth(): Boolean = true

    override fun hasTimedInput(): Boolean = false

    override fun getScreenCharacters(): Dimension = Dimension(25, 25)

    override fun getScreenUnits(): Dimension = Dimension(25, 25)

    override fun getFontSize(): Dimension = Dimension(10, 10)

    override fun getWindowSize(window: Int): Dimension = Dimension(30, 30)

    override fun getDefaultForeground(): Int = 6

    override fun getDefaultBackground(): Int = 9

    override fun getCursorPosition(): Point = Point(0, 0)

    override fun showStatusBar(s: String?, a: Int, b: Int, flag: Boolean) {

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
        val line = handlerInput()
        buffer?.append(line)
        return '\n'.toInt()
    }

    override fun readChar(time: Int): Int {
        return 0
    }

    override fun showString(string: String?) = runOnUiThread {
        with(binding.mZOutput) {
            val buffer = StringBuilder(text)
            buffer.appendLine((if (buffer.isNotEmpty()) "\n" else "") + string)
            text = buffer
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
        return "zork"
    }

    override fun quit() {

    }

    override fun restart() {

    }

}