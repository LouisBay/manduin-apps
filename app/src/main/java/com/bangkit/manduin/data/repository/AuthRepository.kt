package com.bangkit.manduin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.bangkit.manduin.model.UserSessionModel
import com.bangkit.manduin.utils.StateAuth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(private val userPreferences: UserPreferences) {
    private val _stateAuth = MutableLiveData<StateAuth>()
    private val stateAuth: LiveData<StateAuth> = _stateAuth

    fun loginWithGoogle(idToken: String): LiveData<StateAuth> {
        _stateAuth.value = StateAuth.Loading
        Log.d("TEST", "token:$idToken")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TEST", "MASUK SUCCESS")
                    _stateAuth.value = StateAuth.Success
                } else {
                    _stateAuth.value = StateAuth.Error(task.exception?.localizedMessage.toString())
                    Log.d("TEST", "MASUK GAGAL")
                }
            }

        return stateAuth
    }
}