package com.example.adminhungertreats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminhungertreats.databinding.ActivityAdminProfileBinding
import com.example.adminhungertreats.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminProfileBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var adminReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        adminReference = database.reference.child("user")

        binding.apply {
            backButton.setOnClickListener {
                finish()
            }
            saveInfoButton.setOnClickListener {
                updateUserData()
            }
            name.isEnabled = false
            address.isEnabled = false
            email.isEnabled = false
            phone.isEnabled = false
            password.isEnabled = false
            saveInfoButton.isEnabled = false
        }

        var isEnable = false;
        binding.editButton.setOnClickListener {
            isEnable = !isEnable

            binding.name.isEnabled = isEnable
            binding.address.isEnabled = isEnable
            binding.email.isEnabled = isEnable
            binding.phone.isEnabled = isEnable
            binding.password.isEnabled = isEnable

            if (isEnable) {
                binding.name.requestFocus()
            }
            binding.saveInfoButton.isEnabled = isEnable
        }
        retrieveUserData()
    }

    private fun retrieveUserData() {
        val currentUserUid = auth.currentUser?.uid
        if (currentUserUid != null) {
            val userReference = adminReference.child(currentUserUid)
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        var ownerName = snapshot.child("name").getValue()
                        var email = snapshot.child("email").getValue()
                        var password = snapshot.child("password").getValue()
                        var address = snapshot.child("address").getValue()
                        var phone = snapshot.child("phone").getValue()
                        setDataToTextView(ownerName, email,password, address, phone)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }

    }

    private fun updateUserData() {
        var updateName = binding.name.text.toString()
        var updateEmail = binding.name.text.toString()
        var updatePassword = binding.name.text.toString()
        var updatePhone = binding.name.text.toString()
        var updateAddress = binding.name.text.toString()
        val currentUserUid = auth.currentUser?.uid
        if(currentUserUid!=null){
            val userReference = adminReference.child(currentUserUid)
            userReference.child("name").setValue(updateName)
            userReference.child("email").setValue(updateEmail)
            userReference.child("password").setValue(updatePassword)
            userReference.child("phone").setValue(updatePhone)
            userReference.child("address").setValue(updateAddress)

            Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
            //update the email and password for firebase authentication
            auth.currentUser?.updateEmail(updateEmail)
            auth.currentUser?.updatePassword(updatePassword)
        }

        else{
            Toast.makeText(this, "Profile Update Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setDataToTextView(
        ownerName: Any?,
        email: Any?,
        password: Any?,
        address: Any?,
        phone: Any?
    ) {
        binding.name.setText(ownerName.toString())
        binding.email.setText(email.toString())
        binding.address.setText(address.toString())
        binding.phone.setText(phone.toString())
        binding.password.setText(password.toString())
    }
}