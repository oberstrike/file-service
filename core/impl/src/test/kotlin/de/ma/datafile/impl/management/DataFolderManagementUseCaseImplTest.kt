package de.ma.datafile.impl.management

import de.ma.datafile.DataFileManagementUseCaseImpl
import de.ma.datafile.impl.utils.AbstractImplTest
import de.ma.domain.content.DataFileContentGateway
import de.ma.domain.datafile.DataFileGateway
import io.mockk.mockk
import org.junit.jupiter.api.Test

class DataFolderManagementUseCaseImplTest : AbstractImplTest() {

    private val dataFileGateway = mockk<DataFileGateway>()

    private val dataFileContentGateway = mockk<DataFileContentGateway>()

    private val dataFileManagement = DataFileManagementUseCaseImpl(
        dataFileGateway,
        dataFileContentGateway
    )


    @Test
    fun `if a file already exists it throws an exception`() {

    }


    @Test
    fun `create successfully a data file`() {


    }

    @Test
    fun deleteDataFile() {

    }
}