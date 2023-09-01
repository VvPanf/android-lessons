package com.example.android_lessons

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class LifeLongQuestionFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = requireActivity()
        val builder = AlertDialog.Builder(activity)
            .setTitle("Alert!!!")
            .setPositiveButton("Ok") { dialog, id ->
                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            .setNeutralButton("Retry") { dialog, id ->
                dialog.cancel()
            }
        return builder.create()
    }
}