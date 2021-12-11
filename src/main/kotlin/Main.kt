import git_objects.Commit
import java.io.File

fun main(args: Array<String>) {
	val branches = File("${REPOSITORY}${BRANCHES}")
	for (branch in branches.listFiles()) {
		branch.forEachLine { branchHeadCommit ->
			val commit = Commit.from(branchHeadCommit)
		}
	}
}
