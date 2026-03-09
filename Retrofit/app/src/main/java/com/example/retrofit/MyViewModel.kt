package com.example.retrofit

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    val repo = WikiRepository()
    val wikiUiState = mutableStateOf(0)

    fun getHits(name: String) {
        viewModelScope.launch(Dispatchers.IO){
            wikiUiState.value = repo.hitCountCheck(name).query.searchinfo.totalhits
        }
    }
}