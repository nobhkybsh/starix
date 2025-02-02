package com.y9vad9.starix.foundation.time

import com.y9vad9.starix.foundation.validation.CreationFailure
import com.y9vad9.starix.foundation.validation.ValueConstructor
import com.y9vad9.starix.foundation.validation.reflection.wrapperTypeName
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * The class that represents unix-time.
 *
 * @see TimeProvider
 */
@JvmInline
public value class UnixTime private constructor(private val long: Long) {
    /**
     * Minuses current unix time from another.
     * @return [Duration] between given times.
     */
    public operator fun minus(other: UnixTime): Duration {
        return (long - other.long).milliseconds
    }

    /**
     * Pluses current unix time and given [Duration].
     *
     * @return [UnixTime] + [Duration]
     */
    public operator fun plus(duration: Duration): UnixTime {
        val result = try {
            Math.addExact(long, duration.inWholeMilliseconds)
        } catch (_: Throwable) {
            INFINITE.long
        }

        require(result >= 0) { "Unix time cannot be negative" }

        return UnixTime(result)
    }

    /**
     * Minuses current unix time and given [Duration].
     *
     * @return [UnixTime] - [Duration]
     */
    public operator fun minus(duration: Duration): UnixTime {
        val result = long - duration.inWholeMilliseconds
        require(result >= 0) { "Unix time cannot be negative" }

        return UnixTime(long - duration.inWholeMilliseconds)
    }

    /**
     * Returns value in milliseconds
     */
    public val inMilliseconds: Long
        get() = long

    public operator fun compareTo(other: UnixTime): Int = long.compareTo(other.long)

    public companion object : ValueConstructor<UnixTime, Long> {
        override val displayName: String by wrapperTypeName()

        public val ZERO: UnixTime = UnixTime(0)
        public val INFINITE: UnixTime = UnixTime(Long.MAX_VALUE)


        override fun create(value: Long): Result<UnixTime> {
            return when {
                value < 0 -> Result.failure(CreationFailure.ofMin(0))
                else -> Result.success(UnixTime(long = value))
            }
        }
    }
}