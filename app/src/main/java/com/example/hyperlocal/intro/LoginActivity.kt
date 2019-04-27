package com.example.hyperlocal.intro

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hyperlocal.*
import com.example.hyperlocal.extensions.Firebase.auth
import com.example.hyperlocal.extensions.finishAndStart
import com.example.hyperlocal.extensions.snackbar
import com.example.hyperlocal.extensions.usersCollection
import com.example.hyperlocal.model.User
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginActivity : AppCompatActivity() {

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
                        Log.d("UserAdded", "Document Snapshot added with ID: " + user.ID)
                        startApp()
                    }
                    .addOnFailureListener { e ->
                        Log.e("UserNotAdded", "Error adding document", e)
                    }
            }
        } else {
            snackbar("Could not login. Please try again later.")
        }
    }

    private fun startApp() {
        snackbar("Successful sign in!")
        finishAndStart(MainActivity::class.java)
    }
}