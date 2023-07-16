package com.hirno.compose.collection.utils

/**
 * Casts its receiver into the desired type if it was an instance of that type
 *
 * @return The object reference casted into [R] or null otherwise
 */
inline fun <T, reified R> T.cast(transform: (T) -> R): R? {
    return if (this is R) transform(this) else null
}