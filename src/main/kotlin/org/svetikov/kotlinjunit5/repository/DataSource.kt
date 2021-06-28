package org.svetikov.kotlinjunit5.repository

import org.svetikov.kotlinjunit5.model.Bank

interface DataSource {
    fun getBank():Bank

    fun getAllBanks():Collection<Bank>
    fun retrieveBanks(): Collection<Bank>
    fun getBankByAccountNumber(accountNumber:String): Bank
    fun addNewBank(bank: Bank) :Bank
    fun updateBank(bank: Bank):Bank
    fun deleteByAccountNumber(accountNumber: String)
}