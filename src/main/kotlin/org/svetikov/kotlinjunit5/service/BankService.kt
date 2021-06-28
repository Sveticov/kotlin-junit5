package org.svetikov.kotlinjunit5.service

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.svetikov.kotlinjunit5.model.Bank
import org.svetikov.kotlinjunit5.repository.DataSource
import java.lang.IllegalArgumentException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Stream

@Service
class BankService(private val dataSource: DataSource) {
    final val MY_PATH = Paths.get("my_document")

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBankByAccountNumber(accountNumber: String): Bank = dataSource.getBankByAccountNumber(accountNumber)
    fun addNewBank(bank: Bank): Bank {
        if (getBanks().any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("This accountNumber ${bank.accountNumber} already exists")
        }
        return dataSource.addNewBank(bank)
    }

    fun store(document: MultipartFile) {
        Files.copy(document.inputStream, MY_PATH.resolve(document.originalFilename))
    }

    fun downloadDocument(filename: String): Resource {
        val file = MY_PATH.resolve(filename)
        val resource = UrlResource(file.toUri())
        if (resource.exists() || resource.isReadable) {
            return resource
        } else {
            throw RuntimeException("Fail!!!")
        }
    }

    fun updateBank(bank: Bank) = dataSource.updateBank(bank) //TODO
    fun deleteByAccountNumber(accountNumber: String): Unit {
        dataSource.deleteByAccountNumber(accountNumber)
    }


}


