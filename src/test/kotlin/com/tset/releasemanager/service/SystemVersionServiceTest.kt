package com.tset.releasemanager.service

import com.tset.releasemanager.model.ServiceVersion
import com.tset.releasemanager.repository.mock.SystemVersionRepoImpl
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.test.annotation.DirtiesContext
import java.lang.IllegalArgumentException

internal class SystemVersionServiceTest {

    val systemVersionService = SystemVersionService(SystemVersionRepoImpl())

    @Test
    fun `save and check logic`() {
        // given
        val t1 = ServiceVersion(1, "service A")
        val t2 = ServiceVersion(2, "service A")
        val t3 = ServiceVersion(3, "service A")
        val t4 = ServiceVersion(1, "service B")
        val t5 = ServiceVersion(3, "service A")
        val t6 = ServiceVersion(1, "service A")

        // when
        val v1 = systemVersionService.deploy(t1)
        val v2 = systemVersionService.deploy(t2)
        val v3 = systemVersionService.deploy(t3)
        val v4 = systemVersionService.deploy(t4)
        val v5 = systemVersionService.deploy(t5)
        val v6 = systemVersionService.deploy(t6)

        // then
        assertThat(v1).isEqualTo(1)
        assertThat(v2).isEqualTo(2)
        assertThat(v3).isEqualTo(3)
        assertThat(v4).isEqualTo(4)
        assertThat(v5).isEqualTo(4)
        assertThat(v6).isEqualTo(5)

        assertThat(systemVersionService.loadService(1).size == 1)
        assertThat(systemVersionService.loadService(2).size == 1)
        assertThat(systemVersionService.loadService(4).size == 2)
        assertThrows<IllegalArgumentException> { systemVersionService.loadService(-1) }
        assertThrows<IllegalArgumentException> { systemVersionService.loadService(0) }
        assertThrows<NoSuchElementException> { systemVersionService.loadService(100) }
    }
}
