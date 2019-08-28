package com.piotrprus.quickcurrencies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.piotrprus.quickcurrencies.common.data.datasource.local.QuickCurrencyDatabase
import com.piotrprus.quickcurrencies.common.data.models.CurrencyBase
import com.piotrprus.quickcurrencies.common.data.models.Rates
import com.piotrprus.quickcurrencies.common.data.models.dao.CurrencyDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import timber.log.Timber

@RunWith(AndroidJUnit4::class)
class TestCurrencyDao : KoinTest {

    private val database: QuickCurrencyDatabase by inject()
    private val dao: CurrencyDao by inject()

    @Before
    fun before() {
        stopKoin()
        startKoin { modules(testModules) }
    }

    @After
    fun after() {
        database.close()
        stopKoin()
    }

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun saveTest() {
        val myCurrencyBase = CurrencyBase("EUR", "09-09-2019", Rates(1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1, 1.1))
        dao.addAll(myCurrencyBase)
            .test()

        dao.getAll()
            .test()
            .assertValue {
                Timber.d("Read currencyBase name: ${it.base}")
                it.base == "EUR"
            }
    }
}