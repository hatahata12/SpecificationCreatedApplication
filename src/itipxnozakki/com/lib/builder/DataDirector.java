package itipxnozakki.com.lib.builder;

import itipxnozakki.com.lib.data.Ticket;

public class DataDirector {

    private DataBuilderInterface builder;
    private String data;

    public DataDirector (DataBuilderInterface _builder, String _data) {
        this.builder = _builder;
        this.data = _data;
    }

    public Ticket getIssueData() {
        return this.builder.parse(this.data);
    }

}
