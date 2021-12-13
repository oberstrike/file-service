package de.ma.datafile.impl.shared

import de.ma.domain.nanoid.NanoId
import de.ma.domain.nanoid.NanoIdGateway

interface BaseUseCase {
    fun String.toNanoId(): NanoId
}

class BaseUseCaseImpl(
    private val nanoIdGateway: NanoIdGateway
) : BaseUseCase {
    override fun String.toNanoId() = nanoIdGateway.toNanoId(this)
}