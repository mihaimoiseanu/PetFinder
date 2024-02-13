package com.example.petfinder.storage.database

import androidx.paging.PagingSource
import androidx.paging.rxjava3.RxPagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface AnimalDao {

    @Upsert
    fun insert(vararg animal: EntityAnimal): Completable

    @Query("Select * from animals")
    fun pagingSource(): RxPagingSource<Int, EntityAnimal>

    @Query("select * from animals where id = :id")
    fun getPetById(id:Int) : Single<EntityAnimal>
}