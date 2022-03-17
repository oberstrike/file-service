package de.ma.persistence.datafile.utils

import de.ma.domain.shared.SortParam

data class SortParamsImpl(
    override val direction: String,
    override val sortBy: String
): SortParam