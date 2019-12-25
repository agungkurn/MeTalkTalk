package ak.android.metalktalk.profile

import ak.android.metalktalk.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class EditProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
    }
}

class AccountSettingsFragment : PreferenceFragmentCompat() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)[AccountSettingsViewModel::class.java]
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_account, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNameListener()
        addGenderListener()
    }

    private fun showAvailableData() {
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            // TODO: Do it like this instruction
            // 1. New user, no data saved (ignore it)
            // 2. Old user, but have uninstalled the app (saved on the cloud)
            // 3. Active user
        })
    }

    private fun addNameListener() {
        val namePreference = findPreference<EditTextPreference>("nama_lengkap")
        // Show the saved full name on db, if any
//        viewModel.displayName.observe(viewLifecycleOwner, Observer {
//            namePreference?.summary = it
//        })
        // Change listener, if any changes made
//        namePreference?.setOnPreferenceChangeListener { preference, newValue ->
//            Toast.makeText(requireContext(), "New value: $newValue", Toast.LENGTH_SHORT).show()
//            viewModel.saveDisplayName(newValue.toString())
//            true
//        }
    }

    private fun addGenderListener() {
//        val genderPreference = findPreference<DropDownPreference>("jenis_kelamin")
    }
}