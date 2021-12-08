import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.reflect.Type
import java.util.zip.Inflater

const val REPOSITORY = "D:\\_Projects\\_Mirea\\simple_repo\\simple_repo\\.git\\"
const val BRANCHES = "refs\\heads\\"
const val OBJECTS = "objects\\"

fun ByteArray.zlibDecompress(): String {
	val inflater = Inflater()
	val outputStream = ByteArrayOutputStream()

	return outputStream.use {
		val buffer = ByteArray(1024)

		inflater.setInput(this)

		var count = -1
		while (count != 0) {
			count = inflater.inflate(buffer)
			outputStream.write(buffer, 0, count)
		}

		inflater.end()
		outputStream.toString("UTF-8")
	}
}

fun String.getFile(path: String = "${REPOSITORY}${OBJECTS}"): File {
	val directory = substring(0..1)
	val filename = substring(2)
	return File("$path$directory\\$filename")
}

fun <R> MutableList<R>.setLast(transform: (R) -> R): List<R> {
	val iterator = this.listIterator()
	while (iterator.hasNext()) iterator.next()
	iterator.set(transform(iterator.previous()))
	return this
}

fun <R> List<R>.split(predicate: (R) -> Boolean): List<List<R>> {
	val lists = mutableListOf<MutableList<R>>()
	var listIndex = 0
	this.forEach {
		if (predicate(it)) {
			listIndex++
		} else {
			if (listIndex >= lists.size) {
				lists.add(mutableListOf(it))
			} else {
				lists[listIndex].add(it)
			}
		}
	}
	return lists
}

fun List<Byte>.mapToString(): String = this.map { it.toInt().toChar() }.joinToString("")

fun List<Byte>.toHex(): String = joinToString(separator = "") { eachByte -> "%02x".format(eachByte) }

fun ByteArray.zlibDecompressByte(): ByteArray {
	val inflater = Inflater()
	val outputStream = ByteArrayOutputStream()

	return outputStream.use {
		val buffer = ByteArray(1024)

		inflater.setInput(this)

		var count = -1
		while (count != 0) {
			count = inflater.inflate(buffer)
			outputStream.write(buffer, 0, count)
		}

		inflater.end()
		outputStream.toByteArray()
	}
}