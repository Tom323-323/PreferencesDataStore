package com.example.preferencesdatastore

import android.content.Context
import android.widget.Toast
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager (context:Context) {

    private val dataStore = context.createDataStore(name = "user_pref")

    companion object{
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_GENDER_KEY = preferencesKey<Boolean>("USER_GENDER")
    }

    suspend fun storeUser(age:Int, name:String,gender:Boolean) {
        dataStore.edit {
            it[USER_NAME_KEY] = name
            it[USER_AGE_KEY] = age
            it[USER_GENDER_KEY] = gender
        }
    }

    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY]?: ""
    }

    val userAgeFlow: Flow<Int> = dataStore.data.map {
        val age = it[USER_AGE_KEY]?:0

        if (age<18){
            Toast.makeText(context,"TEXT TEST!",Toast.LENGTH_LONG).show()
        }
        age
    }

    val userGenderFlow:Flow<Boolean> = dataStore.data.map {
        it[USER_GENDER_KEY]?: false
    }

}