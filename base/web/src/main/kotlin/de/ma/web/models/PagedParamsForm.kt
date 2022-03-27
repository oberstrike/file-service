package de.ma.web.models

import de.ma.domain.shared.PagedParams
import javax.ws.rs.DefaultValue
import javax.ws.rs.QueryParam

data class PagedParamsImpl(
    override val size: Int,
    override val page: Int
) : PagedParams

class PagedParamsForm {
    @DefaultValue("0")
    @QueryParam("page")
    var page: Int = 0

    @DefaultValue("10")
    @QueryParam("pageSize")
    var size: Int = 10
}

fun PagedParamsForm.toPagedParams(): PagedParams {
    return PagedParamsImpl(page, size)
}