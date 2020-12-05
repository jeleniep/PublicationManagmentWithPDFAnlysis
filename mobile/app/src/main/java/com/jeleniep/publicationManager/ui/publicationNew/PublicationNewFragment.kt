package com.jeleniep.publicationManager.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jeleniep.publicationManager.R

class PublicationDetailsFragment : Fragment() {

    private lateinit var publicationDetailsViewModel: PublicationDetailsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        publicationDetailsViewModel =
                ViewModelProviders.of(this).get(PublicationDetailsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_publication, container, false)
        val textView: TextView = root.findViewById(R.id.text_test)
        publicationDetailsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
