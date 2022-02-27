package com.tset.releasemanager.model

data class SystemVersion(val version: Long, val services: Map<String, ServiceVersion>)

data class ServiceVersion(val version: Long, val name: String)
