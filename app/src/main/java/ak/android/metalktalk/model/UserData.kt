package ak.android.metalktalk.model

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

data class UserData(
    val namaLengkap: String?,
    val jenisKelamin: String?,
    val tanggalLahir: Timestamp?
) {
    companion object {
        val FIELD_NAMA_LENGKAP = "nama_lengkap"
        val FIELD_JENIS_KELAMIN = "jenis_kelamin"
        val FIELD_TANGGAL_LAHIR = "tanggal_lahir"
    }
}

fun String.toFirestoreTimestamp(): Timestamp {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    return Timestamp(dateFormat.parse(this))
}

fun Timestamp.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
    return dateFormat.format(this.toDate())
}