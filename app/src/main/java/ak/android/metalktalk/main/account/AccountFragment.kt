package ak.android.metalktalk.main.account

import ak.android.metalktalk.R
import ak.android.metalktalk.login.LoginActivity
import ak.android.metalktalk.profile.EditProfileActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class AccountFragment : PreferenceFragmentCompat() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[AccountViewModel::class.java]
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_main, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserData()
        setShowProgressListener()
        setLogoutListener()
    }

    private fun showUserData() {
        val userData = findPreference<Preference>("user")
        viewModel.currentUser.observe(viewLifecycleOwner, Observer {
            it?.let {
                // Show display name, if any
                it.displayName?.let { name ->
                    userData?.title = name
                }

                userData?.summary = it.email

                // If clicked, bring to EditProfile
                userData?.setOnPreferenceClickListener {
                    startActivity(Intent(requireContext(), EditProfileActivity::class.java))
                    true
                }
            }
        })
    }

    private fun setShowProgressListener() {
        // TODO: Start intent to progress activity
    }

    private fun setLogoutListener() {
        val logoutOption = findPreference<Preference>("logout")
        logoutOption?.setOnPreferenceClickListener {
            viewModel.logout()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
            true
        }
    }
}