package com.example.petfinder.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.petfinder.R
import com.example.petfinder.storage.database.EntityAnimal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    listVm: ListViewModel = hiltViewModel()
) {
    val screenModel by listVm.screenModel.collectAsState()
    val data = screenModel.pager.flow.collectAsLazyPagingItems()

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(text = "Pet list")
            })
        }) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (data.loadState.refresh == LoadState.Loading) {
                item {
                    Text(
                        text = "Waiting for items to load from the backend",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            items(count = data.itemCount) { index ->
                val item = data[index]
                if (item != null) {
                    PetItem(
                        modifier = Modifier.padding(top = 20.dp),
                        pet = item,
                        onItemClick = listVm::onPetClick
                    )
                }
            }

            if (data.loadState.append == LoadState.Loading) {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        }
    }


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PetItem(
    modifier: Modifier = Modifier,
    pet: EntityAnimal,
    onItemClick: (EntityAnimal) -> Unit
) {
    Row(
        modifier = modifier
            .height(52.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onItemClick(pet) }
            )
    ) {

        GlideImage(
            modifier = Modifier
                .size(52.dp)
                .clip(CircleShape),
            model = pet.previewImage,
            contentDescription = "",
            failure = placeholder(R.drawable.pet_placeholder),
            loading = placeholder(R.drawable.pet_placeholder),
            requestBuilderTransform = { requestBuilder ->
                requestBuilder.transform(CenterCrop())
            }
        )

        Column(
            modifier = Modifier
                .padding(start = 12.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = pet.name,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color(0xFF000E08)
            )

            Text(
                modifier = Modifier.weight(0.7f),
                text = "Distance: ${pet.distance ?: "Unknown"}",
                fontSize = 12.sp,
                textAlign = TextAlign.Start,
                color = Color(0xFF797C7B)
            )
        }
    }

}