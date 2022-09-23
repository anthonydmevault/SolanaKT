package com.solana.api

import com.solana.networking.RpcRequestSerializable
import com.solana.networking.makeRequestResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.serializer

class GetSnapshotSlot: RpcRequestSerializable() {
    override val method: String = "getSnapshotSlot"
}

internal fun GetSnapshotSlotSerializer() = Long.serializer()

fun Api.getSnapshotSlot(onComplete: (Result<Long>) -> Unit) {
    CoroutineScope(dispatcher).launch {
        onComplete(getSnapshotSlot())
    }
}

suspend fun Api.getSnapshotSlot(): Result<Long> =
    router.makeRequestResult(GetSnapshotSlot(), GetSnapshotSlotSerializer())
        .let { result ->
            @Suppress("UNCHECKED_CAST")
            if (result.isSuccess && result.getOrNull() == null)
                Result.failure(Error("Can not be null"))
            else result as Result<Long> // safe cast, null case handled above
        }