package com.bangkit.manduin.data.repository

import android.util.Log
import com.bangkit.manduin.model.UserSessionModel
import com.bangkit.manduin.utils.StateAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthRepository (
    private val auth: FirebaseAuth = Firebase.auth,
    private val database: FirebaseDatabase = Firebase.database
) {
    fun loginWithGoogle(idToken: String) = flow {
        emit(StateAuth.Loading)
        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential).await()
            Log.d(TAG, "Login With Google Success")

            val user = auth.currentUser

            saveUserToRealtimeDatabase(user, true)
            emit(StateAuth.Success)
        } catch (e: Exception) {
            val message = e.localizedMessage?.toString() ?: e.message.toString()
            Log.d(TAG, "Login With Google Failed: $message")
            emit(StateAuth.Error(message))
        }
    }

    fun loginWithEmailAndPassword(email: String, password: String) = flow {
        emit(StateAuth.Loading)
        try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "Login With EmailPassword Success")
            saveUserToRealtimeDatabase(auth.currentUser, true)
            emit(StateAuth.Success)
        } catch (e: Exception) {
            val message = e.localizedMessage?.toString() ?: e.message.toString()
            Log.d(TAG, "Login With Google Failed: $message")
            emit(StateAuth.Error(message))
        }
    }

    fun registerUser(fullname: String, email: String, password: String) = flow {
        emit(StateAuth.Loading)
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            val update = userProfileChangeRequest { displayName = fullname }

            user!!.updateProfile(update).addOnCompleteListener {
                if (it.isSuccessful) Log.d(TAG, "Success Update Display Name: ${user.displayName} ")
                saveUserToRealtimeDatabase(auth.currentUser, true)
            }
            emit(StateAuth.Success)
        } catch (e: Exception) {
            Log.d(TAG, "Register User With EmailPassword Failed: ${e.localizedMessage?.toString()}")
            emit(StateAuth.Error(e.localizedMessage?.toString() ?: e.message.toString()))
        }
    }

    fun saveUserToRealtimeDatabase(user: FirebaseUser?, isLogin: Boolean) {
        val userData = UserSessionModel(
            user?.uid.toString(),
            user?.displayName.toString(),
            user?.email.toString(),
            isLogin
        )

        val dbRef = database.reference.child(DB_CHILD_USERS)
        val userID = user?.uid.toString()

        dbRef.child(userID).setValue(userData)
            .addOnCompleteListener {
                Log.d(TAG, "Success Save Userdata to Realtime Database")
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to Save Userdata to Realtime Database")
            }
    }

    fun sendEmailResetPassword(email: String) = flow {
        emit(StateAuth.Loading)
        try {
            auth.sendPasswordResetEmail(email).await()
            Log.d(TAG, "Send ResetPassword Email Success")
            emit(StateAuth.Success)
        } catch (e: Exception) {
            val message = e.localizedMessage?.toString() ?: e.message.toString()
            Log.d(TAG, "Failed to Send ResetPassword Email : $message")
            emit(StateAuth.Error(message))
        }
    }

    companion object {
        private const val TAG = "AuthRepository"
        private const val DB_CHILD_USERS = "users"
    }
}