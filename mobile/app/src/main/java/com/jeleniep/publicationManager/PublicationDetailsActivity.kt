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

class PublicationDetailsActivity : AppCompatActivity() {

    lateinit var topToolbar: Toolbar
    lateinit var toolbarTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publication_details)
        val publicationId = intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT)
        topToolbar = toolbar
        topToolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_black_24dp)
        topToolbar.setNavigationOnClickListener { onBackPressed() }
        toolbarTitle = toolbar_title

        toolbarTitle.text = if (publicationId != null) getString(R.string.publication_details) else getString(R.string.add_new_publication)
        val transaction = supportFragmentManager.beginTransaction()
//        val fragment: Fragment = if (intent.getStringExtra(MESSAGE_FROM_LIST_FRAGMENT) == "XD5") {
//            PublicationDetailsFragment()
//        } else {
//            PublicationNewFragment()
//        }
        val fragment: Fragment = PublicationDetailsFragment(publicationId, publicationId == null)

        transaction.replace(R.id.main_fragment, fragment)

//        transaction.addToBackStack(null)
        transaction.commit()
    }


}
