package com.example.mrt.util

import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.TextView


fun Fragment.initToolbar(toolbar: Toolbar) {
    val activity = requireActivity() as AppCompatActivity
    activity.setSupportActionBar(toolbar)
    activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    toolbar.setNavigationOnClickListener {
        activity.onBackPressedDispatcher.onBackPressed()
    }
}

fun Fragment.showBottomSheet(message: String) {
    val dialog = BottomSheetDialog(requireContext())
    val view = LayoutInflater.from(requireContext()).inflate(
        com.example.mrt.R.layout.layout_bottom_sheet_message,
        null
    )

    val textView = view.findViewById<TextView>(com.example.mrt.R.id.textMessage)
    textView?.text = message

    dialog.setContentView(view)
    dialog.show()
}



fun Fragment.toast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}
