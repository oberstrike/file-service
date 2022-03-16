package de.ma.web.models

import de.ma.domain.nanoid.NanoId

data class NanoIdImpl(
    override var id: String
): NanoId