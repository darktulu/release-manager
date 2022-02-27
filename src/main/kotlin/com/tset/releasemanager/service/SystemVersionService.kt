package com.tset.releasemanager.service

import com.tset.releasemanager.model.ServiceVersion
import com.tset.releasemanager.model.SystemVersion
import com.tset.releasemanager.repository.SystemVersionRepo
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class SystemVersionService(val systemVersionRepo: SystemVersionRepo) {

    fun deploy(serviceVersion: ServiceVersion): Long {
        val latest = systemVersionRepo.loadLatest()
        val originalVersion = latest.services[serviceVersion.name]

        if (originalVersion != null && originalVersion.version == serviceVersion.version) {
            return latest.version
        }

        // new System version to be created
        val newSystem = HashMap(latest.services)
        newSystem[serviceVersion.name] = serviceVersion

        val version = SystemVersion(latest.version + 1, newSystem)
        systemVersionRepo.save(version)

        return version.version
    }

    fun loadService(systemVersion: Long): List<ServiceVersion> {
        if (systemVersion <= 0) {
            throw IllegalArgumentException("No such version available v$systemVersion.")
        }

        return systemVersionRepo.loadOne(systemVersion)
            .services.map { (_, v) -> v }
            .toList()
    }
}
