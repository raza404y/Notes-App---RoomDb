package com.example.notesapp.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.notesapp.database.Notes
import com.example.notesapp.databinding.ItemBottomsSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetDialog : BottomSheetDialogFragment() {

    private lateinit var binding: ItemBottomsSheetBinding
    private var note: Notes? = null
    var onDeleteClick: ((Notes) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ItemBottomsSheetBinding.inflate(inflater, container, false)
        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.deleteBtn.setOnClickListener {
            note?.let { note ->
                onDeleteClick?.invoke(note)  // Trigger the delete action
                dismiss()  // Close the bottom sheet after deletion
            }
        }
        note?.let {
            // Setup views with note data if necessary
            binding.sureTv.text = "Are you sure want to delete {${note?.noteTitle}}"
        }

        return binding.root
    }

    fun setNote(note: Notes) {
        this.note = note
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog?.setOnShowListener {
            val d = it as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        const val TAG = "ModalBottomSheetDialog"
    }
}
