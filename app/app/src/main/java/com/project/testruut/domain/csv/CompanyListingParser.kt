package com.project.testruut.domain.csv

import com.opencsv.CSVReader
import com.project.testruut.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        val csvReader = CSVReader(InputStreamReader(stream))
        val batchSize = 100
        var line: Array<String>
        var count = 0
        val mutableList = mutableListOf<CompanyListing>()
        withContext(Dispatchers.IO) {
            csvReader.skip(1)
            while ((csvReader.readNext().also { line = it }) != null && count <= batchSize) {
                val symbol = line.getOrNull(0)
                val series = line.getOrNull(1)
                val open = line.getOrNull(2)
                val high = line.getOrNull(3)
                val low = line.getOrNull(4)
                val close = line.getOrNull(5)

                mutableList.add(
                    CompanyListing(
                        symbol = symbol.toString(),
                        series = series.toString(),
                        close = close.toString(),
                        high = high.toString(),
                        low = low.toString(),
                        open = open.toString()
                    )
                )
                count++
            }

        }
        return mutableList
    }
}