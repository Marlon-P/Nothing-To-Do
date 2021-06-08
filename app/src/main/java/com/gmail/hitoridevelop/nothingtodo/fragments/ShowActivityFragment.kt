package com.gmail.hitoridevelop.nothingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.data.Activity
import com.gmail.hitoridevelop.nothingtodo.data.ActivityViewModel
import com.gmail.hitoridevelop.nothingtodo.databinding.FragmentShowActivityBinding
import com.google.android.material.snackbar.Snackbar


class ShowActivityFragment : Fragment() {


    private lateinit var binding: FragmentShowActivityBinding
    private lateinit var activityViewModel: ActivityViewModel
    private lateinit var result: Array<String>
    private lateinit var action: Activity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentShowActivityBinding.inflate(layoutInflater)

        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)

        result = arguments?.getStringArray("bundleKey") as Array<String>

        binding.textView.text = result[0]

        binding.suggestNewActivityBtn.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_showActivityFragment_to_suggestActivityFragment)
        }

        binding.saveForLaterBtn.setOnClickListener {
            saveForLaterDialog(it)
        }



        return binding.root
    }


    private fun saveForLaterDialog(view: View) {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Are you sure you want to add this activity?")
            ?.setPositiveButton("Add") { _, _ ->
                undoAddSnackBar(view)
                binding.saveForLaterBtn.isClickable = false//disable button after saving activity
                insertDataToDatabase()
            }
            ?.setNegativeButton("Cancel") { _, _ ->

            }
            ?.create()
            ?.show()
    }

    private fun insertDataToDatabase() {
                              //Activity name, activity type, accessibility range, participants, price range
        val activity = Activity(result[0], result[1], result[2], result[3], result[4])
        action = activity
        activityViewModel.addActivity(activity)
    }

    private fun deleteActivity(activity: Activity) {
        activityViewModel.deleteActivity(activity)
    }

    private fun undoAddSnackBar(v: View) {
        context?.let {
            Snackbar.make(it, v, "Saved Activity For Later", Snackbar.LENGTH_INDEFINITE)
            .setAction("UNDO") {
               undoDialog()
            }
            .show()
        }
    }

    private fun undoDialog() {
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Are you sure you want to remove this activity?")
            ?.setPositiveButton("REMOVE") { _, _ ->
                binding.saveForLaterBtn.isClickable = true
                deleteActivity(action)
            }
            ?.setNegativeButton("Cancel") { _, _ ->

            }
            ?.create()
            ?.show()
    }

}