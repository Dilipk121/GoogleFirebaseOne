package com.example.googlefirebaseone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.googlefirebaseone.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseRef = FirebaseDatabase.getInstance().getReference("test")

        binding.sendData.setOnClickListener(){

        firebaseRef.setValue("Hello Buddy Im Dilip Boss")
            .addOnCompleteListener(){
                Toast.makeText(this, "Data Store Successfully", Toast.LENGTH_SHORT).show()
            }

        }


    }
}