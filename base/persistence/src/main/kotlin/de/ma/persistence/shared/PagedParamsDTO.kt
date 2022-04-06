package de.ma.persistence.shared

import de.ma.domain.shared.PagedParams

data class PagedParamsDTO(
    override val page: Int,
    override val size: Int
) : PagedParams