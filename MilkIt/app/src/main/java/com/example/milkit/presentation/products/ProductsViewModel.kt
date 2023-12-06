package com.example.milkit.presentation.products

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milkit.presentation.home.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.launch

class ProductsViewModel:ViewModel() {

    val firebaseFireStore : FirebaseFirestore = FirebaseFirestore.getInstance()

    val allProducts = mutableStateListOf<Product>()
    var isLoading by mutableStateOf(false)

    init {
        getProducts()
    }

    fun getProducts() {
        isLoading = true
        viewModelScope.launch {
            firebaseFireStore.collection("products").get()
                .addOnSuccessListener { res ->
                    isLoading = false
                    allProducts.clear()
                    for(doc in res) {
                        allProducts.add(doc.toObject())
                    }
                }
                .addOnFailureListener{
                    isLoading = false
                }
        }
    }
}