package com.example.googlefirebaseone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlefirebaseone.adapter.RvContactAdapter
import com.example.googlefirebaseone.databinding.FragmentHomeBinding
import com.example.googlefirebaseone.model.Contacts
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding  get() = _binding!!

    private lateinit var contactList : ArrayList<Contacts>
    private lateinit var firebaseRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //_binding should inflate here
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        binding.addBtn.setOnClickListener(){

            findNavController().navigate(R.id.action_homeFragment_to_addFragment)

        }


        //fetch the data from firebase DB
        firebaseRef = FirebaseDatabase.getInstance().getReference("contacts") // write same name as in DB
        contactList = arrayListOf()


        fetchFirebaseData() // fetch data function

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }


        return binding.root
    }

    private fun fetchFirebaseData() {

        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                if (snapshot.exists()){
                    for (contactSnap in snapshot.children){
                        val contacts = contactSnap.getValue(Contacts::class.java)
                        contactList.add(contacts!!)
                    }
                }
                val rvAdapter = RvContactAdapter(contactList)
                binding.recyclerView.adapter = rvAdapter //adapter attached to recycler view
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error $error", Toast.LENGTH_SHORT).show()
            }

        })

    }


}