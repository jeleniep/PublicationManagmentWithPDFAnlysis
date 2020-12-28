package com.jeleniep.publicationManager

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.interfaces.LoginObserver
import com.jeleniep.publicationManager.model.users.UserDTO
import com.jeleniep.publicationManager.model.users.UserRepository
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_login.*
import pub.devrel.easypermissions.EasyPermissions

class LoginActivity : AppCompatActivity(), LoginObserver {

    private lateinit var loginTextView: TextInputEditText
    private lateinit var passwordTextView: TextInputEditText
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferencesHelper =
            SharedPreferencesHelper(PublicationManagerApplication.appContext!!);
        loginTextView = email_text_input_edit_text
        passwordTextView = password_text_input_edit_text
        val loginButton: Button = button_login
        loginButton.setOnClickListener(LoginButtonOnClickListener(this))
        val registerButton: Button = button_register
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, "Can read memory?", 0, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        val authToken = sharedPreferencesHelper.getAuthToken()
        if (authToken != null) {
            Log.d("debug", authToken)
            val loginResponse = UserRepository.checkUser(this)
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)


        }

    }


    inner class LoginButtonOnClickListener(private val loginObserver: LoginObserver) :
        View.OnClickListener {
        override fun onClick(v: View?) {
            val loginResponse = UserRepository.loginUser(
                loginTextView.text.toString(),
                passwordTextView.text.toString(),
                loginObserver
            )

        }
    }

    override fun onUserLoginSuccessful(userDTO: UserDTO) {
        val savedToken = sharedPreferencesHelper.getAuthToken()
        if (userDTO.authToken != null) {
            sharedPreferencesHelper.saveAuthToken(userDTO.authToken!!)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else if (savedToken != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

}
