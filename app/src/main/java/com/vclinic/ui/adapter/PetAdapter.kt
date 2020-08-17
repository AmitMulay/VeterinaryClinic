package com.vclinic.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vclinic.databinding.RowItemBinding
import com.vclinic.ui.PetDetailsWebViewActivity
import com.vclinic.ui.constant.ApplicationConstant
import com.vclinic.ui.model.Pet

class PetAdapter (act: Activity) : RecyclerView.Adapter<PetItemsViewHolder>() {
     var activity = act

    private var petList: List<Pet>? = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetItemsViewHolder {

        val rowItemBinding = RowItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return  PetItemsViewHolder(rowItemBinding)
    }

    override fun getItemCount(): Int {
        return petList?.size?:0
    }

    override fun onBindViewHolder(holder: PetItemsViewHolder, position: Int) {
        holder.rowItemBinding.root.setOnClickListener {
            val myIntent = Intent(activity, PetDetailsWebViewActivity::class.java)
            myIntent.putExtra(ApplicationConstant.ARGUMENT_CONTENT_URL, petList?.get(position)?.contentUrl) //Optional parameters
            myIntent.putExtra(ApplicationConstant.ARGUMENT_TITLE, petList?.get(position)?.title) //Optional parameters
            activity.startActivity(myIntent)
        }
        holder.rowItemBinding.petRow=petList?.get(position)
        holder.rowItemBinding.executePendingBindings()
    }

    fun setPetList(items:List<Pet>?) {
        this.petList = items
    }
}

class PetItemsViewHolder(val rowItemBinding:RowItemBinding) :
    RecyclerView.ViewHolder(rowItemBinding.root)