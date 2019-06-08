package com.example.hyperlocal.intro

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.example.hyperlocal.*
import com.example.hyperlocal.extensions.*
import com.example.hyperlocal.extensions.Firebase.auth
import com.example.hyperlocal.model.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*
import com.crashlytics.android.Crashlytics
import com.example.hyperlocal.base.BaseActivity
import io.fabric.sdk.android.Fabric



class LoginActivity : BaseActivity() {

    private var user = User()

    companion object {
        private const val RC_SIGN_IN = 123
    }

    private fun createGoogleLoginIntent(): Intent {
        return AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                Arrays.asList(
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .setIsSmartLockEnabled(true)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(auth.currentUser != null) {
            startApp()
        } else {
            sign_in_button.setOnClickListener {
                startActivityForResult(createGoogleLoginIntent(), RC_SIGN_IN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if(resultCode == Activity.RESULT_OK) {

                user.ID = auth.currentUser!!.uid
                user.name = auth.currentUser!!.displayName.toString()
                user.email = auth.currentUser!!.email.toString()

                usersCollection.document(auth.currentUser!!.email.toString())
                    .set(user)
                    .addOnSuccessListener {
                        logDebug("Document Snapshot added with ID: " + user.ID)
                        //setUpPermissions()
                        startApp()
                    }
                    .addOnFailureListener { e ->
                        logError("Error adding document", e)
                    }
            }
        } else {
            snackbar("Could not login. Please try again later.")
        }
    }

    private fun setUpPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            logError(Error("Permission to record denied"))
        }
    }

    private fun startApp() {
        snackbar("Successful sign in!")
        finishAndStart(MainActivity::class.java)
    }
}
