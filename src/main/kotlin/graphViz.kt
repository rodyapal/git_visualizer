import git_objects.Commit

fun toGraphviz(head: Commit): String = "digraph GirRepository {\n${head.toGraphviz()}\n}"