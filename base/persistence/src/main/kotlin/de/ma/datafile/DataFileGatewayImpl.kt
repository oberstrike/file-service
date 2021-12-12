package de.ma.datafile

import de.ma.domain.datafile.com.DataFileCreate
import de.ma.domain.datafile.DataFileGateway
import de.ma.domain.datafile.DataFileShowView
import de.ma.domain.datafile.shared.NanoId
import de.ma.domain.datafile.shared.PagedList
import de.ma.domain.datafile.shared.PagedParams
import de.ma.domain.datafile.shared.SearchParams

class DataFileGatewayImpl(
    private val dataFileRepository: DataFileRepository
): DataFileGateway {
    override fun findById(id: NanoId): DataFileShowView? {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: NanoId): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : DataFileCreate> save(dataFile: T): Result<DataFileShowView> {
        TODO("Not yet implemented")
    }

    override fun findAll(pagedParams: PagedParams, searchParams: SearchParams?): Result<PagedList<DataFileShowView>> {
        TODO("Not yet implemented")
    }


}