package com.jeleniep.publicationManager.ui.publicationsList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jeleniep.publicationManager.MainActivity
import com.jeleniep.publicationManager.PublicationDetailsActivity
import com.jeleniep.publicationManager.R
import com.jeleniep.publicationManager.ui.utils.Constants.Companion.MESSAGE_FROM_LIST_FRAGMENT
import kotlin.properties.Delegates


class PublicationsListFragment : Fragment(), PublicationAdapter.OnItemClickListener {

    private lateinit var publicationsListViewModel: PublicationsListViewModel
    private val list: ArrayList<PublicationListItem> = ArrayList<PublicationListItem>();
    private val adapter = PublicationAdapter(list, this)
    private var isStopped by Delegates.notNull<Boolean>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        publicationsListViewModel =
            ViewModelProvider(this).get(PublicationsListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_publications_list, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.publications_recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.setHasFixedSize(true)
        publicationsListViewModel.publications.observe(viewLifecycleOwner, Observer {
            adapter.publicationList = it
        })
        val activity: Activity = this.requireActivity()
        (activity as MainActivity).topToolbar.collapseIcon =
            activity.getDrawable(R.drawable.ic_arrow_back_black_24dp)
        isStopped = false
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).toolbarTitle.text =
            (activity as MainActivity).getString(R.string.app_name)
        if (!(activity as MainActivity).topToolbar.menu.hasVisibleItems()) {
            (activity as MainActivity).topToolbar.inflateMenu(R.menu.menu_items)
            (activity as MainActivity).topToolbar.setOnMenuItemClickListener(OnAddClickListener())
            if (isStopped) {
                publicationsListViewModel.refreshList()
                isStopped = false
            }
        }
    }


    override fun onStop() {
        isStopped = true
        super.onStop()
        (activity as MainActivity).topToolbar.menu.clear()
    }

    inner class OnAddClickListener : Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            if (item != null) {
                when (item.itemId) {
                    R.id.action_add_publication -> {
                        val intent = Intent(activity, PublicationDetailsActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
            return true
        }
    }

    override fun onItemClick(position: Int) {
        val clickedItem = adapter.publicationList[position]
        adapter.notifyItemChanged(position)
        val message = clickedItem._id
        val intent = Intent(this.activity, PublicationDetailsActivity::class.java).apply {
            putExtra(MESSAGE_FROM_LIST_FRAGMENT, message)
        }
        startActivity(intent)
    }


}
