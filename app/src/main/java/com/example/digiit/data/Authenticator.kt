package com.example.digiit.data

import com.example.digiit.data.user.RemoteUser
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


typealias AuthCallback = (error: String?, user: RemoteUser?) -> Unit


class Authenticator(private val firebase: FirebaseApp) {
    private val auth = Firebase.auth(firebase)

    fun signIn(email: String, password: String, callback: AuthCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onAuthResult(task, callback) }
    }

    fun register(email: String, password: String, callback: AuthCallback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onAuthResult(task, callback) }
    }

    private fun onAuthResult(task: Task<AuthResult>, callback: AuthCallback) {
        if (task.isSuccessful && task.result.user != null) {
            callback(task.exception.toString(), RemoteUser(firebase, task.result.user!!))
        } else {
            callback(task.exception.toString(), null)
        }
    }
}