package com.piotrprus.quickcurrencies.common.data.models.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM CurrencyBase")
    fun getAll(): Single<CurrencyBase>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(item: CurrencyBase): Completable

}