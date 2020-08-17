package com.vclinic.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.vclinic.R
import com.vclinic.databinding.ActivityPetinfoBinding
import com.vclinic.ui.adapter.PetAdapter
import com.vclinic.ui.model.Pet
import com.vclinic.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dataBinding: ActivityPetinfoBinding
    private lateinit var petAdapter: PetAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewAndAdapter()
    }

    /**
     * Method to set view model and adapter
     */
    private fun initViewAndAdapter(){
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_petinfo)
        dataBinding.viewModel = mainViewModel
        dataBinding.lifecycleOwner = this

        dataBinding.btnCall.setOnClickListener {
            showDialog()
        }
        dataBinding.btnChat.setOnClickListener {
            showDialog()
        }
        dataBinding.listPetinfo.layoutManager = LinearLayoutManager(this)
        petAdapter = PetAdapter(this)
        petAdapter?.setPetList(mainViewModel.pet.value)
        dataBinding.listPetinfo.adapter = petAdapter
        mainViewModel.pet.observe(this, Observer<List<Pet?>?> {
            petAdapter?.setPetList(mainViewModel.pet.value)
            petAdapter?.notifyDataSetChanged()

        })
    }

    /**
     * Method to open alert dialog with message
     */
    private fun showDialog() {
            val alertMessage = if (mainViewModel.isOfficeHours()) {
                R.string.within_hours
            } else {
                R.string.outside_hours
            }
            val builder = AlertDialog.Builder(this)
            builder.apply {
                setMessage(alertMessage)
                setPositiveButton(R.string.ok){ alertDialog, _ -> alertDialog.dismiss()
                }
            }
            builder.create().show()
    }
}