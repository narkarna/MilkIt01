package com.example.milkit.presentation.home

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val firebaseFireStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val featuredProducts = mutableStateListOf<Product>()
    var isLoading by mutableStateOf(false)

    init {
        getFeaturedProducts()
    }

    fun getFeaturedProducts() {
        isLoading = true
        viewModelScope.launch {
            firebaseFireStore.collection("products")
                .whereEqualTo("isFeatured", true)
                .get()
                .addOnSuccessListener { res ->
                    isLoading = false
                    featuredProducts.clear()
                    for(doc in res) {
                        featuredProducts.add(doc.toObject())
                    }
                }
                .addOnFailureListener{
                    isLoading = false
                }
        }
    }
}