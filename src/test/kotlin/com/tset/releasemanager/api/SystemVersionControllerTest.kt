package com.tset.releasemanager.api

import com.fasterxml.jackson.databind.ObjectMapper
import com.tset.releasemanager.model.ServiceVersion
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
internal class SystemVersionControllerTest {

    @Autowired
    lateinit var client: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    fun deploy() {
        // given
        val a1 = ServiceVersion(1, "service A")
        val b1 = ServiceVersion(1, "service B")
        val a2 = ServiceVersion(2, "service A")

        // when/then
        createPost(a1)
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { string("1") }
            }

        createPost(b1)
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { string("2") }
            }

        createPost(a2)
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { string("3") }
            }

        createPost(b1)
            .andDo { print() }
            .andExpect {
                status { isCreated() }
                content { string("3") }
            }
    }

    @Test
    fun services() {
        createGet("1")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { jsonPath("$[0].version") { value("1") } }
            }

        createGet("3")
            .andDo { print() }
            .andExpect {
                status { isOk() }
                content { jsonPath("$[0].version") { value("2") } }
            }
    }

    @Test
    fun noValidVersion() {
        createGet("5")
            .andDo { print() }
            .andExpect { status { isNotFound() } }
    }

    @Test
    fun noValidArgument() {
        createGet("-1")
            .andDo { print() }
            .andExpect { status { isBadRequest() } }

        createGet("0")
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
    }

    private fun createGet(v: String) = client.get("/services") {
        param("systemVersion", v)
    }

    private fun createPost(v: ServiceVersion) = client.post("/deploy") {
        contentType = MediaType.APPLICATION_JSON
        content = mapper.writeValueAsString(v)
    }
}
