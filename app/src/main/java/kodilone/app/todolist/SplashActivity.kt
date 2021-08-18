package kodilone.app.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        val i = Intent(this@SplashActivity, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(i)
            finish()
        }, 4000)
    }
}