package com.bigwiv.snorehunter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.os.Environment;
import android.util.Log;

public class XmlManager {

	public static void XmlFileCreator(RecordBean rb) {
		File xmlfile = new File(Environment.getExternalStorageDirectory()+"/SnoreHunter/"
				+ "record.xml");
		try {
			if (!xmlfile.exists()) {

				xmlfile.createNewFile();
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document document = builder.newDocument();
				document.setXmlVersion("1.0");
				Element root = document.createElement("records"); // 创建根节点
				document.appendChild(root); // 将根节点添加到Document对象中

				Element recordElement = document.createElement("record");
				// recordElement.setAttribute("date", rb.getDate());

				Element dateElement = document.createElement("date");
				dateElement.setTextContent(rb.getDate());
				recordElement.appendChild(dateElement);

				Element timeElement = document.createElement("time");
				timeElement.setTextContent(rb.getTime());
				recordElement.appendChild(timeElement);

				Element durationElement = document.createElement("duration");
				durationElement.setTextContent(rb.getDuration());
				recordElement.appendChild(durationElement);

				Element fileElement = document.createElement("recordfile");
				fileElement.setTextContent(rb.getRecordFile());
				recordElement.appendChild(fileElement);

				Element thresholdElement = document.createElement("threshold");
				thresholdElement.setTextContent(rb.getThreshold());
				recordElement.appendChild(thresholdElement);

				if (rb.getStartPoints().size() > 0) {
					for (int i = 0; i < rb.getStartPoints().size(); i++) {
						Element startElement = document
								.createElement("startpoint");
						startElement.setTextContent(rb.getStartPoints().get(i));
						recordElement.appendChild(startElement);
					}
				} else {
					Element startElement = document.createElement("startpoint");
					startElement.setTextContent("");
					recordElement.appendChild(startElement);
				}
				root.appendChild(recordElement);
				TransformerFactory transFactory = TransformerFactory
						.newInstance(); // 开始把Document映射到文件
				Transformer transFormer = transFactory.newTransformer();
				DOMSource domSource = new DOMSource(document); // 设置输出结果

				FileOutputStream out = new FileOutputStream(xmlfile); // 文件输出流
				StreamResult xmlResult = new StreamResult(out); // 设置输入源
				transFormer.transform(domSource, xmlResult); // 输出xml文件
			} else {
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document document = db.parse(xmlfile);

				Element root = document.getDocumentElement();

				Element recordElement = document.createElement("record");

				Element dateElement = document.createElement("date");
				dateElement.setTextContent(rb.getDate());

				Element timeElement = document.createElement("time");
				timeElement.setTextContent(rb.getTime());

				Element durationElement = document.createElement("duration");
				durationElement.setTextContent(rb.getDuration());

				Element fileElement = document.createElement("recordfile");
				fileElement.setTextContent(rb.getRecordFile());

				Element thresholdElement = document.createElement("threshold");
				thresholdElement.setTextContent(rb.getThreshold());

				recordElement.appendChild(dateElement);
				recordElement.appendChild(timeElement);
				recordElement.appendChild(durationElement);
				recordElement.appendChild(fileElement);
				recordElement.appendChild(thresholdElement);

				if (rb.getStartPoints().size() > 0) {
					for (int i = 0; i < rb.getStartPoints().size(); i++) {
						Element startElement = document
								.createElement("startpoint");
						startElement.setTextContent(rb.getStartPoints().get(i));
						recordElement.appendChild(startElement);
					}
				} else {
					Element startElement = document.createElement("startpoint");
					startElement.setTextContent("");
					recordElement.appendChild(startElement);
				}

				root.appendChild(recordElement);
				TransformerFactory transFactory = TransformerFactory
						.newInstance(); // 开始把Document映射到文件
				Transformer transFormer = transFactory.newTransformer();
				DOMSource domSource = new DOMSource(document); // 设置输出结果

				FileOutputStream out = new FileOutputStream(xmlfile); // 文件输出流
				StreamResult xmlResult = new StreamResult(out); // 设置输入源
				transFormer.transform(domSource, xmlResult); // 输出xml文件
			}

		} catch (Exception e) {
			Log.e("Exception", "bug");
		}

	}

	public static List<RecordBean> getXmlData(File file)
			throws Exception {
		List<RecordBean> list = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(file);
		// 得到根元素，records
		Element root = document.getDocumentElement();
		// 得到一个集合，里面存放在xml文件中所有的record
		NodeList nodeList = root.getElementsByTagName("record");
		//
		if (nodeList == null || nodeList.getLength() == 0) {
			return null;
		}
		// 初始化
		list = new ArrayList<RecordBean>();

		for (int i = 0; i < nodeList.getLength(); i++) {
			
			Element element = (Element) nodeList.item(i);
			RecordBean rb = new RecordBean();
			//得到date
			String date = getAttrText(element, "date");
			rb.setDate(date);
			// 得到time
			String time = getAttrText(element, "time");
			rb.setTime(time);
			// 得到duration
			String duration = getAttrText(element, "duration");
			rb.setDuration(duration);
			//得到filepath
			String recordfile = getAttrText(element, "recordfile");
			rb.setRecordFile(recordfile);
			//得到threshold
			String threshold = getAttrText(element, "threshold");
			rb.setThreshold(threshold);
			//得到startpoints
			NodeList startpointsnode = element.getElementsByTagName("startpoint");
			ArrayList<String> startpoints  = new ArrayList<String>();
			if (startpointsnode.getLength()>0) {
				for (int j = 0; j < startpointsnode.getLength(); j++) {
					startpoints.add(startpointsnode.item(j).getTextContent());
				}
				rb.setStartPoints(startpoints);
			}else {
				//startpoints.add("");
				rb.setStartPoints(startpoints);
			}
			list.add(rb);                                    
		}
		return list;           
	}

	public static String getAttrText(Element element, String name) {
		NodeList nodeList2 = element.getChildNodes();
		Node node = null;
		for (int i = 0; i < nodeList2.getLength(); i++) {
			node = nodeList2.item(i);
			if (node.getNodeName().equals(name)) {
				return node.getTextContent();
			}
		}
		return null;
	}
}
