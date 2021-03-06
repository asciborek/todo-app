package com.github.asciborek.todolist.report

interface ReportGenerator {

    fun createReport(): ByteArray

    fun getSupportedExtension(): Extension

}

enum class Extension(val fileExtension: String) {
    CSV("csv"), PDF("pdf");
}