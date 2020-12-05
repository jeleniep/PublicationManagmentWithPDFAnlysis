package com.jeleniep.publicationManager.ui.publicationNew

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jeleniep.publicationManager.R

class PublicationNewFragment : Fragment() {

    private lateinit var publicationDetailsViewModel: PublicationNewViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        publicationDetailsViewModel =
                ViewModelProviders.of(this).get(PublicationNewViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_new_publication, container, false)
        val textView: TextView = root.findViewById(R.id.text_test)
        publicationDetailsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
