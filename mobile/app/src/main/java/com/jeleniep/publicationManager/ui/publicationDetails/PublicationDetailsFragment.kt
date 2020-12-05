package com.jeleniep.publicationManager.ui.publicationDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.jeleniep.PublicationManagerApplication
import com.jeleniep.publicationManager.R
import com.jeleniep.publicationManager.interfaces.PublicationListObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.model.publications.PublicationDTO
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.utils.Helpers
import com.jeleniep.publicationManager.utils.SharedPreferencesHelper


class PublicationDetailsFragment(private var _id: String?, private var isEditionActive: Boolean) :
    Fragment(), PublicationListObserver {

    private lateinit var publicationDetailsViewModel: PublicationDetailsViewModel
    private lateinit var viewModelFactory: PublicationDetailsViewModelFactory
    private lateinit var publicationNameTextView: TextInputEditText
    private lateinit var publicationAuthorsTextView: TextInputEditText
    private lateinit var publicationDescriptionTextView: TextInputEditText
    private lateinit var publicationTagsTextView: TextInputEditText
    private lateinit var unlockEditionButton: ImageButton
    private lateinit var saveButton: Button
    private lateinit var root: View
    private lateinit var outAnimation: Animation
    private lateinit var inAnimation: Animation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFactory = PublicationDetailsViewModelFactory((_id))
        publicationDetailsViewModel = ViewModelProvider(this, viewModelFactory)
            .get(PublicationDetailsViewModel::class.java)
        root = inflater.inflate(R.layout.fragment_publication_details, container, false)
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        unlockEditionButton = root.findViewById(R.id.unlock_edition_button)
        saveButton = root.findViewById(R.id.save_button)
        initiateUnlockEditionButton(isEditionActive)

        publicationNameTextView =
            root.findViewById(R.id.publication_name_text_input_edit_text)
        publicationAuthorsTextView =
            root.findViewById(R.id.publication_authors_text_input_edit_text)
        publicationDescriptionTextView =
            root.findViewById(R.id.publication_description_text_input_edit_text)
        publicationTagsTextView =
            root.findViewById(R.id.publication_tags_text_input_edit_text)

        changeEditionState(isEditionActive)
        if (isEditionActive) {
            unlockEditionButton.setImageResource(R.drawable.ic_lock_outline_open_black_24dp);
        }

        publicationDetailsViewModel.publication.observe(viewLifecycleOwner, Observer {
            publicationNameTextView.text = Helpers.stringToEditable(it.name)
            publicationAuthorsTextView.text = Helpers.listOfStringsToEditable(it.authors)
            publicationDescriptionTextView.text = Helpers.stringToEditable(it.description);
//            publicationTagsTextView.text = Editable.Factory.getInstance().newEditable(it.name);
        })
        saveButton.setOnClickListener(SaveButtonOnClickListener(this))

        return root

    }

    inner class UnlockEditionOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val layout: RelativeLayout = root.findViewById(R.id.main_layout)
            layout.requestFocus()
            changeEditionState(!isEditionActive)
            unlockEditionButton.startAnimation(outAnimation);
        }
    }

    inner class SaveButtonOnClickListener(private val publicationListObserver: PublicationListObserver) :
        View.OnClickListener {
        override fun onClick(v: View?) {
            val layout: RelativeLayout = root.findViewById(R.id.main_layout)
            layout.requestFocus()
            changeEditionState(!isEditionActive)

            unlockEditionButton.startAnimation(outAnimation);
            val sharedPreferencesHelper =
                SharedPreferencesHelper(PublicationManagerApplication.appContext!!)

            val token = sharedPreferencesHelper.getAuthToken()
            val publicationDTO = PublicationDTO().apply {
                name = publicationNameTextView.text.toString()
                description = publicationDescriptionTextView.text.toString()
                authors = publicationAuthorsTextView.text.toString().split(", ")
            }
            if (_id != null) {
                PublicationRepository.editPublication(token, _id!!,  publicationDTO, publicationListObserver)

            } else {
                PublicationRepository.addPublication(token, publicationDTO, publicationListObserver)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        val layout: RelativeLayout = root.findViewById(R.id.main_layout)
        layout.requestFocus()
    }

    private fun changeEditionState(state: Boolean) {
        isEditionActive = state
        publicationNameTextView.isEnabled = state
        publicationAuthorsTextView.isEnabled = state
        publicationDescriptionTextView.isEnabled = state
        saveButton.isEnabled = state
        publicationTagsTextView.isEnabled = state

    }

    private fun initiateUnlockEditionButton(state: Boolean) {

        unlockEditionButton.setOnClickListener(UnlockEditionOnClickListener())
        outAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation) {

                // Modify the resource of the ImageButton
                val resId: Int =
                    if (isEditionActive) R.drawable.ic_lock_outline_open_black_24dp else R.drawable.ic_lock_outline_closed_black_24dp
                unlockEditionButton.setImageResource(resId);

                // Create the new Animation to apply to the ImageButton.
                unlockEditionButton.startAnimation(inAnimation);
            }
        })
    }

    override fun onPublicationUpdateSuccess(publicationDTO: PublicationDTO, type: String) {
        val actionName = if (type == "create") "added" else "updated"
        var toast: Toast =
            Toast.makeText(
            context,
            "Publication ${publicationDTO.name} successfully $actionName",
            Toast.LENGTH_LONG
        )

        toast.show()
        activity?.onBackPressed()
    }

    override fun onPublicationUpdateFail(errorResponse: ErrorResponse?, type: String) {
        if (errorResponse != null) {
            val toast = Toast.makeText(
                context,
                errorResponse.message,
                Toast.LENGTH_LONG
            )
            toast.show()
        }
        changeEditionState(!isEditionActive)

    }

}
