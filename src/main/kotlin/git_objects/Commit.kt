package git_objects

import OBJECTS
import REPOSITORY
import setLast
import zlibDecompress
import java.io.File

data class Commit(
	val hash: String,
	val treeHash: String,
	val parentHash: String,
	val author: String,
	val committer: String,
	val description: String
) {
	val tree = Tree.from(treeHash)
	val parent = Commit.from(parentHash)

	fun toGraphviz(): String {
		var result = "\"$hash\" [shape=rect, color=gold, label=\"$description\"];\n"
		result += "\"$hash\" -> \"$hash${tree.hash}\";\n${tree.toGraphviz(hash)}"
		parent?.let {
			result += "\"$hash\" -> \"${parent.hash}\";\n${parent.toGraphviz()}"
		}
		return result
	}

	companion object {
		fun from(hash: String): Commit? {
			val commitFile = File("$REPOSITORY$OBJECTS${hash.take(2)}\\${hash.substring(2)}")
			val content = commitFile
				.readBytes()
				.zlibDecompress()
				.split(0.toChar(), '\n')
				.filter { it.isNotEmpty() }
				.toMutableList()
				.setLast { "description $it" }
				.map { it.split(' ', limit = 2) }
				.associate { it[0] to it[1] }

			return if (!content.containsKey("parent")) null
			else Commit(hash, content["tree"]!!, content["parent"]!!, content["author"]!!, content["committer"]!!, content["description"]!!)
		}
	}
}