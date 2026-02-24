package com.tbc.core.domain.util


sealed interface Resource<out D, out E : Error> {
    data class Success<out D>(val data: D) : Resource<D, Nothing>
    data class Failure<out E : Error>(val error: E) : Resource<Nothing, E>
}


fun <D> D.asSuccess() = Resource.Success(data = this)
fun <E : Error> E.asFailure() = Resource.Failure(error = this)


inline fun <T, D, E : Error> Resource<D, E>.map(transform: (D) -> T) =
    when (this) {
        is Resource.Success -> Resource.Success(transform(data))
        is Resource.Failure -> Resource.Failure(error)
    }

inline fun <T, D, E : Error> Resource<List<D>, E>.mapList(transform: (D) -> T) =
    map { it.map(transform) }


inline fun <D, E : Error> Resource<D, E>.onSuccess(action: (D) -> Unit): Resource<D, E> {
    if (this is Resource.Success)
        action(data)

    return this
}

inline fun <D, E : Error> Resource<D, E>.onFailure(action: (E) -> Unit): Resource<D, E> {
    if (this is Resource.Failure)
        action(error)

    return this
}
