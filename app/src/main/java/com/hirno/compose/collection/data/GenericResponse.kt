package com.hirno.compose.collection.data

import com.hirno.compose.collection.network.response.NetworkResponse

/**
 * A typealias used for using generic [ErrorResponseModel] as the ErrorModel type of [NetworkResponse]
 */
typealias GenericResponse<S> = NetworkResponse<S, ErrorResponseModel>