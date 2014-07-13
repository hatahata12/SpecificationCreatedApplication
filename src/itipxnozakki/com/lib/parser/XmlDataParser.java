package itipxnozakki.com.lib.parser;

import itipxnozakki.com.lib.data.parsedata.Leaf;
import itipxnozakki.com.lib.data.parsedata.ParseData;
import itipxnozakki.com.lib.data.parsedata.Tree;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlDataParser implements DataParser {

    private Tree rootTree = new Tree("root");

    @Override
    public ParseData parse(StringBuffer sb) {

        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
            Document doc = docbuilder.parse(new ByteArrayInputStream(new String(sb).getBytes("utf-8")));
            return this.convertDataParser(doc);
        } catch (ParserConfigurationException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return null;

    }

    private ParseData convertDataParser(Document doc) {
        Element root = doc.getDocumentElement();
        this._convert(root.getChildNodes(), rootTree);
       return new ParseData(rootTree);
    }

    /**
     * 再帰的にTree,Leafオブジェクトを作成する
     *
     * @param nodeList
     * @param tree 親のTreeオブジェクト
     */
   private void _convert(NodeList nodeList, Tree tree) {
       Tree nTree;
       // Treeノードの場合は
       // 親Treeとの親子関係をつなげる
       // 以下の場合に現ノードのTreeオブジェクトを作成する
       // 子が2以上存在する（lengthが1でもそれはテキストノードが子になっている場合がある、つまり階層による子ノードをもたない親である）
       // 子が1かつ子がテキストノードでない場合は、自身は子ノードを持つ親である
       if (nodeList.getLength() > 1 || (nodeList.getLength() == 1
               && nodeList.item(0).getNodeType() != Node.TEXT_NODE)) {
           // 親となるノード名を設定
           nTree = new Tree(nodeList.toString().replaceAll("\\[|\\]", "").split(":")[0]);
           tree.setTree(nTree);
       } else {
           nTree = null;
       }
       // ノードループ
       // Treeの場合は再帰処理を行う(上記処理のよりノード名をセットしている)
       // Leafの場合はLeafオブジェクト生成し、Treeにセットしている
       for (int i = 0; i < nodeList.getLength(); i++) {
           // 子がないならLeafのはず
           if (nodeList.item(i).getChildNodes().getLength() > 0) {
               this._convert(nodeList.item(i).getChildNodes(), nTree);
           } else {
               // 属性が設定されていた場合の処理
               NamedNodeMap attrs = nodeList.item(i).getAttributes();
               if (attrs != null) {
                   Attr att;
                   Leaf leaf = new Leaf(nodeList.item(i).getNodeName(), "");
                   for (int j=0; j<attrs.getLength(); j++) {
                        att = (Attr) attrs.item(j);
                        leaf.addAttr(att.getName(), att.getValue());
                    }
                   // 現在のtreeに設定する
                   // Leafと違い、再帰呼び出しをしないため
                   // nTreeにleafオブジェクトを格納する
                   nTree.setLeaf(leaf);
               } else {
                   // Leafの生成処理
                   // ここにきたノードは#textのため、ノード名は親ノードから取得する
                   Leaf leaf = new Leaf(nodeList.item(i).getParentNode().getNodeName(), nodeList.item(i).getTextContent());
                   tree.setLeaf(leaf);
               }
           }
       }
   }

}
