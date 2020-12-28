package com.jeleniep.publicationManager.model.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.interfaces.LoginObserver
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserRepository {
    private val userService: UserService = UserService.create()

    private val sharedPreferencesHelper =
        SharedPreferencesHelper(PublicationManagerApplication.appContext!!)

    fun loginUser(login: String, password: String, loginObserver: LoginObserver): UserDTO {
        var userData = UserDTO()
        val loginBody = LoginBody(login, password)
        userService.login(loginBody).enqueue(object : Callback<UserDTO> {
            override fun onResponse(
                call: Call<UserDTO>,
                response: Response<UserDTO>
            ) {
                if (response.code() == 200) {
                    val userResponse = response.body()!!
                    Log.d("debug123a", userResponse!!.authToken)
                    userData = userResponse!!
                    loginObserver.onUserLoginSuccessful(userData)
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("debug", t.message);
            }

        })
        return userData
    }

    fun logoutUser(): UserDTO {
        val authToken = sharedPreferencesHelper.getAuthToken()
        var userData = UserDTO()
        userService.logout("Bearer $authToken").enqueue(object : Callback<UserDTO> {
            override fun onResponse(
                call: Call<UserDTO>,
                response: Response<UserDTO>
            ) {
                if (response.code() == 200) {
                    val userResponse = response.body()!!
                    userData = userResponse!!
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("debug", t.message);
            }

        })
        return userData
    }

    fun checkUser(loginObserver: LoginObserver): UserDTO {
        val authToken = sharedPreferencesHelper.getAuthToken()
        var userData = UserDTO()
        userService.checkUser("Bearer $authToken").enqueue(object : Callback<UserDTO> {
            override fun onResponse(
                call: Call<UserDTO>,
                response: Response<UserDTO>
            ) {
                if (response.code() == 200) {
                    val userResponse = response.body()!!
                    userData = userResponse!!
                    loginObserver.onUserLoginSuccessful(userData)
                }
            }

            override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                Log.d("debug", t.message);
            }

        })
        return userData
    }

    fun getUserDetails(id: String): MutableLiveData<UserDTO> {
        val authToken = sharedPreferencesHelper.getAuthToken()

        val userData: MutableLiveData<UserDTO> = MutableLiveData()
        userService.getUserDetails("Bearer $authToken", id)
            .enqueue(object : Callback<UserDTO> {
                override fun onResponse(
                    call: Call<UserDTO>,
                    response: Response<UserDTO>
                ) {
                    if (response.code() == 200) {
                        val userResponse = response.body()!!
                        userData.value = userResponse!!
                    }
                }

                override fun onFailure(call: Call<UserDTO>, t: Throwable) {
                    userData.value = null
                }

            })
        return userData
    }


}