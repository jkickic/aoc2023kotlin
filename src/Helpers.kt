class Helpers {


    companion object {
        fun loadResourceFile(resourcePath: String): List<String> {
            val content = Helpers::class.java.getResource(resourcePath).readText()
            return content.split(System.lineSeparator())
        }
    }
}