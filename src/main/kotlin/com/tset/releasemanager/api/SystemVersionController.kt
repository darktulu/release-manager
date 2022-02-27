package com.tset.releasemanager.api

import com.tset.releasemanager.model.ServiceVersion
import com.tset.releasemanager.service.SystemVersionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@RestController
@RequestMapping
class SystemVersionController(val systemVersionService: SystemVersionService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchVersion(e: NoSuchElementException) = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(e: IllegalArgumentException) = ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @PostMapping("/deploy")
    @ResponseStatus(HttpStatus.CREATED)
    fun deploy(@RequestBody serviceVersion: ServiceVersion): Long = systemVersionService.deploy(serviceVersion)

    @GetMapping("/services")
    fun services(@RequestParam systemVersion: Long) = systemVersionService.loadService(systemVersion)
}
