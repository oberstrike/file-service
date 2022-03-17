package de.ma.web.models

import de.ma.domain.shared.SortParam

data class SortParamsImpl(
    override val sortBy: String,
    override val direction: String
): SortParam


data class SortParamsContainer(val sortParams: List<SortParam>)