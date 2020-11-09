package br.com.invictus.app

import android.app.Application
import java.lang.IllegalStateException

class InvictusApplication: Application(){

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: InvictusApplication? = null

        fun getInstance(): InvictusApplication {
            if (appInstance == null) {
                throw IllegalStateException("Configure o application no Manifest")
            }
            return appInstance!!
        }
    }
}