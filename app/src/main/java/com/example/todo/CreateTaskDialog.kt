package com.example.todo

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.todo.R
import com.example.todo.databinding.RegularDialogBinding
import com.example.todo.databinding.FragmentCreateTaskDialogBinding
import com.example.todo.CreateDataModel
import com.example.todo.databinding.FragmentHomeBinding
import com.example.todo.ui.fragments.DatePickerFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CreateTaskDialog : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCreateTaskDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
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

    private fun initClicker() {
        binding.dateBtn.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            requireActivity().supportFragmentManager.setFragmentResultListener(
                "myKey",
                viewLifecycleOwner
            ) { resultKey, bundle ->
                if (resultKey == "myKey") {
                    val date = bundle.getString("key")
                    binding.dateBtn.text = date
                }
            }
            datePickerFragment.show(requireActivity().supportFragmentManager, "TAG")
        }
        binding.regularBtn.setOnClickListener {
            showRegularDialog()
        }
        binding.applyBtn.setOnClickListener {
            val bundle = Bundle()
            val model = CreateDataModel(
                binding.taskEdit.text.toString(),
                binding.dateBtn.text.toString(),
                binding.regularBtn.text.toString()
            )
            bundle.putSerializable("model", model)
            findNavController().navigate(R.id.homeFragment, bundle)
            dismiss()
        }
    }

    @SuppressLint("InflateParams")
    private fun showRegularDialog() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        val binding = RegularDialogBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        with(binding) {
            everyDayRadioBtn.setOnClickListener {
                this@CreateTaskDialog.binding.regularBtn.text = everyDayRadioBtn.text
                dialog.dismiss()
            }
            everyWeekRadioBtn.setOnClickListener {
                this@CreateTaskDialog.binding.regularBtn.text = everyWeekRadioBtn.text
                dialog.dismiss()
            }
            everyMonthRadioBtn.setOnClickListener {
                this@CreateTaskDialog.binding.regularBtn.text = everyMonthRadioBtn.text
                dialog.dismiss()
            }
            everyYearRadioBtn.setOnClickListener {
                this@CreateTaskDialog.binding.regularBtn.text = everyYearRadioBtn.text
                dialog.dismiss()
            }
            cancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}