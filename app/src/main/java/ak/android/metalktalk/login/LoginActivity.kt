package ak.android.metalktalk.login

import ak.android.metalktalk.R
import ak.android.metalktalk.main.MainActivity
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.common.SignInButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        internal const val RC_SIGN_IN = 1000
    }

    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setSize(SignInButton.SIZE_WIDE)
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            setupLogin()
        }
    }

    private fun setupLogin() {
        btn_login.setOnClickListener {
            // Auth provider
            val provider = arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
            // Create and launch sign in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(provider)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Success signed in
                startActivity(Intent(this, MainActivity::class.java))
                // Destroy activity
                finish()
            } else {
                // Sign in failed
                Toast.makeText(
                    this,
                    "Gagal login.\nError code: ${response?.error?.errorCode}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
