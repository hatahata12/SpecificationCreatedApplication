package itipxnozakki.com.lib.builder;

import itipxnozakki.com.lib.data.Journal;
import itipxnozakki.com.lib.data.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class IssueBasicDataBuilder implements DataBuilderInterface {

    @Override
    public Ticket parse(String data) {

        String[] splitData = data.split("\n");

        boolean tableFlag = false;
        List<String> journalData = new ArrayList<String>();
        int pos = 0;
        int lastPos = 0;
        int tablePosCount = 1;
        String[] tmpArray;
        List<String[]> tmpList = new ArrayList<String[]>();
        Map<String, List<String[]>> tableMap = new HashMap<String, List<String[]>>();

        int max = splitData.length;
        for (int i = 0; i < max; i++){

        	// |から始まる行の場合はテーブル記述の解析
            if (splitData[i].matches("\\|.*")) {
                pos = splitData[i].indexOf('|');
                lastPos = splitData[i].lastIndexOf('|');
                // |の始まりから終わりまでを切り出しsplitの対象とする
                String slaceString = splitData[i].substring(pos, lastPos);
                slaceString = StringUtils.stripStart(slaceString, "|");
                tmpArray = slaceString.split("\\|");
                tmpList.add(tmpArray);
                // テーブル記述が終わるまでフラグをオンにする
                // このフラグがオン以降はテーブル解析処理になる
                tableFlag = true;
                // テーブル記述行は空にする
                splitData[i] = "";
            } else {
            	// |が存在しない行で、テーブルフラグがONの場合は
            	// 直前までテーブル記述だったということ。
            	// テーブル記述の解析結果を格納する
                if (tableFlag) {
                    tableMap.put("table_" + String.valueOf(tablePosCount), tmpList);
                    tableFlag = false;
                    journalData.add(splitData[i] + "table_" + String.valueOf(tablePosCount));
                    tmpList = new ArrayList<String[]>();
                    tablePosCount++;
                }
            }

            // 通常のテキスト行の場合
            if (!tableFlag) {
                journalData.add(splitData[i]);
            }

            // 次の行が存在しない場合
            if ((i + 1) == splitData.length) {
            	// 直前がテーブル記述だった場合
                if (tableFlag) {
                    tableMap.put("table_" + String.valueOf(tablePosCount), tmpList);
                    tableFlag = false;
                    journalData.add(splitData[i] + "table_" + String.valueOf(tablePosCount));
                    tmpList = new ArrayList<String[]>();
                    tablePosCount++;
                }
            }

        }
        Journal journal = new Journal();
        journal.setData(journalData);
        return new Ticket(journal, tableMap);
    }

}
