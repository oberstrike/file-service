package de.ma.web.datafile

import java.nio.file.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class Watcher {


    fun setup() {

        try {
            val key = Path.of("/home/ma/key.pem")

            val watchService =
                FileSystems.getDefault().newWatchService()

            val path = Paths.get("/home/oberstrike/Desktop/content")

            path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE
            )

            val watchKey = watchService.poll()

            watchService.take()


        } catch (e: Exception) {
        }


    }

}
