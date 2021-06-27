package org.svetikov.kotlinjunit5.controller

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import lombok.extern.slf4j.Slf4j
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.svetikov.kotlinjunit5.model.Bank
import org.svetikov.kotlinjunit5.service.BankService
import java.lang.IllegalArgumentException

@Slf4j
@RestController
@RequestMapping("/api/bank")
class BankController(private val service: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleArgumentException(e:IllegalArgumentException):ResponseEntity<String> =
    ResponseEntity(e.message,HttpStatus.BAD_REQUEST)

    @GetMapping("/banks")
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBankByAccount(@PathVariable accountNumber: String) =
        service.getBankByAccountNumber(accountNumber)

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    fun addNewBank(@RequestBody bank: Bank): Bank = service.addNewBank(bank)

    @PatchMapping()
    fun updateBank(@RequestBody bank: Bank) = service.updateBank(bank)

// upload and download file
    @PostMapping("/doc")
    fun addDocument(@RequestParam("document") document: MultipartFile): String {
        println(
            """ 
            document name: ${document.originalFilename},
            size : ${document.size}
        """.trimMargin()
        )
        service.store(document)
        return document.originalFilename.toString()
    }

    @GetMapping("/doc/{filename}")
    fun downloadDocument(@PathVariable filename: String) =
        ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment;filename=\"" + service.downloadDocument(filename).filename + "\""
            )
            .body(service.downloadDocument(filename))

}