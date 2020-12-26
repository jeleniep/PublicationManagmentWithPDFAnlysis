package com.jeleniep.publicationManager.ui.publicationDetails

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.jeleniep.publicationManager.PublicationDetailsActivity
import com.jeleniep.publicationManager.R
import com.jeleniep.publicationManager.interfaces.OpenPdfCallback
import com.jeleniep.publicationManager.interfaces.RequestObserver
import com.jeleniep.publicationManager.model.errors.ErrorResponse
import com.jeleniep.publicationManager.model.publications.PublicationDTO
import com.jeleniep.publicationManager.model.publications.PublicationRepository
import com.jeleniep.publicationManager.utils.Helpers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.InputStream


open class PublicationDetailsFragment(
    private var _id: String?,
    private var isEditionActive: Boolean
) :
    Fragment(), RequestObserver<PublicationDTO>, OpenPdfCallback {

    private lateinit var publicationDetailsViewModel: PublicationDetailsViewModel
    private lateinit var viewModelFactory: PublicationDetailsViewModelFactory
    private lateinit var publicationNameTextView: TextInputEditText
    private lateinit var publicationAuthorsTextView: TextInputEditText
    private lateinit var publicationDescriptionTextView: TextInputEditText
    private lateinit var publicationTagsTextView: TextInputEditText
    private lateinit var unlockEditionSwitch: Switch
    private lateinit var unlockEditionImageView: ImageView
    private lateinit var saveButton: Button
    private lateinit var selectPdfButton: Button
    private lateinit var root: View
    private lateinit var outAnimation: Animation
    private lateinit var inAnimation: Animation
    private val PICK_PDF_FILE = 2
    private lateinit var filePart: MultipartBody.Part
    private lateinit var filePath: String
    private lateinit var pdfDoi: String


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
        unlockEditionSwitch = root.findViewById(R.id.unlock_edition_switch)
        unlockEditionImageView = root.findViewById(R.id.unlock_edition_icon)
        saveButton = root.findViewById(R.id.save_button)
        selectPdfButton = root.findViewById(R.id.select_pdf_button)
        initiateUnlockEditionSwitch(isEditionActive)

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
            unlockEditionSwitch.isChecked = isEditionActive;
        }
        filePath = ""
        publicationDetailsViewModel.publication.observe(viewLifecycleOwner, Observer {
            publicationNameTextView.text = Helpers.stringToEditable(it.name)
            publicationAuthorsTextView.text = Helpers.listOfStringsToEditable(it.authors)
            publicationDescriptionTextView.text = Helpers.stringToEditable(it.description);
            if (it.file != null)
                filePath = it.file!!
            if (it.doi != null)
                pdfDoi = it.doi!!
//            publicationTagsTextView.text = Editable.Factory.getInstance().newEditable(it.name);
        })
        saveButton.setOnClickListener(SaveButtonOnClickListener(this))
        selectPdfButton.setOnClickListener(SelectPdfButtonOnClickListener(this))
        return root

    }

    inner class UnlockEditionOnClickListener : View.OnClickListener {
        override fun onClick(v: View?) {
            val layout: RelativeLayout = root.findViewById(R.id.main_layout)
            layout.requestFocus()
            changeEditionState(!isEditionActive)
        }
    }

    inner class SaveButtonOnClickListener(private val requestObserver: RequestObserver<PublicationDTO>) :
        View.OnClickListener {
        override fun onClick(v: View?) {
            val layout: RelativeLayout = root.findViewById(R.id.main_layout)
            layout.requestFocus()
            changeEditionState(!isEditionActive)

//            unlockEditionButton.startAnimation(outAnimation);
            val publicationDTO = PublicationDTO().apply {
                name = publicationNameTextView.text.toString()
                description = publicationDescriptionTextView.text.toString()
                authors = publicationAuthorsTextView.text.toString().split(", ")
                file = filePath
                doi = pdfDoi
            }
            if (_id != null) {
                PublicationRepository.editPublication(
                    _id!!,
                    publicationDTO,
                    requestObserver
                )
            } else {
                PublicationRepository.addPublication(publicationDTO, requestObserver)

            }
        }
    }

    inner class OnRemoveClickListener(private val requestObserver: RequestObserver<PublicationDTO>) :
        Toolbar.OnMenuItemClickListener {
        override fun onMenuItemClick(item: MenuItem?): Boolean {
            Log.d("debug", "remove")
            if (_id != null) {
                PublicationRepository.deletePublication(_id!!, requestObserver)
            }
            return true
        }
    }

    override fun onResume() {
        super.onResume()
        val layout: RelativeLayout = root.findViewById(R.id.main_layout)
        layout.requestFocus()
        if (_id != null) {
            (activity as PublicationDetailsActivity).toolbarTitle.text =
                (activity as PublicationDetailsActivity).getString(R.string.app_name)
            if (!(activity as PublicationDetailsActivity).topToolbar.menu.hasVisibleItems()) {
                (activity as PublicationDetailsActivity).topToolbar.inflateMenu(R.menu.publication_details_menu_items)
                (activity as PublicationDetailsActivity).topToolbar.setOnMenuItemClickListener(
                    OnRemoveClickListener(this)
                )
            }
            selectPdfButton.text = getString(R.string.open_pdf)

        }
    }

    override fun onStop() {
        super.onStop()
        (activity as PublicationDetailsActivity).topToolbar.menu.clear()
    }

    private fun changeEditionState(state: Boolean) {
        isEditionActive = state
        publicationNameTextView.isEnabled = state
        publicationAuthorsTextView.isEnabled = state
        publicationDescriptionTextView.isEnabled = state
        saveButton.isEnabled = state
//        selectPdfButton.isEnabled = state
        publicationTagsTextView.isEnabled = state
        unlockEditionImageView.startAnimation(outAnimation);
//        unlockEditionSwitch.isChecked = state
    }

    private fun initiateUnlockEditionSwitch(state: Boolean) {

        unlockEditionSwitch.isChecked = state;
        unlockEditionSwitch.setOnClickListener(UnlockEditionOnClickListener())
        outAnimation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation) {

                // Modify the resource of the ImageButton
                val resId: Int =
                    if (isEditionActive) R.drawable.ic_lock_outline_open_black_24dp else R.drawable.ic_lock_outline_closed_black_24dp
                unlockEditionImageView.setImageResource(resId);

                // Create the new Animation to apply to the ImageButton.
                unlockEditionImageView.startAnimation(inAnimation);
            }
        })
    }

    override fun onSuccess(publicationDTO: PublicationDTO, type: String) {

        var actionName = ""
        if (type == "create") else "updated"
        when (type) {
            "create" -> actionName = "added"
            "update" -> actionName = "updated"
            "delete" -> actionName = "deleted"
        }
        var toast: Toast =
            Toast.makeText(
                context,
                "Publication ${publicationDTO.name} successfully $actionName",
                Toast.LENGTH_LONG
            )

        toast.show()
        activity?.onBackPressed()
    }

    override fun onFail(errorResponse: ErrorResponse?, type: String) {
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


    inner class SelectPdfButtonOnClickListener(private val callback: OpenPdfCallback) :
        View.OnClickListener {
        override fun onClick(v: View?) {
            if (_id != null) {
                PublicationRepository.getPublicationPdf(_id!!, callback);
            } else {
                openFile(Uri.EMPTY)
            }
        }
    }


    fun openFile(pickerInitialUri: Uri) {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "application/pdf"
        }
        intent = Intent.createChooser(intent, "Choose a file");
        startActivityForResult(intent, PICK_PDF_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (requestCode == PICK_PDF_FILE) {
            if (resultCode == RESULT_OK) {
                resultData?.data?.also { uri ->

                    val inputStream: InputStream = activity?.contentResolver?.openInputStream(uri)!!
                    filePart = MultipartBody.Part.createFormData(
                        "file", "${uri.path}", RequestBody.create(
                            MediaType.parse("application/pdf"),
                            inputStream.readBytes()
                        )
                    )
                    PublicationRepository.addPublicationFromPdf(
                        filePart,
                        publicationDetailsViewModel
                    )
                    Toast.makeText(context, "Path: ${inputStream.available()}", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onPdfDownloaded(path: String) {
        val file = File(path)

        val apkURI: Uri = FileProvider.getUriForFile(
            requireContext(), requireContext().applicationContext
                .packageName.toString() + ".provider", file
        )
        val intent = Intent(Intent.ACTION_VIEW)
        Log.d("debug", apkURI.path)
        intent.setDataAndType(apkURI, "application/pdf")
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent)
    }


}
