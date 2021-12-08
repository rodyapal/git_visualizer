package git_objects

import OBJECTS
import REPOSITORY
import mapToString
import split
import toHex
import zlibDecompressByte
import java.io.File

data class Tree(
	val hash: String,
	val name: String
) {
	private val _trees = mutableListOf<Tree>()
	val trees get() = _trees as List<Tree>

	private val _blobs = mutableListOf<Blob>()
	val blobs get() = _blobs as List<Blob>


	companion object {
		private const val BLOB_MODE = "100644"
		private const val TREE_MODE = "40000"

		fun from(hash: String, name: String = "repository"): Tree {
			val treeFile = File("$REPOSITORY$OBJECTS${hash.take(2)}\\${hash.substring(2)}")
			val byteLists = treeFile.readBytes().zlibDecompressByte()
				.toList()
				.split { it == 0.toByte() }
				.drop(1)
			val hashes = byteLists
				.drop(1)
				.mapIndexed { index, bytes ->
					if (index == byteLists.lastIndex - 1) {
						bytes.toHex()
					} else {
						bytes.subList(0, 20).toHex()
					}
				}
			val entities = byteLists
				.dropLast(1)
				.mapIndexed { index, bytes ->
					if (index == 0) bytes.mapToString() else bytes.subList(20, bytes.lastIndex + 1).mapToString()
				}

			return Tree(hash, name).apply {
				for (index in hashes.indices) {
					val (mode, objectName) = entities[index].split(" ", limit = 2)
					if (mode == TREE_MODE) {
						_trees.add(
							Tree.from(hashes[index], objectName)
						)
					} else {
						_blobs.add(
							Blob.from(hashes[index])
						)
					}
				}
			}
		}
	}
}
