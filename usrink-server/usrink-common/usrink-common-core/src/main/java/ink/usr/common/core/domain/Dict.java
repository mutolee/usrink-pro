package ink.usr.common.core.domain;

import java.util.HashMap;

/**
 * 扩展HashMap
 */
public class Dict extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    private Dict() {
    }

    public Dict set(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Dict set(Dict map) {
        super.putAll(map);
        return this;
    }

    public Dict remove(String key) {
        super.remove(key);
        return this;
    }

    public void clear() {
        super.clear();
    }

    public boolean isNotEmpty() {
        return !super.isEmpty();
    }

    public boolean isEmpty() {
        return super.isEmpty();
    }

    public static Dict create() {
        return new Dict();
    }

}
