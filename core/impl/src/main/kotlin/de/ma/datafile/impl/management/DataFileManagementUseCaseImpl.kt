package de.ma.datafile.impl.management

import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import java.lang.RuntimeException

class DataFileManagementUseCaseImpl(
    private val dataFileGateway: DataFileGateway,
    private val dataFileContentGateway: DataFileContentGateway
) : DataFileManagementUseCase {


    //create Data File whole process
    override suspend fun dataFileCreate(createDataFile: DataFileCreate): Result<DataFileOverview> {

        //uses the createDataFileUseCase to create a datafile
        val dataFileOverviewResult = dataFileGateway.save(createDataFile)

        //if the datafile was not created
        if (dataFileOverviewResult.isFailure) {
            return Result.failure(
                dataFileOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile")
            )
        }

        //get the datafile overview
        val dataFileOverview = dataFileOverviewResult.getOrNull()!!

        //prepare dataFileContent
        val nanoId = dataFileOverview.id

        val dataFileContentCreate = createDataFile.content

        //create the datafile content
        val dataFileContentOverviewResult = dataFileContentGateway.saveContent(dataFileContentCreate, nanoId)

        //if data file content couldn't be created delete the datafile
        if (dataFileContentOverviewResult.isFailure) {
            val deleted = dataFileGateway.delete(dataFileOverview)

            //if the datafile couldn't be deleted return Result a failure
            val deletedValue = deleted.getOrNull()

            if (deleted.isFailure || deletedValue == null) {
                return Result.failure(
                    dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
                )
            }

            dataFileGateway.purge(deletedValue)
            //if the datafile was deleted return Result a failure
            return Result.failure(
                dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not create datafile content")
            )
        }

        //if the datafile and the datafile content were created return Result a success
        return Result.success(dataFileOverview)
    }

    /*
        delete data file and data file content
     */
    override suspend fun deleteDataFile(dataFileDelete: DataFileDelete): Result<Unit> {

        val dataFileContentDelete = dataFileContentGateway.toContentDelete(dataFileDelete)

        //delete the datafile
        val dataFileDeleteResult = dataFileGateway.delete(dataFileDelete)

        val dataFile = dataFileDeleteResult.getOrNull()

        //if the datafile couldn't be deleted return Result a failure
        if (dataFileDeleteResult.isFailure || dataFile == null) {
            return Result.failure(
                dataFileDeleteResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
            )
        }

        //delete the datafile content
        val dataFileContentDeleteResult = dataFileContentGateway.deleteContent(dataFileContentDelete)

        //if the datafile content couldn't be deleted return Result a failure
        if (dataFileContentDeleteResult.isFailure) {
            dataFileGateway.recover(dataFile)

            return Result.failure(
                dataFileContentDeleteResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile content")
            )
        }

        dataFileGateway.purge(dataFile)
        return Result.success(Unit)
    }

    override suspend fun getDataFile(dataFileSearch: DataFileSearch): Result<DataFileShow> {

        val dataFileShowResult = dataFileGateway.find(dataFileSearch)

        if (dataFileShowResult.isFailure) {
            return Result.failure(
                dataFileShowResult.exceptionOrNull() ?: RuntimeException("Could not get datafile")
            )
        }

        val dataFileShow = dataFileShowResult.getOrNull()!!

        val dataFileContentResult =
            dataFileContentGateway.getContent(dataFileContentGateway.toContentSearch(dataFileSearch))

        if (dataFileContentResult.isFailure) {
            return Result.failure(
                dataFileContentResult.exceptionOrNull() ?: RuntimeException("Could not get datafile content")
            )
        }

        dataFileShow.content = dataFileContentResult.getOrNull()!!
        return Result.success(dataFileShow)
    }


    override suspend fun getDataFiles(
        pagedParams: PagedParams,
        searchParams: SearchParams?,
        sortParams: SortParams?
    ): Result<PagedList<DataFileOverview>> {
        val result = dataFileGateway.findAll(pagedParams, searchParams, sortParams)

        if (result.isFailure) {
            println("Could not get datafiles")
            println(result.exceptionOrNull()?.message ?: "No error message")
            return Result.failure(RuntimeException("Could not get datafiles"))
        }
        return result
    }

}