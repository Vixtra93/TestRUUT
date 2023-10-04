import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context?.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    this?.let { Toast.makeText(it, text, duration).show() }