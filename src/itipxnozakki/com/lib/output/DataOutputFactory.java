package itipxnozakki.com.lib.output;

import itipxnozakki.com.lib.data.OutputData;

import java.util.List;

public class DataOutputFactory {

    private String url;
    private List<OutputData> outputData;

    public DataOutputFactory(String _url, List<OutputData> outputData) {
        this.url = _url;
        this.outputData = outputData;
    }
    public DataOutputInterface output(String type) {
        DataOutputInterface outputer;
        switch (type) {
            case "excel" :
                outputer = new ExcelOutput(this.url, this.outputData);
            break;
            default:
                outputer = new ExcelOutput(this.url, this.outputData);
        }
           return outputer;
    }
}
