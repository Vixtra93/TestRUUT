package com.project.testruut.di

import com.project.testruut.domain.csv.CSVParser
import com.project.testruut.domain.csv.CompanyListingParser
import com.project.testruut.domain.model.CompanyListing
import com.project.testruut.data.repository.StockRepository
import com.project.testruut.data.repository.StockRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImp: StockRepositoryImp
    ): StockRepository


}