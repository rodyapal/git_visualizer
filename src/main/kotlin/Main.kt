import git_objects.Commit
import java.io.File

fun main(args: Array<String>) {
	val branches = File("${REPOSITORY}${BRANCHES}")
//	val hash = "0ab6bd53e2bc1876b7948165ee37cc08b605887b"
	for (branch in branches.listFiles()) {
		branch.forEachLine { branchHeadCommit ->
			val commit = Commit.from(branchHeadCommit)
		}
	}
//	val treeFile = File("$REPOSITORY$OBJECTS${hash.take(2)}\\${hash.substring(2)}")
//	val byteLists = treeFile.readBytes().zlibDecompressByte()
//	println(byteLists.toList().toHex())
//		.toList()
//		.split { it == 0.toByte() }
//		.drop(1)
//	println(byteLists.toHex())
//	byteLists.forEach {
//		val a = it.mapToString()
//		println(a)
//	}
//	println()
//	val hashes = byteLists
//		.drop(1)
//		.mapIndexed { index, bytes ->
//			val str = bytes.mapToString()
//			if (index == byteLists.lastIndex - 1) {
//				bytes.toHex()
//			} else {
//				bytes.subList(0, 20).toHex()
//			}
//		}
//	hashes.forEach {
//		println(it)
//	}
//	val entities = byteLists
//		.dropLast(1)
//		.mapIndexed { index, bytes ->
//			if (index == 0) bytes.mapToString() else bytes.subList(20, bytes.lastIndex + 1).mapToString()
//		}
//	println()
//	entities.forEach {
//		println(it)
//	}
}