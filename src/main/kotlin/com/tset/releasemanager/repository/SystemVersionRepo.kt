package com.tset.releasemanager.repository

import com.tset.releasemanager.model.SystemVersion

interface SystemVersionRepo {
    fun save(version: SystemVersion)

    fun loadOne(id : Long): SystemVersion

    fun loadLatest(): SystemVersion
}
