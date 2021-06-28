package org.svetikov.kotlinjunit5.repository.MokitoRepositoryBank

import org.springframework.stereotype.Repository
import org.svetikov.kotlinjunit5.model.Bank
import org.svetikov.kotlinjunit5.repository.DataSource

@Repository
class MockitoRepositoryBank : DataSource {

    val banks = mutableListOf<Bank>(
        Bank("1", 0.7, 9),
        Bank("2", 0.0, 92),
        Bank("3", 0.5, 0)
    )

    override fun getBank(): Bank {
        TODO("Not yet implemented")
    }

    override fun getAllBanks(): Collection<Bank> {
        return banks
    }

    override fun retrieveBanks(): Collection<Bank> {
        return banks
    }

    override fun getBankByAccountNumber(accountNumber: String): Bank {
        return banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    }

    override fun addNewBank(bank: Bank): Bank {
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks.firstOrNull { it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number ${bank.accountNumber}")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteByAccountNumber(accountNumber: String):Unit {
       val deleteBank = banks.firstOrNull{it.accountNumber == accountNumber}
           ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
        banks.remove(deleteBank)

    }
}