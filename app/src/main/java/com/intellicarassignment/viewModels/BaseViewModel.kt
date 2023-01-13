package com.graphicalab.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    protected val dataLoading = MutableLiveData<Boolean>().apply { value = false }

    protected val toastMessage = MutableLiveData<String>()

    fun observeLoading() = dataLoading
    fun observeToast() = toastMessage

}