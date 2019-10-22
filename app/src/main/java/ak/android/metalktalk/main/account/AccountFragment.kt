package ak.android.metalktalk.main.account

import ak.android.metalktalk.R
import ak.android.metalktalk.login.LoginActivity
import android.content.Intent
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.google.firebase.auth.FirebaseAuth

class AccountFragment : PreferenceFragmentCompat() {
    private val auth = FirebaseAuth.getInstance()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_account, rootKey)
        showUserData()
        setShowProgressListener()
        setLogoutListener()

        // TODO: List preference terlalu ke bawah, margin top terlalu jauh
    }

    private fun showUserData() {
        auth.currentUser?.let {
            val userData = findPreference<Preference>("user")
            if (it.displayName != null) {
                // Show display name
                userData?.title = it.displayName
            }
            userData?.summary = it.email
        }
    }

    private fun setShowProgressListener() {
        // TODO: Start intent to progress activity
    }

    private fun setLogoutListener() {
        val logoutOption = findPreference<Preference>("logout")
        logoutOption?.setOnPreferenceClickListener {
            auth.signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
            true
        }
    }
}