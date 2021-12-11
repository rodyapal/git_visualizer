package git_objects

import OBJECTS
import REPOSITORY
import zlibDecompress
import java.io.File

data class Blob(
	val hash: String,
	val content: String,
	val name: String
) {

	fun toGraphviz(hashPrefix: String = ""): String =
		"\"$hashPrefix$hash\" [shape=ellipse, label=\"${name}\"];\n"

	companion object {

		fun from(hash: String, name: String = ""): Blob {
			val blobFile = File("$REPOSITORY$OBJECTS${hash.take(2)}\\${hash.substring(2)}")
			val content = blobFile
				.readBytes()
				.zlibDecompress()
				.split(0.toChar())
				.drop(1)
				.joinToString("")
			return Blob(hash, content, name)
		}
	}
}
