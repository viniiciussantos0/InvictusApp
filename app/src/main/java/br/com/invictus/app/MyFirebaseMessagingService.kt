package br.com.invictus.app

import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    val TAG = "firebase"

    // recebe o novo token criado
    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d(TAG, "Novo token: $p0")

        // Guarda o token em Shared Preferences
        Prefs.setString("FB_TOKEN", p0!!)
    }

    private fun showNotification(mensagemRemota: RemoteMessage) {
        val intent = Intent(this, ProdutoActivity::class.java)

        val titulo = mensagemRemota?.notification?.title
        val corpo = mensagemRemota?.notification?.body

        NotificationUtils.create(1, intent, titulo!!, corpo!!)

    }

    // recebe a notificação de push
    override fun onMessageReceived(p0: RemoteMessage?) {
        Log.d(TAG, "onMessageReceived")

        // verifica se a mensagem recebida do firebase é uma notificação
        if (p0?.notification != null) {
            val titulo = p0?.notification?.title
            val corpo = p0?.notification?.body
            showNotification(p0)

            Log.d(TAG, "Titulo da mensagem: $titulo")
            Log.d(TAG, "Corpo da mensagem: $corpo")
        }
    }
}