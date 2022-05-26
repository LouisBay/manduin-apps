package com.bangkit.manduin.data.repository

import android.util.Log
import com.bangkit.manduin.utils.StateAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthRepository {

    fun loginWithGoogle(idToken: String) = flow {
        emit(StateAuth.Loading)

        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(credential).await()
            Log.d(TAG, "Login With Google Success")
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
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            Log.d(TAG, "Login With EmailPassword Success")
            emit(StateAuth.Success)
        } catch (e: Exception) {
            val message = e.localizedMessage?.toString() ?: e.message.toString()
            Log.d(TAG, "Login With Google Failed: $message")
            emit(StateAuth.Error(message))
        }
    }

    fun registerUser(fullname: String, email: String, password: String) = flow {
        emit(StateAuth.Loading)
        val auth = FirebaseAuth.getInstance()
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
            val user = auth.currentUser
            val update = userProfileChangeRequest { displayName = fullname }

            user!!.updateProfile(update).addOnCompleteListener {
                if (it.isSuccessful) Log.d(TAG, "Success Update Display Name: ${user.displayName} ")
            }

            saveUserToFirestore(fullname, email)
            emit(StateAuth.Success)
        } catch (e: Exception) {
            Log.d(TAG, "Register User With EmailPassword Failed: ${e.localizedMessage?.toString()}")
            emit(StateAuth.Error(e.localizedMessage?.toString() ?: e.message.toString()))
        }
    }

    private fun saveUserToFirestore(fullname: String, email:String) {
        val firebaseUser = Firebase.auth.currentUser
        val user = hashMapOf(
            KEY_EMAIL to email,
            KEY_FULLNAME to fullname,
            KEY_UID to firebaseUser?.uid.toString(),
            KEY_ISLOGIN to true,
        )

        FirebaseFirestore.getInstance().collection(DB_USERS_COLLECTION)
            .document(firebaseUser?.uid.toString())
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "Success Save Userdata to Firestore")
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to Save Userdata to Firestore")
            }
    }

    companion object {
        const val KEY_EMAIL = "email"
        const val KEY_FULLNAME = "fullname"
        const val KEY_UID = "uid"
        const val KEY_ISLOGIN = "isLogin"
        private const val TAG = "AuthRepository"
        private const val DB_USERS_COLLECTION = "users"
    }
}