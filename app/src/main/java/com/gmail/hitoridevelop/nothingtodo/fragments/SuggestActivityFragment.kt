package com.gmail.hitoridevelop.nothingtodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.gmail.hitoridevelop.nothingtodo.R
import com.gmail.hitoridevelop.nothingtodo.api.BoredApi
import com.gmail.hitoridevelop.nothingtodo.api.BoredApiResponse
import com.gmail.hitoridevelop.nothingtodo.databinding.FragmentSuggestActivityBinding
import kotlinx.coroutines.*
import kotlin.random.Random




class SuggestActivityFragment : Fragment(), AdapterView.OnItemSelectedListener  {

    lateinit var binding: FragmentSuggestActivityBinding

    lateinit var activityType: String
    lateinit var accessibility: String
    lateinit var participants: String
    lateinit var price: String

    lateinit var response: BoredApiResponse


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSuggestActivityBinding.inflate(inflater)
        // Type of Activity spinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.type_of_activities_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                binding.activitySpinner.adapter = adapter
            }
        }

        //Accessibility range spinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.accessibility_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.accessibilitySpinner.adapter = adapter
            }
        }

        //Amount of participants spinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.participants_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.participantsSpinner.adapter = adapter
            }
        }

        //Price range spinner
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.price_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding.priceSpinner.adapter = adapter
            }
        }

        binding.activitySpinner.onItemSelectedListener = this
        binding.accessibilitySpinner.onItemSelectedListener = this
        binding.participantsSpinner.onItemSelectedListener = this
        binding.priceSpinner.onItemSelectedListener = this



        val viewModelJob = Job()
        val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

        binding.button.setOnClickListener {

            /*choices: type = Any or (the other choices in the spinner)
            * same goes for all the other 3 choices
            * so 4 options with 2 choices each
            * 16 possible get calls 2 * 2 * 2 * 2 */
            var useCustomGet = false
            val baseUrl = "https://www.boredapi.com/api/activity?"
            var url  = baseUrl

            //if the user chooses a specific type of activity, then use the custom url get instead of the
            //get that gives a random activity
            if (activityType != "Any") {
                useCustomGet = true
                url += "type=${activityType.lowercase()}"
            }

            //accessibility values in the api range from 0.0,  meaning completely accessible, to 1.0
            //meaning it might not be accessible to those with certain disabilities
            if (accessibility != "Any") {
                useCustomGet = true

                var accessibility = "maxaccessibility=0"
                when (accessibility) {
                    "Completely Accessible" -> {} // api uses accessibility values from (0.0 - 0.2)
                    "Limited Accessibility" -> {
                        accessibility = "minaccessibility=0.2&maxaccessibility=0.5"
                    }
                    "Poor Accessibility" -> { accessibility = "minaccessibility=0.5&maxaccessibility=1" }
                }

                if (url != baseUrl) {
                    url += "&"
                }

                url += accessibility
            }

            if (participants != "Any") {
                useCustomGet = true

                var amountOfPeople = "1"

                when (participants) {
                    "1 Person" -> { } //default value is 1
                    "2 – 4 people" -> {
                        val num = Random.nextInt(2,5)
                        amountOfPeople = "$num"
                    }

                    //the max amount of participants for any given activity seems to be 8 in this api, but there are no
                    //activities that include 6 or 7 people, so it's just a choice between 5 or 8 people
                    "5 or more people" -> {
                        val num = Random.nextInt(2,4)
                        amountOfPeople = if (num == 2) "5"
                        else "8"
                    }
                }

                if (url != baseUrl) {
                    url += "&"
                }

                url += "participants=$amountOfPeople"
            }

            if (price != "Any") {
                useCustomGet = true

                var priceRange = "maxprice=0"
                when (price) {
                    "Free" -> {} //default value if free or 0
                    "Inexpensive" -> {//based on some responses from the api, over .4 seems expensive os values .1 - .4
                        priceRange = "minprice=0.1&maxprice=0.4"
                    }
                    "Expensive" -> { priceRange = "minprice=0.4" }
                }

                if (url != baseUrl) {
                    url += "&"
                }

                url += priceRange
            }



            uiScope.launch {
                withContext(Dispatchers.IO) {
                    //Do background tasks...
                    try {
                        response  = if (useCustomGet) {

                            BoredApi.retrofitService.customActivity(url)

                        } else {
                            BoredApi.retrofitService.getRandomActivity()

                        }


                    } catch (e: Exception) {
                        println(e.message)
                    }
                    withContext(Dispatchers.Main){
                        //println(response.toString())

                        val activityArray = arrayOf(response.activity, activityType, accessibility, participants, price)
                        val bundle = bundleOf("bundleKey" to activityArray)

                        view?.findNavController()?.navigate(R.id.action_suggestActivityFragment_to_showActivityFragment, bundle)
                    }
                }
            }
        }



        return binding.root
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        when (parent?.id) {
            binding.activitySpinner.id -> activityType = parent.getItemAtPosition(pos).toString()
            binding.accessibilitySpinner.id -> accessibility = parent.getItemAtPosition(pos).toString()
            binding.participantsSpinner.id -> participants = parent.getItemAtPosition(pos).toString()
            binding.priceSpinner.id -> price = parent.getItemAtPosition(pos).toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        when (parent?.id) {
            binding.activitySpinner.id -> println("nothing")

            binding.accessibilitySpinner.id -> println("nothing")

            binding.participantsSpinner.id -> println("nothing")

            binding.priceSpinner.id -> println("nothing")
        }
    }

}