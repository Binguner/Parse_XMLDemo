package main;

import java.awt.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.jar.Attributes.Name;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HandleXML {
	
	static ArrayList<StudentBean> studentList = new ArrayList<StudentBean>();
	public static Scanner input = new Scanner(System.in); 
	
	//增加功能
	public static StudentBean addStudent() throws Exception {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");

		StudentBean student = new StudentBean();
		System.out.println("请输入学生姓名");
		String name = input.next();
		student.setName(name);
		
		System.out.println("请输入学生准考证号");
		String examId = input.next();
		student.setExamId(examId);
		
		System.out.println("请输入学生身份证号");
		String idcard = input.next();
		student.setIdcard(idcard);
		
		System.out.println("请输入学生所在地");
		String location = input.next();
		student.setLocation(location);
		
		System.out.println("请输入学生成绩");
		String grade = input.next();
		student.setGrade(grade);
		
		//拿到节点所有 exam
		NodeList nodeList = document.getElementsByTagName("exama");
		//拿到第一个，唯一的 exam 节点
		Node node = nodeList.item(0);
		//创建学生节点
		Element element = document.createElement("student");
		//为学生节点添加属性 idcard
		Element idcardEkElement = document.createElement("idcard");
		element.setAttribute("idcard", student.getIdcard());
		//为学生节点添加属性 examId
		Element examIdElement = document.createElement("examId");
		element.setAttribute("examId", student.getExamId());
		
		//创建节点 name
		Element nameElement = document.createElement("name");
		nameElement.setTextContent(student.getName());
		element.appendChild(nameElement);
		
		//创建节点location
		Element locationElement = document.createElement("location");
		locationElement.setTextContent(student.getLocation());
		element.appendChild(locationElement);
		
		//创建节点grade
		Element gradeElement = document.createElement("grade");
		gradeElement.setTextContent(student.getGrade());
		element.appendChild(gradeElement);
		
		node.appendChild(element);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/student.xml"));
		studentList.add(student);
		System.out.println("------添加数据成功------");
		return student;
	}
	
	//删除功能
	public static void deleteStudent() throws Exception{
		System.out.println("请输入删除的学生姓名：");
		String name = input.next();
		//读取xml文件
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");
		//读取所有 name 节点
		NodeList nodeList = document.getElementsByTagName("name");
		//拿到指定的 name 节点
		for(int i = 0;i < nodeList.getLength();i++){
			Node node = nodeList.item(i);
			//拿到name节点里的内容
			String nodeText = node.getTextContent();
			//System.out.println(nodeText);
			if(name.equals(nodeText)){
				//拿到student节点
				System.out.println("");
				Node parNode = node.getParentNode();
				parNode.getParentNode().removeChild(parNode);
			}
		}
		System.out.println("------已成功删除学生信息------");
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/student.xml"));
	}
	
	//查询成绩
	public static void SearchGrade() throws Exception{
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");
		System.out.println("请输入查询的学生准考证号：");
		String examId = input.next();
		//拿到所有的student节点
		NodeList nodeList = document.getElementsByTagName("student");
		//Node node = nodeList.item(0);
		//System.err.println(node.getTextContent());
		for(int i=0;i<nodeList.getLength();i++){
			//拿到examId节点里的文字
			Node node1 = nodeList.item(i);
			String examIdText = node1.getAttributes().getNamedItem("examId").getNodeValue();
			//System.out.println(examIdText);
			if(examId.equals(examIdText)){
				//获取"grade" 的节点
				NodeList allNodes = node1.getChildNodes();
				for(int k = 0;k<allNodes.getLength();k++){
					if("grade".equals(allNodes.item(k).getNodeName())){
						System.out.println(allNodes.item(k).getTextContent());
					}
				}
				
				//System.out.println(gradeNode.getTextContent());
				//String gradeTextString = gradeNode.toString();
				//System.out.println(gradeTextString);
			}
		}
		
	}
}
