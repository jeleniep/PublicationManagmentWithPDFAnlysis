package com.jeleniep.publicationManager

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jeleniep.publicationManager.ui.publicationDetails.PublicationDetailsFragment
import com.jeleniep.publicationManager.ui.publicationNew.PublicationNewFragment

import com.jeleniep.publicationManager.ui.utils.Constants.Companion.MESSAGE_FROM_LIST_FRAGMENT
import kotlinx.android.synthetic.main.activity_publication_details.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_details)
        Log.d("debug1", intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT))
        val topToolbar: Toolbar = toolbar
        topToolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_24dp)
        topToolbar.setNavigationOnClickListener { onBackPressed() }
        val toolbarTitle: TextView = toolbar_title
        toolbarTitle.text = getString(R.string.publication_details)
        val transaction = supportFragmentManager.beginTransaction()
        Log.d("debug1", (intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT) == "XD5").toString())
//        val fragment: Fragment = if (intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT) == "XD5") {
//            PublicationDetailsFragment()
//        } else {
//            PublicationNewFragment()
//        }
        val fragment: Fragment = PublicationDetailsFragment(intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT), false)

        transaction.replace(R.id.main_fragment, fragment)

//        transaction.addToBackStack(null)
        transaction.commit()
    }


}
