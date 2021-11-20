package com.example.lab5_ph16745_phamtronghieu;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLParser {
    //1 tạo tài liệu
    public Document getDocument(String xml) throws IOException, SAXException {
        Document document = null;
        //xử lý
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        InputSource inputSource = new InputSource();
        inputSource.setCharacterStream(new StringReader(xml));
        inputSource.setEncoding("UTF-8");//định dạng utf 8
        document = builder.parse(inputSource);//thực thi
        return document;
    }
    //2 lấy text
    //input : Element , name
    //output : value(text)
    public  String getValue(Element node , String name){
        String kq = "";
        //xử lý
        NodeList nodeList = node.getElementsByTagName(name);//lấy về các phần tử cùng tên
        kq = getTextOfNode(nodeList.item(0));//lấy về text của phần tử đầu tiên
        //kết thúc
        return kq;
    }
    private  String getTextOfNode(Node n){//lấy về text của node
        Node child;
        if(n != null){
            if(n.hasChildNodes()){//nếu có con
                //đưa vào vòng lặp
                for(child = n.getFirstChild() ; child != null ; child = child.getNextSibling()){
                    //kiểm tra type có phải text không
                    if (child.getNodeType() == Node.TEXT_NODE){
                        return child.getNodeValue();//trả về text của node
                    }
                }
            }
        }
        return "";
    }
}
