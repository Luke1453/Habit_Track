package com.lake1453.habit_track.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.lake1453.habit_track.R
import com.lake1453.habit_track.dialogs.EmptyFieldsDialog
import com.lake1453.habit_track.helperActivities.SignInActivity
import com.lake1453.habit_track.model.UserInfo
import com.lake1453.habit_track.viewModel.HabitViewModel
import com.lake1453.habit_track.viewModel.UserInfoViewModel

class Settings : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var viewModel: UserInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this).get(UserInfoViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_settings, container, false)
        val logOutBtn: Button = view.findViewById(R.id.log_out_btn)
        val setUserInfoBtn: Button = view.findViewById(R.id.set_user_info_btn)
        val userNameEditTextView: EditText = view.findViewById(R.id.user_name_text_field)
        val userSurnameEditText: EditText = view.findViewById(R.id.user_surname_text_field)
        logOutBtn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        setUserInfoBtn.setOnClickListener {
            val userName = userNameEditTextView.text.toString()
            val userSurname = userSurnameEditText.text.toString()

            if(userName.isNotBlank() && userSurname.isNotBlank()){
                val userInfo = UserInfo(userName,userSurname)
                viewModel.setUserInfo(userInfo)
            }else{
                val emptyFieldsDialog = EmptyFieldsDialog()
                emptyFieldsDialog.show(activity?.supportFragmentManager!!, "emptyFields")
            }

        }
        // Inflate the layout for this fragment
        return view
    }

}