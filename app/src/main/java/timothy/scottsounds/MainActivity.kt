package sangui.boitephilippe

import android.animation.ObjectAnimator
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var mSoundPlayer: MediaPlayer? = null
    private var mSoundPlayer2: MediaPlayer? = null
    private val mButtons = arrayOfNulls<Button>(NUMBER_ITEMS)
    private var bInit = false
    lateinit var imageLeft: ImageView
    lateinit var imageRight: ImageView

    companion object {
        const val NUMBER_ITEMS = 6
    }

    override fun onSaveInstanceState(state: Bundle) {
        // set a flag so that onCreate knows this is valid
        state.putBoolean("StateSaved", true)
        super.onSaveInstanceState(state)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.mSoundPlayer = null
        this.mSoundPlayer2 = null

        this.mButtons[0] = findViewById(R.id.sound1Right)
        this.mButtons[1] = findViewById(R.id.sound2Right)
        this.mButtons[3] = findViewById(R.id.sound3Right)
        this.mButtons[2] = findViewById(R.id.sound1Left)
        this.mButtons[4] = findViewById(R.id.sound2Left)
        this.mButtons[5] = findViewById(R.id.sound3Left)
        this.imageLeft = findViewById(R.id.imageTopLeft)
        this.imageRight = findViewById(R.id.imageTopRight)

        for (i in 0 until NUMBER_ITEMS) {
            this.mButtons[i]?.setOnClickListener(this)
        }

    }

    public override fun onPause() {
        super.onPause()
        if (this.mSoundPlayer != null) {
            this.mSoundPlayer!!.stop()
            this.mSoundPlayer!!.reset()
        }
        if (this.mSoundPlayer2 != null) {
            this.mSoundPlayer2!!.stop()
            this.mSoundPlayer2!!.reset()
        }
    }

    public override fun onResume() {
        super.onResume()
    }

    public override fun onDestroy() {
        super.onDestroy()

        if (this.mSoundPlayer != null) {
            this.mSoundPlayer!!.stop()
            this.mSoundPlayer!!.release()
        }
        if (this.mSoundPlayer2 != null) {
            this.mSoundPlayer2!!.stop()
            this.mSoundPlayer2!!.release()
        }
    }

    /**
     * 1st argument : media source, 2nd argument : sound init delay,
     * 3rd argument : 1 for 1st character voice, 2 for 2nd character voice
     */
    override fun onClick(view: View) {
        val shakeLeft = ObjectAnimator.ofFloat(this.imageLeft, "Rotation", 0f, 5f)

        shakeLeft.duration = 150
        shakeLeft.repeatCount = 5
        shakeLeft.repeatMode = ObjectAnimator.REVERSE

        val shakeRight = ObjectAnimator.ofFloat(this.imageRight, "Rotation", 0f, 5f)

        shakeRight.duration = 150
        shakeRight.repeatCount = 5
        shakeRight.repeatMode = ObjectAnimator.REVERSE

        when (view.id) {
            R.id.sound1Right -> playSound(R.raw.compelling_01, 0, 1)
            R.id.sound2Right -> playSound(R.raw.chromebook_01, 0, 1)
            R.id.sound3Right -> playSound(R.raw.system_01, 0, 1)
            R.id.sound1Left -> playSound(R.raw.shareholders_01, 0, 2)
            R.id.sound2Left -> playSound(R.raw.fine_01, 0, 2)
            R.id.sound3Left -> playSound(R.raw.noise_01, 0, 1)
        }
        when (view.id) {
            R.id.sound1Left -> shakeLeft.start();
            R.id.sound2Left -> shakeLeft.start();
            R.id.sound3Left -> shakeLeft.start();
            R.id.sound1Right -> shakeRight.start();
            R.id.sound2Right -> shakeRight.start();
            R.id.sound3Right -> shakeRight.start();
        }
    }

    private fun playSound(id: Int, delay: Int, mediaType: Int) {
        if (mediaType == 1) {
            if (this.mSoundPlayer != null) {
                if (this.mSoundPlayer!!.isPlaying) {
                    this.mSoundPlayer!!.stop()
                }
                this.mSoundPlayer!!.release()
            }
            this.mSoundPlayer = MediaPlayer.create(this, id)
            this.mSoundPlayer!!.seekTo(delay)
            this.mSoundPlayer!!.start()
        } else {
            if (this.mSoundPlayer2 != null) {
                if (this.mSoundPlayer2!!.isPlaying) {
                    this.mSoundPlayer2!!.stop()
                }
                this.mSoundPlayer2!!.stop()
                this.mSoundPlayer2!!.release()
            }
            this.mSoundPlayer2 = MediaPlayer.create(this, id)
            this.mSoundPlayer2!!.seekTo(delay)
            this.mSoundPlayer2!!.start()
        }

    }

}
