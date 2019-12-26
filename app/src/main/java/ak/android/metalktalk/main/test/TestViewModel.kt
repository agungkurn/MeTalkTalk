package ak.android.metalktalk.main.test

import android.app.Application
import android.media.MediaRecorder
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.IOException

class TestViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>()

    private val storage = FirebaseStorage.getInstance().reference
    private val user = FirebaseAuth.getInstance().currentUser

    private var recorder: MediaRecorder? = null

    private val path = "${context.cacheDir.absolutePath}/test.aac"

    internal fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS)
            setOutputFile(path)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(TestFragment.TAG, "Error recording: ${e.localizedMessage}")
            }
        }

        recorder?.start()
    }

    internal fun stopRecording() {
        recorder?.apply {
            stop()
            release()
        }
        recorder = null

        Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()

        uploadFile()
    }

    private fun uploadFile() {
        val fileToUpload = File(path)
        val fileUri = Uri.fromFile(fileToUpload)

        Toast.makeText(context, "Mengirim rekaman...", Toast.LENGTH_SHORT).show()
        storage.child("test/${user?.email}/test.aac")
            .putFile(fileUri)
            .addOnFailureListener {
                Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                Log.e(TestFragment.TAG, "Error upload: ${it.localizedMessage}")
            }.addOnSuccessListener {
                Toast.makeText(context, "Memproses rekaman Anda...", Toast.LENGTH_SHORT).show()
            }
    }
}