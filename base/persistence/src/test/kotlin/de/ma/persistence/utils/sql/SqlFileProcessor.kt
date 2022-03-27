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
        println("Processing file: $targetFile")

        val mutinySessionFactory = getMutinySessionFactory()


        val inputStream =
            this::class.java.getResource(targetFile)?.openStream() ?: throw FileNotFoundException(
                "File not found: $targetFile"
            )

        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        var nextCommand = ""
        var isDo = false

        bufferedReader.forEachLineAsync { line ->
            if (line.startsWith("--")) {
                return@forEachLineAsync

            }
            nextCommand += line

            if( isDo &&  !line.startsWith("END \$\$;")) {
                return@forEachLineAsync
            }


            if(line.startsWith("DO")) {
                isDo = true
                return@forEachLineAsync
            }

            if (line.endsWith(";") || line.startsWith("END \$\$;")) {
                mutinySessionFactory.withSession {
                    it.createNativeQuery<Unit>(nextCommand).executeUpdate()
                }.awaitSuspending()
                isDo = false
                nextCommand = ""
            }
        }
        println("Processed file: $targetFile")
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