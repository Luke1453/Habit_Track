package com.lake1453.habit_track.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.lake1453.habit_track.R

class EmailDialog : DialogFragment() {

    //widgets
    private lateinit var mInput: EditText
    private lateinit var mActionOk: TextView
    private lateinit var mActionCancel: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.dialog_email, container, false)

        val mInput = view.findViewById(R.id.input) as EditText
        val mActionOk = view.findViewById(R.id.action_ok) as TextView
        val mActionCancel = view.findViewById(R.id.action_cancel) as TextView

        mActionCancel.setOnClickListener {
            this.dismiss()
        }

        mActionOk.setOnClickListener {
            if(mInput.text.isNotBlank()){

                this.dismiss()
            }
        }


        // Inflate the layout for this fragment
        return view
    }

}