package git_objects

import OBJECTS
import REPOSITORY
import zlibDecompress
import java.io.File

data class Blob(
	val hash: String,
	val content: String
) {

	companion object {

		fun from(hash: String): Blob {
			val blobFile = File("$REPOSITORY$OBJECTS${hash.take(2)}\\${hash.substring(2)}")
			val content = blobFile
				.readBytes()
				.zlibDecompress()
				.split(0.toChar())
				.drop(1)
				.joinToString("")
			return Blob(hash, content)
		}
	}
}
