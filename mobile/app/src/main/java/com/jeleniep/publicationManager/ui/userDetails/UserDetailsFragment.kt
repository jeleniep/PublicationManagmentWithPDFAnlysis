package com.jeleniep.publicationManager.ui.userDetails

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.jeleniep.publicationManager.LoginActivity
import com.jeleniep.publicationManager.MainActivity
import com.jeleniep.publicationManager.R
import com.jeleniep.publicationManager.network.users.UserRepository
import com.jeleniep.publicationManager.utils.Helpers

class UserDetailsFragment : Fragment() {

    private lateinit var publicationDetailsViewModel: UserDetailsViewModel
    private lateinit var userEmailTextView: TextInputEditText
    private lateinit var userNameTextView: TextInputEditText
    private lateinit var userIcon: TextView
    private lateinit var saveButton: Button
    private lateinit var logoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        publicationDetailsViewModel =
                ViewModelProviders.of(this).get(UserDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_user_details, container, false)
        userNameTextView = root.findViewById(R.id.username_text_input_edit_text)
        userEmailTextView = root.findViewById(R.id.user_email_text_input_edit_text)
        saveButton = root.findViewById(R.id.save_button)
        logoutButton = root.findViewById(R.id.logout_button)
        userIcon = root.findViewById(R.id.user_icon)
        publicationDetailsViewModel.user.observe(viewLifecycleOwner, Observer {
            userNameTextView.text = Helpers.stringToEditable(it.username)
            userEmailTextView.text = Helpers.stringToEditable(it.email)
            userIcon.text = it.username!!.first().toString()
        })
        (activity as MainActivity).toolbarTitle.text = (activity as MainActivity).getString(R.string.user_details)
        logoutButton.setOnClickListener(LogoutButtonOnClickListener())
        return root
    }

    inner class LogoutButtonOnClickListener() :
        View.OnClickListener {
        override fun onClick(v: View?) {
            UserRepository.logoutUser()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Adds the FLAG_ACTIVITY_NO_HISTORY flag
            startActivity(intent)
            activity!!.finish()

        }
    }
}
