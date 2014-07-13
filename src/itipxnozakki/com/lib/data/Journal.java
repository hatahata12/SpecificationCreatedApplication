package itipxnozakki.com.lib.data;

import java.util.List;

public class Journal {

    private List<String> data;

    public List<String> getData() {
        return data;
    }
    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }


}
