package com.lake1453.habit_track.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.lake1453.habit_track.R
import kotlinx.android.synthetic.main.dialog_empty_fields.view.*

class EmptyFieldsDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.dialog_empty_fields, container, false)

        view.ok_button.setOnClickListener {
            dismiss()
        }

        return view
    }
}