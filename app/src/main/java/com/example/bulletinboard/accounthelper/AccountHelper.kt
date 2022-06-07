package com.example.bulletinboard.accounthelper

import android.widget.Toast
import com.example.bulletinboard.MainActivity
import com.example.bulletinboard.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

const val SiGN_IN_REQUEST_CODE = 100
const val GOOGLE_SIGN_IN_REQUEST_CODE = 101

class AccountHelper(act: MainActivity) {
    private val activity = act
    private lateinit var signInClient: GoogleSignInClient
    fun signUpWithEmail(email: String, password: String) {
        // проверка на то  Если email и password не пустой, то регистриреум пользователя
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.authentication.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    // isSuccessful - это проверка, если успешно то отправляем
                    if (task.isSuccessful) {
                        sendEmailVerification(task.result?.user!!)
                        activity.uiUpdate(task.result?.user)

                    } else {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.sing_up_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


        }

    }

    fun signInWithEmail(email: String, password: String) {
        // проверка на то  Если email и password не пустой, то регистриреум пользователя
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.authentication.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    // isSuccessful - это проверка, если успешно то отправляем
                    if (task.isSuccessful) {
                        activity.uiUpdate(task.result?.user)

                    } else {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.sing_in_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }


        }

    }

    // Функция для регистрации по гугл аккаунту
    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.default_web_client_id)).build()
        return GoogleSignIn.getClient(activity, gso)

    }

    // Функция, которая будет включаться после нажатия на кнопку войти на гугл аккаунт
    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intent = signInClient.signInIntent
        activity.startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    // Создаем функцию для отправки подтверждающего письма
    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.send_verification_done),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.send_verification_error),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


    }
}