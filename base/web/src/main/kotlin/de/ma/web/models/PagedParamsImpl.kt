package de.ma.web.models

import de.ma.domain.shared.PagedParams

data class PagedParamsImpl(
    override val page: Int,
    override val size: Int
): PagedParams