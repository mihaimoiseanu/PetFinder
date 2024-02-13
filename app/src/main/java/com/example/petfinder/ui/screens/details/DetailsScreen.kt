package com.example.petfinder.ui.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.petfinder.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(detailsVM: DetailsViewModel = hiltViewModel()) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Pet Details") },
                navigationIcon = {
                    IconButton(onClick = detailsVM::navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }) { paddingValues ->

        val screenModel by detailsVM.screenModel.collectAsState()

        val pet = screenModel.animal
        if (pet != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {

                GlideImage(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                    model = pet.image,
                    contentDescription = "",
                    failure = placeholder(R.drawable.pet_placeholder),
                    loading = placeholder(R.drawable.pet_placeholder),
                    requestBuilderTransform = { requestBuilder ->
                        requestBuilder.transform(CenterCrop())
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Name"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = pet.name
                    )
                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Gender"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = pet.gender
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Status"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = pet.status
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Breed"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = pet.breeds
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Distance"
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = " ${pet.distance ?: "Unknown"}",
                    )
                }
            }
        }

    }
}