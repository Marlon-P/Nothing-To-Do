package com.gmail.hitoridevelop.nothingtodo.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.view.View
import com.gmail.hitoridevelop.nothingtodo.R

class LoadingDialog(val activity: Activity) {

    private lateinit var  dialog: AlertDialog


    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity)

        builder.setView(View.inflate(activity.applicationContext, R.layout.progress_bar, null))
        builder.setCancelable(true)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog() {
        dialog.dismiss()
    }
}