package com.lighthouse.data.remote.datasource

import kotlinx.coroutines.flow.Flow

interface TestDataSource {
    fun getTest(): Flow<String>
}