package yangj.simpleplugin.utils

import java.lang.reflect.Field

/**
 * Created by YangJ on 2018/11/16.
 */
object FieldUtils {

    fun getField(clazz: Class<*>, name: String): Field {
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field
    }

    fun getField(cls: String, name: String): Field {
        val clazz = Class.forName(cls)
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field
    }

    fun setField(clazz: Class<*>, obj: Any, name: String, value: Any) {
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        field.set(obj, value)
    }

    fun getObject(clazz: Class<*>, obj: Any?, name: String): Any? {
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field.get(obj)
    }

    fun getObject(cls: String, obj: Any?, name: String): Any? {
        val clazz = Class.forName(cls)
        val field = clazz.getDeclaredField(name)
        field.isAccessible = true
        return field.get(obj)
    }
}