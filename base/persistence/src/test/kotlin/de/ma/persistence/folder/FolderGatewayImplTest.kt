package de.ma.persistence.folder

import de.ma.domain.folder.FolderGateway
import de.ma.persistence.datafile.content.data.DataFileContentCreateDTO
import de.ma.persistence.datafile.data.toEntity
import de.ma.persistence.datafile.utils.DataFileCreateDTO
import de.ma.persistence.datafile.utils.PagedParamsDTO
import de.ma.persistence.folder.data.FolderCreateDTO
import de.ma.persistence.folder.data.FolderEntity
import de.ma.persistence.folder.data.FolderRepository
import de.ma.persistence.folder.data.FolderSearchParamsDTO
import de.ma.persistence.shared.nanoid.NanoIdDTO
import de.ma.persistence.utils.TransactionalQuarkusTest
import de.ma.persistence.utils.sql.Sql
import io.quarkus.hibernate.reactive.panache.Panache
import io.quarkus.hibernate.reactive.panache.PanacheEntity_.id
import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldNotBe
import org.amshove.kluent.shouldNotBeEmpty
import org.hibernate.reactive.mutiny.Mutiny
import org.junit.jupiter.api.Test

import javax.inject.Inject
import javax.transaction.Transactional

@TransactionalQuarkusTest
@ExperimentalCoroutinesApi
class FolderGatewayImplTest {

    @Inject
    lateinit var folderGateway: FolderGateway

    @Inject
    lateinit var folderRepository: FolderRepository


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
        val id = folderEntity.id!!

        val result = folderGateway.deleteFolder(FolderSearchParamsDTO(NanoIdDTO(id.id), null))

        result.isSuccess shouldBe true
        result.getOrNull()!! shouldBe true

        folderRepository.findById(id).chain { folder ->
            folder.deleted shouldBe true
            Mutiny.fetch(folder.dataFiles).map { dataFiles ->
                dataFiles.forEach { dataFile ->
                    dataFile.deleted shouldBe true
                }
            }
        }.awaitSuspending()
    }

    @Test
    fun deleteFolderNegativTest() = runTest {
        val result = folderGateway.deleteFolder(FolderSearchParamsDTO(NanoIdDTO("test"), null))
        result.isSuccess shouldBe false
        println("result: $result")
    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun getFolderByIdTest() = runTest {
        val folderEntity = folderRepository.findAll().firstResult<FolderEntity>().awaitSuspending()
        val id = folderEntity.id ?: throw IllegalStateException("id should not be null")

        val result = folderGateway.getFolderById(id.toEntity())

        result.isSuccess shouldBe true

        val folderShow = result.getOrNull()!!
        folderShow.dataFiles.isNotEmpty() shouldBe true
        folderShow.dataFiles.first() shouldNotBe "2"
    }

    @Test
    @Sql(before = ["tenFolders.sql"], after = ["reset.sql"])
    fun getFoldersPagedTest() = runTest {
        val foldersPaged = folderGateway.getFoldersPaged(PagedParamsDTO(10, 0))

        foldersPaged.isSuccess shouldBe true
        val pagedList = foldersPaged.getOrNull()!!

        pagedList.items.shouldNotBeEmpty()

        pagedList.items.size shouldBe 10

    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun deleteDatafilesFromFolderTest() = runTest {

    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun addDataFileToFolderNegativeTest() = runTest {
    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun addDataFileToFolderTest() = runTest {

    }

    @Test
    fun updateFolder() {
    }

    @Test
    @Sql(before = ["oneFolder.sql"], after = ["reset.sql"])
    fun existsTest() = runTest {
        val folderEntity = folderRepository.findAll().firstResult<FolderEntity>().awaitSuspending()
        val name = folderEntity.name

        val exists = folderGateway.oneFolderWithSameNameExists(name)
        exists shouldBe true
    }

    @Test
    fun hasDataFileWithNameById() {
    }
}