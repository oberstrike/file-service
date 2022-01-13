package de.ma.persistence.datafile.utils

import de.ma.domain.shared.SearchParams

data class SearchParamsImpl(
    override val field: String,
    override val content: String
) : SearchParams