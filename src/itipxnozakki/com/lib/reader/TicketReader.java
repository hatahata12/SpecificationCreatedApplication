package itipxnozakki.com.lib.reader;

import itipxnozakki.com.lib.builder.DataDirector;
import itipxnozakki.com.lib.builder.IssueBasicDataBuilder;
import itipxnozakki.com.lib.data.Ticket;
import itipxnozakki.com.lib.data.parsedata.ParseData;
import itipxnozakki.com.lib.data.parsedata.Tree;
import itipxnozakki.com.lib.parser.DataParser;

public class TicketReader extends Reader {

    private DataParser parser;

    public TicketReader(String _url, boolean httpsFlag) {
        super(_url, httpsFlag);
    }

    public void setParser(DataParser paser) {
        this.parser = paser;
    }

    public Ticket get() {
        StringBuffer sb = super.getStreamDataByHttpOrHttps();
        ParseData data = parser.parse(sb);
        Ticket issuedata = null;
        //data.show();
        StringBuffer dataBuffer = new StringBuffer();
        for (Tree journalsTree : data.getDatas("journals")) {
        	for (Tree tree : journalsTree.getTree()) {
            	dataBuffer.append(data.getDataByTree(tree, "notes") + "\n\n");
        	}
        }

        if (dataBuffer != null) {
        	issuedata = new DataDirector(new IssueBasicDataBuilder(), dataBuffer.toString()).getIssueData();
            String ticketName = data.getData("subject");
            issuedata.setName(ticketName);
        }
        return issuedata;
    }

}
