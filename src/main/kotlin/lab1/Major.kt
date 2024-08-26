package lab1

class Major(val name: String) {
    private val students = mutableListOf<Student>()

    fun addStudent(student: Student) {
        students.add(student)
    }

    fun stats(): Triple<Double, Double, Double> {
        val allAverages = students.map { it.weightedAverage() }
        val min = allAverages.minOrNull() ?: 0.0
        val max = allAverages.maxOrNull() ?: 0.0
        val average = if (allAverages.isNotEmpty()) allAverages.sum() / allAverages.size else 0.0
        return Triple(min, max, average)
    }

    fun stats(courseName: String): Triple<Double, Double, Double> {
        val filteredAverages = students.map { student ->
            student.weightedAverage(courseName)
        }
        val min = filteredAverages.minOrNull() ?: 0.0
        val max = filteredAverages.maxOrNull() ?: 0.0
        val average = if (filteredAverages.isNotEmpty()) filteredAverages.sum() / filteredAverages.size else 0.0
        return Triple(min, max, average)
    }
}