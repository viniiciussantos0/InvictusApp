package br.com.invictus.app

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun validade(activity: Activity, requestCode: Int, vararg permission: String) : Boolean {
        val list = ArrayList<String>()

        for (p in permission) {
            val ok = ContextCompat.checkSelfPermission(activity, p) == PackageManager.PERMISSION_GRANTED

            if (!ok) {
                list.add(p)
            }
        }
        if (list.isEmpty()) return true

        val newP = arrayOfNulls<String>(list.size)
        list.toArray(newP)

        ActivityCompat.requestPermissions(activity, newP, requestCode)
        return false
    }
}