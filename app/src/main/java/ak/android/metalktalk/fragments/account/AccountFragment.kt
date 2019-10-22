package ak.android.metalktalk.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ak.android.metalktalk.R
import androidx.lifecycle.ViewModelProviders

class AccountFragment : Fragment() {

    private val accountViewModel by lazy {
        ViewModelProviders.of(this)[AccountViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
}