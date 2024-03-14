package com.example.googlefirebaseone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.googlefirebaseone.databinding.FragmentAddBinding
import com.example.googlefirebaseone.model.Contacts
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddFragment : Fragment() {

    private var _binding : FragmentAddBinding? = null
    private val binding get() = _binding!!

    //firebase database
    private lateinit var firebaseRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts")


        binding.sendDataFirebase.setOnClickListener(){

            savaData()

        }


        return binding.root
    }

    private fun savaData() {
       val name = binding.etName.text.toString()
        val mobile = binding.etMobile.text.toString()

        if (name.isEmpty()) binding.etName.error = "Write Your Name !!"
        if (mobile.isEmpty()) binding.etMobile.error = "Write Your Mobile Number !!"

        val contactId = firebaseRef.push().key!! // it will create a unique id
        val contacts = Contacts(contactId,name,mobile) // pass data in model contacts class

        // create child using contactId
        firebaseRef.child(contactId).setValue(contacts)

            .addOnCompleteListener(){
                Toast.makeText(context, "Data Store Successfully", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener(){
                Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
            }

        binding.etName.text.clear()
        binding.etMobile.text.clear()
    }


}