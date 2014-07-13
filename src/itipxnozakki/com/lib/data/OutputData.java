package itipxnozakki.com.lib.data;

import java.util.ArrayList;
import java.util.List;

public class OutputData {
    private List<List<Ticket>> dataList = new ArrayList<List<Ticket>>();

    public void add(List<Ticket> data) {
        dataList.add(data);
    }
    public List<List<Ticket>>  get() {
        return dataList;
    }

}
