package br.com.invictus.app

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONObject
import java.lang.Exception

object UserService {
    private const val host = "http://invictusapp2.pythonanywhere.com"

    fun login(context: Context, username: String, password: String): Boolean {
        if (AndroidUtils.isInternetDisponivel(context)) {
            val url = "$host/login"

            val hashMap: HashMap<String, String> = HashMap<String, String>();
            hashMap["username"] = username;
            hashMap["password"] = password;

            return try {
                val json = HttpHelper.post(url, Gson().toJson(hashMap).toString());
                true
            } catch (err: Exception) {
                false;
            }
        } else {
            return false
        }
    }
}