package itipxnozakki.com.lib.reader;

import itipxnozakki.com.lib.converter.Converter;
import itipxnozakki.com.lib.data.Issue;
import itipxnozakki.com.lib.data.parsedata.ParseData;
import itipxnozakki.com.lib.parser.DataParser;

import java.util.List;

public class IssueReader extends Reader  {


    private DataParser parser;

    public IssueReader(String _url, boolean httpsFlag) {
        super(_url, httpsFlag);
    }

    public void setParser(DataParser paser) {
        this.parser = paser;
    }

    public List<Issue> get() {
        StringBuffer sb = super.getStreamDataByHttpOrHttps();
        ParseData data = parser.parse(sb);
        return Converter.convertIssues(data);
    }

}
