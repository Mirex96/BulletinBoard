package com.example.bulletinboard.dialoghelper

import android.app.AlertDialog
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.bulletinboard.MainActivity
import com.example.bulletinboard.R
import com.example.bulletinboard.accounthelper.AccountHelper
import com.example.bulletinboard.databinding.SignDialogBinding

const val SIGN_UP_STATE_ALERT_DIALOG = 0
const val SIGN_IN_STATE_ALERT_DIALOG = 1

class DialogHelper(act: MainActivity) {
    private val activity = act
    private val accHelper = AccountHelper(act)

    fun signDialog(index: Int) {
        val alertDialog = AlertDialog.Builder(activity)
        val bindingDialog = SignDialogBinding.inflate(activity.layoutInflater)
        alertDialog.setView(bindingDialog.root)

        setDialogState(index, bindingDialog)
        val dialog = alertDialog.create()
        // Добавление прослушивателя нажатия на кнопку
        bindingDialog.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(index, bindingDialog, dialog)
        }
        bindingDialog.btForgetPassword.setOnClickListener {
            setOnClickResetPassword(bindingDialog, dialog)
        }
        dialog.show()
    }

    private fun setOnClickResetPassword(bindingDialog: SignDialogBinding, dialog: AlertDialog?) {
        if (bindingDialog.signEmail.text.isNotEmpty()) {
            activity.authentication.sendPasswordResetEmail(bindingDialog.signEmail.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            activity,
                            R.string.email_reset_password_was_sent,
                            Toast.LENGTH_SHORT
                        ).show()
                        dialog?.dismiss()
                    } else {
                        bindingDialog.tvDialogMassage.visibility = View.VISIBLE
                    }

                }
        }
    }

    private fun setOnClickSignUpIn(index: Int, bindingDialog: SignDialogBinding, dialog: AlertDialog?) {
        dialog?.dismiss()
        if (index == SIGN_UP_STATE_ALERT_DIALOG) {
            accHelper.signUpWithEmail(
                bindingDialog.signEmail.text.toString(),
                bindingDialog.signPassword.text.toString()
            )
        } else {
            accHelper.signInWithEmail(
                bindingDialog.signEmail.text.toString(),
                bindingDialog.signPassword.text.toString()
            )
            Toast.makeText(activity, "Выполнен вход в аккаунт", Toast.LENGTH_SHORT).show()

        }
    }


    // Функция для выбора состояния диалога, для регистрации и для входа
    private fun setDialogState(index: Int, bindingDialog: SignDialogBinding) {
        if (index == SIGN_UP_STATE_ALERT_DIALOG) {
            bindingDialog.signTitle.text = activity.resources.getString(R.string.ac_signe_up)
            bindingDialog.btSignUpIn.text = activity.resources.getString(R.string.sign_up_action)

        } else {
            bindingDialog.signTitle.text = activity.resources.getString(R.string.ac_signe_in)
            bindingDialog.btSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            bindingDialog.btForgetPassword.visibility = View.VISIBLE
        }
    }
}