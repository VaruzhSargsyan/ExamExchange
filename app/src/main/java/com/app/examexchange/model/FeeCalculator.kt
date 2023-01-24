package com.app.examexchange.model

import androidx.annotation.WorkerThread
import androidx.sqlite.db.SimpleSQLiteQuery
import com.app.examexchange.database.MyDatabase
import com.app.examexchange.database.entities.Exchange
import com.app.examexchange.database.entities.RuleTypes

// layer to get/generate/calculate the fee
class FeeCalculator(database: MyDatabase) {
    private val ruleDao = database.ruleDao()
    private val exchangeDao = database.exchangeDao()
    
    @WorkerThread
    suspend fun updateFee(exchange: Exchange) {
        exchange.feeRate = 0.007f
        val listRules = ruleDao.getAllRules()
        
        for (rule in listRules) {
            when (rule.type) {
                RuleTypes.SCRIPT_RUN_ON_EXCHANGE -> {
                    if (exchangeDao.runRuleOnHistory(SimpleSQLiteQuery(rule.content, null)) == true) {
                        exchange.feeRate = 0f
                        return
                    }
                }
                RuleTypes.CONVERSION_MAXIMAL_LIMIT_IN_EURO -> {
                    if (exchange.sumSell / exchange.rateSell < 200) {
                        exchange.updateFeeRate(0f)
                        return
                    }
                }
    
                RuleTypes.DEFAULT_FEE -> {
                    try {
                        exchange.updateFeeRate(rule.content.toFloat())
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                }
            }
        }
    }
}