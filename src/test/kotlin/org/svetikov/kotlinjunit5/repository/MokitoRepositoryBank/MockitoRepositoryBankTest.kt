package org.svetikov.kotlinjunit5.repository.MokitoRepositoryBank

import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test

internal class MockitoRepositoryBankTest{

val repository=MockitoRepositoryBank()

    @Test
    fun `all bank not empty`(){
        val banks = repository.getAllBanks()
        assertThat(banks).isNotEmpty
    }

    @Test
    fun `should provide some mock data`(){
        val banks = repository.getAllBanks()
        assertThat(banks).allSatisfy { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).allMatch { it.transactionFee != 0 }
    }
}