package br.com.pocintelbrasv10

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    lateinit var textView: TextView
    private val REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = R.string.app_name.toString();
        textView = findViewById(R.id.textView)
        getOperatorData();
    }

    private fun getOperatorData() {
        val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as
                TelephonyManager
        if (ActivityCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_PHONE_STATE), REQUEST_CODE)
        }
        if (telephonyManager == null) {
            textView.text = getString(R.string.data_operator_not_found);
        } else {
            val builder = StringBuilder();
            builder.append(telephonyManager.simOperatorName);
            builder.append(" / ");
            builder.append(telephonyManager.simCountryIso);
            textView.text = builder.toString();
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}