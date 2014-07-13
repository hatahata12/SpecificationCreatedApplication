package itipxnozakki.com.lib.data.parsedata;

import java.util.HashMap;
import java.util.Map;

public class Leaf {
    private String key;
    private String data;
    private Map<String, String> attr = new HashMap<String, String>();

    public Leaf(String key, String data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void addAttr(String _key, String _data) {
        attr.put(_key, _data);
    }

    public String getAttr(String _key) {
        return attr.get(_key);
    }

}
