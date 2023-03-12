package com.example.preferencesdatastore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.example.preferencesdatastore.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var userManager: UserManager
    var name = ""
    var age = 0
    var gender = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userManager = UserManager(this)

        buttonSave()

        obsserveData()
    }

    private fun buttonSave(){
        binding.btnSave.setOnClickListener {
            name = binding.etName.text.toString()
            age = binding.etAge.text.toString().toInt()
            val isMale: Boolean = binding.switchGender.isChecked

            GlobalScope.launch {
                userManager.storeUser(age,name,isMale)
            }
        }
    }
    private fun obsserveData(){

        userManager.userNameFlow.asLiveData().observe(this) {
            name = it
            binding.tvName.text = it.toString()
        }

        userManager.userAgeFlow.asLiveData().observe(this){
            age = it
            binding.tvAge.text = it.toString()
        }

        userManager.userGenderFlow.asLiveData().observe(this){
            gender = if(it)"Male" else "Female"
            binding.tvGender.text = gender
        }
    }




}