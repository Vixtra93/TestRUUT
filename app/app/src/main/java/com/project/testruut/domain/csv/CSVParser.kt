package com.project.testruut.domain.csv

import java.io.InputStream

interface CSVParser<T> {
    suspend fun  parse(stream: InputStream) : List<T>

}