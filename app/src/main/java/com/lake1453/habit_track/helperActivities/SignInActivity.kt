package com.lake1453.habit_track.helperActivities

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.lake1453.habit_track.MainActivity
import com.lake1453.habit_track.R
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignIn: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mAuth = FirebaseAuth.getInstance()

        // Configuring Sing in with google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignIn = GoogleSignIn.getClient(this, gso)
        // binging button to sign in func
        sign_in_google.setOnClickListener {
            doGoogleAuth()
        }

        // Configuring Sign in with email
        sign_in_email.setOnClickListener {
            val email: String = email_edit_text.text.toString()
            val password: String = password_edit_text.text.toString()
            if (email.isEmailValid() && password.isNotBlank()) {
                doEmailAuth(email, password)
            }
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    /////////////////////////////////////////////////////////// Firebase google Auth ////////////////////////////////////////////////////////////////////////////////

    private fun doGoogleAuth() {
        val signInIntent = googleSignIn.signInIntent
        googleSignInIntentResult.launch(signInIntent)
    }

    private var googleSignInIntentResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val exception = task.exception
                if (task.isSuccessful) {
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        val account = task.getResult(ApiException::class.java)!!
                        Log.d("googleSignIn", "firebaseAuthWithGoogle:" + account.id)
                        firebaseAuthWithGoogle(account.idToken!!)
                    } catch (e: ApiException) {
                        // Google Sign In failed, update UI appropriately
                        Log.w("googleSignIn", "Google sign in failed", e)
                        // ...
                    }
                } else {
                    Log.w("googleSignIn", exception.toString())
                }
            }
        }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("googleSignIn", "signInWithCredential:success")
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainActivityIntent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("googleSignIn", "signInWithCredential:failure", task.exception)
                }
            }
    }

    ///////////////////////////////////////////////////////////////////////////// Firebase Email Auth /////////////////////////////////////////////////////////////

    private fun doEmailAuth(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { signInTask ->
                if (signInTask.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val mainActivityIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainActivityIntent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure, signing up user", signInTask.exception)
                    mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { createUserTask ->
                            if (createUserTask.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success")
                                val mainActivityIntent = Intent(this, MainActivity::class.java)
                                startActivity(mainActivityIntent)
                                finish()
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", createUserTask.exception)
                            }
                        }
                }
            }


    }

}