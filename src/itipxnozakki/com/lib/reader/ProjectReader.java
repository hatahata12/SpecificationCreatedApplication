package itipxnozakki.com.lib.reader;

import itipxnozakki.com.lib.converter.Converter;
import itipxnozakki.com.lib.data.Project;
import itipxnozakki.com.lib.data.parsedata.ParseData;
import itipxnozakki.com.lib.parser.DataParser;

import java.util.List;

public class ProjectReader extends Reader  {


    private DataParser parser;

    public ProjectReader(String _url, boolean httpsFlag) {
        super(_url, httpsFlag);
    }

    public void setParser(DataParser paser) {
        this.parser = paser;
    }

    public List<Project> get() {

        StringBuffer sb = super.getStreamDataByHttpOrHttps();
        ParseData data = parser.parse(sb);
        return Converter.convertProjects(data);

    }

}
