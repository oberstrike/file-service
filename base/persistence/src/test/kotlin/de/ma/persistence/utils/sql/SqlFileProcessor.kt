package de.ma.persistence.utils.sql

import io.smallrye.mutiny.coroutines.awaitSuspending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.hibernate.reactive.mutiny.Mutiny
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import javax.enterprise.inject.spi.CDI

object SqlFileProcessor {
    suspend fun processTargetFile(targetFile: String) = withContext(Dispatchers.IO) {
        val mutinySessionFactory = getMutinySessionFactory()

        val inputStream =
            this::class.java.getResource(targetFile)?.openStream() ?: throw FileNotFoundException(
                "File not found: $targetFile"
            )

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        var nextCommand = ""

        bufferedReader.forEachLineAsync { line ->
            if (line.startsWith("--")) {
                return@forEachLineAsync
            }
            nextCommand += line

            if (line.endsWith(";")) {
                mutinySessionFactory.withSession {
                    it.createNativeQuery<Unit>(nextCommand).executeUpdate()
                }.awaitSuspending()
                nextCommand = ""
            }
        }
    }

    private fun getMutinySessionFactory(): Mutiny.SessionFactory {
        try {
            return CDI.current().select(Mutiny.SessionFactory::class.java).get()
        } catch (e: Exception) {
            throw RuntimeException("Could not get Mutiny Session Factory", e)
        }
    }

    private suspend fun BufferedReader.forEachLineAsync(action: suspend (String) -> Unit) {
        forEachLine {
            runBlocking {
                action(it)
            }
        }
    }

}