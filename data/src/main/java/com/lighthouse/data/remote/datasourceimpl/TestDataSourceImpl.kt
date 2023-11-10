package com.lighthouse.data.remote.datasourceimpl

import com.lighthouse.data.api.TestAPI
import com.lighthouse.data.remote.datasource.TestDataSource
import com.lighthouse.data.util.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TestDataSourceImpl @Inject constructor(
    private val api: TestAPI
) : TestDataSource, NetworkResponse() {
    override fun getTest(): Flow<String> = flow {
        emit(changeResult(api.getTest()))
    }
}