package com.tset.releasemanager.repository.mock

import com.tset.releasemanager.model.SystemVersion
import com.tset.releasemanager.repository.SystemVersionRepo
import org.springframework.stereotype.Repository

@Repository
class SystemVersionRepoImpl : SystemVersionRepo {

    // Mock of data
    var mockData: List<SystemVersion> = mutableListOf(SystemVersion(0, HashMap()))

    override fun save(version: SystemVersion) {
        mockData = mockData + version
    }

    override fun loadOne(id: Long): SystemVersion {
        if (id >= mockData.size) throw NoSuchElementException("No such version available v$id.")
        return mockData[id.toInt()]
    }

    override fun loadLatest(): SystemVersion {
        return mockData.last()
    }
}
