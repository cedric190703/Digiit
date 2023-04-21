package com.example.digiit.data

import com.example.digiit.data.user.LocalUser
import com.example.digiit.data.user.RemoteUser
import com.example.digiit.data.user.User
import com.example.digiit.utils.ActionCallback
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.io.File


typealias AuthCallback = (error: Exception?, user: RemoteUser?) -> Unit


class UserProvider(private val firebase: FirebaseApp) {
    private val auth = Firebase.auth(firebase)
    private val users = ArrayList<User>()

    init {
        println("UserProvider created");
    }

    var user: User? = null

    fun loginRemoteUser(email: String, password: String, callback: AuthCallback) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onAuthResult(task, callback) }
    }

    fun registerRemoteUser(email: String, password: String, callback: AuthCallback) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onAuthResult(task, callback) }
    }

    /*fun load() {
        val file = firebase.applicationContext.filesDir.resolve("users.json")
        val jsonRoot = JSONObject(file.readText())
        val jsonUsers = jsonRoot.getJSONArray("users")
        for (i in 0 until jsonUsers.length()) {
            val user = jsonUsers.getJSONObject(i)
            if (user.getBoolean("local")) {
                //users.add(LocalUser(null, user.getString("id")))
            } else {
                //users.add(RemoteUser())
            }
        }
    }*/

    private fun onAuthResult(task: Task<AuthResult>, callback: AuthCallback) {
        if (task.isSuccessful && task.result.user != null) {
            val remoteUser = RemoteUser(firebase, task.result.user!!)
            user = remoteUser
            remoteUser.load { err1 ->
                if (err1 != null) callback(err1, remoteUser)
                else remoteUser.queryTickets { err2 ->
                    if (err2 != null) callback(err2, remoteUser)
                    else remoteUser.queryWallets { err3 ->
                        callback(err3, remoteUser)
                    }
                }
            }
        } else {
            callback(task.exception, null)
        }
    }

    fun sendPasswordResetEmail(email: String, callback: ActionCallback) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            callback(task.exception)
        }
    }
}