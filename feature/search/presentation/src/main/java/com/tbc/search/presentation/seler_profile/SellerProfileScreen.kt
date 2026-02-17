package com.tbc.search.presentation.seler_profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SellerProfileScreen(
    viewModel: SellerProfileViewModel = hiltViewModel(),
    sellerUid: String
){
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        println("sellerUid $sellerUid")
    }
}


@Composable
private fun SellerProfileContent(){

}