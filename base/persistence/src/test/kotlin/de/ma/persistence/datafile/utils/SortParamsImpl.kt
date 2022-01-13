package de.ma.persistence.datafile.utils

import de.ma.domain.shared.SortParams

data class SortParamsImpl(
    override val direction: String,
    override val sortBy: String
): SortParams