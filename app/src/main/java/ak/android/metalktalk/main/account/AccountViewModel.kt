package ak.android.metalktalk.main.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AccountViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    internal val currentUser: LiveData<FirebaseUser?>
        get() = MutableLiveData<FirebaseUser?>().apply {
            value = auth.currentUser
        }

    internal fun logout() = auth.signOut()
}