package de.ma.persistence.folder

import de.ma.domain.folder.FolderGateway
import de.ma.persistence.datafile.data.DataFileRepository
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.folder.data.FolderCreateDTO
import de.ma.persistence.folder.data.FolderEntity
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.folder.data.FolderSearchParamsDTO
import de.ma.persistence.shared.nanoid.NanoIdDTO
import de.ma.persistence.utils.TransactionalQuarkusTest
import de.ma.persistence.utils.sql.Sql
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.jupiter.api.Test

import javax.inject.Inject

@TransactionalQuarkusTest
@ExperimentalCoroutinesApi
class FolderGatewayImplTest {

    @Inject
    lateinit var folderGateway: FolderGateway

    @Inject
    lateinit var dataFileRepository: DataFileRepository

    @Inject
    lateinit var folderRepository : FolderRepository

    @Test
    @Sql(after = ["reset.sql"])
    fun createFolderTest() = runTest {
        val result = folderGateway.createFolder(FolderCreateDTO("test"))
        result.isSuccess shouldBe true
        println("result: $result")
    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun deleteFolderTest() = runTest {
        val folderEntity = folderRepository.findAll().firstResult<FolderEntity>().awaitSuspending()
        val id = folderEntity.id!!.id

        val result = folderGateway.deleteFolder(FolderSearchParamsDTO(NanoIdDTO(id), null))

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe true
    }

    @Test
    fun deleteFolderNegativTest() = runTest {
        val result = folderGateway.deleteFolder(FolderSearchParamsDTO(NanoIdDTO("test"), null))
        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe false
    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun getFolderByIdTest() = runTest{
        val folderEntity = folderRepository.findAll().firstResult<FolderEntity>().awaitSuspending()
        val id = folderEntity.id?: throw IllegalStateException("id should not be null")

        val result = folderGateway.getFolderById(id.toEntity())
        result.isSuccess shouldBe true
    }

    @Test
    @Sql(before = ["tenFolders.sql"], after = ["reset.sql"])
    fun getFoldersPaged() {
        println("getFoldersPaged")
    }

    @Test
    fun deleteDatafilesFromFolder() {
    }

    @Test
    fun addDataFileToFolder() {
    }

    @Test
    fun updateFolder() {
    }

    @Test
    fun exists() {
    }

    @Test
    fun hasDataFileWithNameById() {
    }
}