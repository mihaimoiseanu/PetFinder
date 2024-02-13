package com.example.petfinder.ui.screens.list

import androidx.paging.Pager
import com.example.petfinder.storage.database.EntityAnimal

data class ListScreenModel(
    val pager: Pager<Int, EntityAnimal>
)
