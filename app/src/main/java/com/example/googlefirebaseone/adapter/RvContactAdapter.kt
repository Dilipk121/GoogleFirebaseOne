package com.example.googlefirebaseone.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.googlefirebaseone.HomeFragment
import com.example.googlefirebaseone.HomeFragmentDirections
import com.example.googlefirebaseone.databinding.RvContactItemsBinding
import com.example.googlefirebaseone.model.Contacts
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.FirebaseDatabase

class RvContactAdapter(private val contactList: ArrayList<Contacts>) :
    RecyclerView.Adapter<RvContactAdapter.RvViewHolder>() {

    class RvViewHolder(val binding: RvContactItemsBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        return RvViewHolder(
            RvContactItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
        val currentItem = contactList[position]

        holder.apply {
            binding.apply {
                tvId.text = currentItem.id
                tvName.text = currentItem.name
                tvMobile.text = currentItem.mobile

                rvContainer.setOnClickListener {

                    val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(
                        currentItem.id.toString(),
                        currentItem.name.toString(),
                        currentItem.mobile.toString()
                    )
                    Navigation.findNavController(holder.itemView).navigate(action)

                }

                rvContainer.setOnLongClickListener {

                    //alert box
                    MaterialAlertDialogBuilder(holder.itemView.context)
                        .setTitle("Delete Item Permanently")
                        .setMessage("Are You Sure To Delete This Item")
                        .setPositiveButton("Yes") { _, _ ->

                            val firebaseRef =
                                FirebaseDatabase.getInstance().getReference("contacts")
                            firebaseRef.child(currentItem.id.toString()).removeValue()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        holder.itemView.context,
                                        "Item Removed Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        holder.itemView.context,
                                        "Error ${it.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                        .setNegativeButton("No") { _, _ ->
                            Toast.makeText(holder.itemView.context, "Cancelled", Toast.LENGTH_SHORT)
                                .show()

                        }
                        .show()

                    return@setOnLongClickListener true
                }
            }
        }

    }

}