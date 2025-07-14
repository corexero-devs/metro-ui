package org.corexero.metroui.utils.collections

object CollectionUtils {
    fun <T> hasDuplicate(iterable: Iterable<T>): Boolean {
        val hashSet = HashSet<T>()
        for (add in iterable) {
            if (!hashSet.add(add)) {
                return true
            }
        }
        return false
    }

    fun <T> getTopElements(list: List<T>, i: Int): List<T> {
        return if (i < list.size) list.subList(0, i) else list
    }
}
