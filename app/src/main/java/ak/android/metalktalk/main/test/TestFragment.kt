package ak.android.metalktalk.main.test

import ak.android.metalktalk.R
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {
    companion object {
        internal val TAG = TestFragment::class.java.simpleName
    }

    private val RC_RECORD_AUDIO = 100

    private val viewModel by lazy {
        ViewModelProviders.of(this)[TestViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_test, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Request permission to record audio
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.RECORD_AUDIO), RC_RECORD_AUDIO
        )

        fab_record.setOnTouchListener { v, event ->
            // Hold to start recording, release to stop recording
            if (event.action == MotionEvent.ACTION_DOWN) {
                tv_record_status.text = getString(R.string.test_record_started)
                viewModel.startRecording()
            } else if (event.action == MotionEvent.ACTION_UP) {
                tv_record_status.text = getString(R.string.test_record_stopped)
                viewModel.stopRecording()
            }
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(requireContext(), "Izin untuk merekam ditolak", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}