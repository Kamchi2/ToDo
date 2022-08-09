package com.example.todo

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.DatePicker
import android.widget.TextView
import com.example.todo.databinding.FragmentCreateTaskDialogBinding
import com.example.todo.ui.fragments.DatePickerFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.*

class CreateTaskDialog : BottomSheetDialogFragment() {

    lateinit var binding: FragmentCreateTaskDialogBinding

    var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateTaskDialogBinding.inflate(layoutInflater)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bottomSheet = view.parent as View
        bottomSheet.backgroundTintMode = PorterDuff.Mode.CLEAR
        bottomSheet.backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
        bottomSheet.setBackgroundColor(Color.TRANSPARENT)
        initClicker()
    }
    private fun showRegularDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.regular_dialog)
        val cancel = dialog.findViewById(R.id.cancel) as TextView
        cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun initClicker() {
        with(binding) {
            binding.dateBtn.setOnClickListener {
                val datePickerFragment = DatePickerFragment()
                requireActivity().supportFragmentManager.setFragmentResultListener(
                    "date",
                    viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "date") {
                        val date = bundle.getString("key")
                        binding.dateBtn.text = date
                    }
                }
                datePickerFragment.show(requireActivity().supportFragmentManager, "TAG")
            }
            binding.regularBtn.setOnClickListener {
                showRegularDialog()
            }
        }
    }

//    private fun updateDateInView() {
//        val myFormat = "MM.dd.yyyy" // mention the format you need
//        val sdf = SimpleDateFormat(myFormat, Locale.US)
//        binding.dateBtn.text = sdf.format(cal.time)
//    }
}