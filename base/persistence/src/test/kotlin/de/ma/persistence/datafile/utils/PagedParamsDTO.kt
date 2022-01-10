package de.ma.persistence.datafile.utils

import de.ma.domain.shared.PagedParams

data class PagedParamsDTO(
    override val size: Int,
    override val page: Int
): PagedParams