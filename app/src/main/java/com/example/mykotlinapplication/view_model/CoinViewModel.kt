package com.example.mykotlinapplication.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mykotlinapplication.model.MainModel
import com.example.mykotlinapplication.pojo.CoinPriceInfo

interface CoinViewModel {
    fun loadData()
    fun getDetailCoinInfo(fSym: String): LiveData<CoinPriceInfo>
    fun setModel(model: MainModel)

    class Base : ViewModel(), CoinViewModel {

        lateinit var priceList: LiveData<List<CoinPriceInfo>>

        private var model = MainModel(this)

        override fun loadData() {
            priceList = model.getPriceList()
        }

        override fun getDetailCoinInfo(fSym: String): LiveData<CoinPriceInfo> {
            return model.getDetailCoinInfo(fSym)
        }

        override fun setModel(model: MainModel) {
            this.model = model
        }
    }
}