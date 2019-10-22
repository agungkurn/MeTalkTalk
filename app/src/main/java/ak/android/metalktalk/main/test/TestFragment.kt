package ak.android.metalktalk.main.test

import ak.android.metalktalk.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {
    private val testViewModel by lazy {
        ViewModelProviders.of(this)[TestViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fab_record.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                tv_record_status.text = getString(R.string.test_record_started)
            } else if (event.action == MotionEvent.ACTION_UP) {
                tv_record_status.text = getString(R.string.test_record_stopped)
                Toast.makeText(requireContext(), "Harap tunggu", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }
}