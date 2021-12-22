package de.ma.datafile.impl.management

import de.ma.datafile.api.content.CreateDataFileContentUseCase
import de.ma.datafile.api.content.DeleteDataFileContentUseCase
import de.ma.datafile.api.content.GetDataFileContentUseCase
import de.ma.datafile.api.datafile.CreateDataFileUseCase
import de.ma.datafile.api.management.DataFileManagementUseCase
import de.ma.datafile.api.datafile.DeleteDataFileUseCase
import de.ma.datafile.api.datafile.GetDataFileUseCase
import de.ma.datafile.api.datafile.GetDataFilesUseCase
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.*
import de.ma.domain.shared.PagedList
import de.ma.domain.shared.PagedParams
import de.ma.domain.shared.SearchParams
import de.ma.domain.shared.SortParams
import java.lang.RuntimeException

class DataFileManagementUseCaseImpl(
    private val createDataFileContentUseCase: CreateDataFileContentUseCase,
    private val createDataFileUseCase: CreateDataFileUseCase,
    private val deleteDataFileUseCase: DeleteDataFileUseCase,
    private val deleteDataFileContentUseCase: DeleteDataFileContentUseCase,
    private val getDataFileUseCase: GetDataFileUseCase,
    private val getDataFileContentUseCase: GetDataFileContentUseCase,
    private val getDataFilesUseCase: GetDataFilesUseCase,
    private val dataFileContentGateway: DataFileContentGateway
) : DataFileManagementUseCase {


    //create Data File whole process
    override suspend fun createDataFile(createDataFile: DataFileCreate): Result<DataFileOverview> {

        //uses the createDataFileUseCase to create a datafile
        val dataFileOverviewResult = createDataFileUseCase(createDataFile)

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
        val dataFileContentOverviewResult = createDataFileContentUseCase(dataFileContentCreate, nanoId)

        //if data file content couldn't be created delete the datafile
        if (dataFileContentOverviewResult.isFailure) {
            val deleted = deleteDataFileUseCase(dataFileOverview)

            //if the datafile couldn't be deleted return Result a failure
            if (deleted.isFailure || deleted.getOrNull() == false) {
                return Result.failure(
                    dataFileContentOverviewResult.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
                )
            }

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
    override suspend fun deleteDataFile(deleteDataFile: DataFileDelete): Result<Unit> {

        //first delete the datafile content
        val dataFileContentDeleted = deleteDataFileContentUseCase(
            dataFileContentGateway.toContentDelete(deleteDataFile)
        )

        //if the datafile content couldn't be deleted
        if (dataFileContentDeleted.isFailure) {
            return Result.failure(
                dataFileContentDeleted.exceptionOrNull() ?: RuntimeException("Could not delete datafile content")
            )
        }

        //then delete the datafile
        val dataFileDeleted = deleteDataFileUseCase(deleteDataFile)

        if (dataFileDeleted.isFailure) {
            //recover the datafile content


            return Result.failure(
                dataFileDeleted.exceptionOrNull() ?: RuntimeException("Could not delete datafile")
            )
        }


        return Result.success(Unit)
    }

    override suspend fun getDataFile(dataFileSearch: DataFileSearch): Result<DataFileShow> {

        val dataFileShowResult = getDataFileUseCase(dataFileSearch)

        if (dataFileShowResult.isFailure) {
            return Result.failure(
                dataFileShowResult.exceptionOrNull() ?: RuntimeException("Could not get datafile")
            )
        }

        val dataFileShow = dataFileShowResult.getOrNull()!!

        val dataFileContentResult = getDataFileContentUseCase(dataFileContentGateway.toContentSearch(dataFileSearch))

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
        val result = getDataFilesUseCase(pagedParams, searchParams, sortParams)

        if (result.isFailure) {
            println("Could not get datafiles")
            println(result.exceptionOrNull()?.message ?: "No error message")
            return Result.failure(RuntimeException("Could not get datafiles"))
        }
        return result
    }

}