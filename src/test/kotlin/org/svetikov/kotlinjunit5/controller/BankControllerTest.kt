package org.svetikov.kotlinjunit5.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import org.svetikov.kotlinjunit5.model.Bank

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    private val url_bank = "/api/bank"

    @Nested
    @DisplayName("PATCH/api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        @Test
        fun `should update an existing bank`() {
            val updateBank = Bank("1",9.5,7)

          val performPatchRequest =  mockMvc.patch(url_bank){
                contentType = MediaType.APPLICATION_JSON
                content=objectMapper.writeValueAsString(updateBank)
            }

            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updateBank))
                    }
                }
        }
    }

    @Nested
    @DisplayName("POST/api/bank")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class AddNewBank {
        @Test
        fun `should be add new bank`() {
            val bank = Bank("new12", 97.0, 900)

            val performPost = mockMvc.post(url_bank) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.accountNumber") { value("new12") }
                    jsonPath("$.trust") { value("97.0") }
                    jsonPath("$.transactionFee") { value("900") }

                }
        }

        @Test
        fun `should BED REQUEST if bank already exists`() {
            val bank = Bank("1", 0.9, 1)
            val performPost = mockMvc.post(url_bank) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(bank)
            }

            performPost
                .andDo { print() }
                .andExpect {
                    status { isBadRequest() }
                }
        }
    }


    @Nested
    @DisplayName("GET/api/bank/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAllBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get("$url_bank/banks")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1") }
                }
        }
    }

    @Nested
    @DisplayName("GET/api/bank/accountNumber")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBank {
        @Test
        fun `should return by accountNumber`() {
            val accountNumber = "1"
            mockMvc.get("$url_bank/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    // status { isNotFound() }
                }
        }


    }

}