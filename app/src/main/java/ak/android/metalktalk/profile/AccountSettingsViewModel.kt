package ak.android.metalktalk.profile

import ak.android.metalktalk.model.UserData
import ak.android.metalktalk.model.toFirestoreTimestamp
import ak.android.metalktalk.model.toFormattedString
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountSettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val db = FirebaseFirestore.getInstance()
    private val pref = PreferenceManager.getDefaultSharedPreferences(getApplication())

    private val _error = MutableLiveData<Boolean>()
    internal val error: LiveData<Boolean> = _error

    internal val userData: LiveData<UserData?>
        get() = MutableLiveData<UserData?>().apply {
            // Check on preferences
            val savedName = pref.getString("nama_lengkap", null)
            val savedGender = pref.getString("jenis_kelamin", null)
            val savedBornDateStr = pref.getString("tanggal_lahir", null)

            if (savedName != null && savedGender != null && savedBornDateStr != null) {
                // If there is user data saved, show it
                val savedBornDate = savedBornDateStr.toFirestoreTimestamp()
                value = UserData(savedName, savedGender, savedBornDate)
            } else {
                // Else, fetch it from DB
                db.collection("user")
                    .document(currentUser?.email.toString())
                    .get()
                    .addOnSuccessListener {
                        // Download semua info user
                        value = if (it.exists()) {
                            val userData = UserData(
                                namaLengkap = it[UserData.FIELD_NAMA_LENGKAP].toString(),
                                jenisKelamin = it[UserData.FIELD_JENIS_KELAMIN].toString(),
                                tanggalLahir = it[UserData.FIELD_TANGGAL_LAHIR] as Timestamp
                            )
                            // Save to preferences
                            saveUserData(userData)
                            userData
                        } else {
                            _error.value = true
                            null
                        }
                    }
            }
        }

    internal fun fetchSavedUserData() {
        db.collection("user")
            .document(currentUser?.email.toString())
            .get()
            .addOnSuccessListener {
            }
    }

    internal fun saveUserData(userData: UserData) {
        // Save to firestore
        val dataToSave = hashMapOf<String, Any?>()
        dataToSave[UserData.FIELD_NAMA_LENGKAP] = userData.namaLengkap
        dataToSave[UserData.FIELD_JENIS_KELAMIN] = userData.jenisKelamin
        dataToSave[UserData.FIELD_TANGGAL_LAHIR] = userData.tanggalLahir
        db.collection("user")
            .document(currentUser?.email.toString())
            .update(dataToSave)

        // Save to Preferences
        pref.edit()
            .putString(UserData.FIELD_NAMA_LENGKAP, userData.namaLengkap)
            .putString(UserData.FIELD_JENIS_KELAMIN, userData.jenisKelamin)
            .putString(UserData.FIELD_TANGGAL_LAHIR, userData.tanggalLahir?.toFormattedString())
            .apply()
    }
}