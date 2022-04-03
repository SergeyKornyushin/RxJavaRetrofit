package com.example.mykotlinapplication.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.map
import com.example.mykotlinapplication.api.ApiFactory
import com.example.mykotlinapplication.database.DataBase
import com.example.mykotlinapplication.pojo.CoinInfoListOfData
import com.example.mykotlinapplication.pojo.CoinPriceInfo
import com.example.mykotlinapplication.pojo.CoinPriceInfoRawData
import com.example.mykotlinapplication.view_model.CoinViewModel
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.TimeUnit
import kotlin.math.sin

class MainModel constructor(private val viewModelInterface: CoinViewModel) {
    init {
        loadData()
        viewModelInterface.setModel(this)
        viewModelInterface.loadData()
    }

    fun getPriceList(): LiveData<List<CoinPriceInfo>> {
        return DataBase.db.coinPriceInfoDao().getPriceList()
    }

    private fun loadData() {
        ApiFactory.apiService.getTopCoinsInfo(limit = 10)
            .map { it -> it.data.joinToString(",") { it.coinInfo.name } }
            .flatMap { ApiFactory.apiService.getFullPriceList(fSyms = it) }
            .map { getPriceListFromRawData(it) }
            .subscribeOn(Schedulers.io())
            .delaySubscription(5, TimeUnit.SECONDS)
            .repeat()
            .retry()
            .subscribe({
                DataBase.db.coinPriceInfoDao().insertPriceList(it)
            }, {
                Log.i("test4", "${it.message}")
            })
    }

    private fun getPriceListFromRawData(
        coinPriceInfoRawData: CoinPriceInfoRawData
    ): List<CoinPriceInfo> {
        val result = ArrayList<CoinPriceInfo>()
        val jsonObject = coinPriceInfoRawData.coinPriceInfoJsonObject ?: return result
        jsonObject.keySet().forEach { it ->
            jsonObject.getAsJsonObject(it)
                .keySet().forEach { it2 ->
                    result.add(
                        Gson().fromJson(
                            jsonObject.getAsJsonObject(it).getAsJsonObject(it2),
                            CoinPriceInfo::class.java
                        )
                    )
                }
        }
        return result
    }

    fun getDetailCoinInfo(fSym: String): LiveData<CoinPriceInfo> {
        return DataBase.db.coinPriceInfoDao().getPriceInfoAboutCoin(fSym)
    }
}