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
	
	//���ӹ���
	public static StudentBean addStudent() throws Exception {
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");

		StudentBean student = new StudentBean();
		System.out.println("������ѧ������");
		String name = input.next();
		student.setName(name);
		
		System.out.println("������ѧ��׼��֤��");
		String examId = input.next();
		student.setExamId(examId);
		
		System.out.println("������ѧ�����֤��");
		String idcard = input.next();
		student.setIdcard(idcard);
		
		System.out.println("������ѧ�����ڵ�");
		String location = input.next();
		student.setLocation(location);
		
		System.out.println("������ѧ���ɼ�");
		String grade = input.next();
		student.setGrade(grade);
		
		//�õ��ڵ����� exam
		NodeList nodeList = document.getElementsByTagName("exama");
		//�õ���һ����Ψһ�� exam �ڵ�
		Node node = nodeList.item(0);
		//����ѧ���ڵ�
		Element element = document.createElement("student");
		//Ϊѧ���ڵ�������� idcard
		Element idcardEkElement = document.createElement("idcard");
		element.setAttribute("idcard", student.getIdcard());
		//Ϊѧ���ڵ�������� examId
		Element examIdElement = document.createElement("examId");
		element.setAttribute("examId", student.getExamId());
		
		//�����ڵ� name
		Element nameElement = document.createElement("name");
		nameElement.setTextContent(student.getName());
		element.appendChild(nameElement);
		
		//�����ڵ�location
		Element locationElement = document.createElement("location");
		locationElement.setTextContent(student.getLocation());
		element.appendChild(locationElement);
		
		//�����ڵ�grade
		Element gradeElement = document.createElement("grade");
		gradeElement.setTextContent(student.getGrade());
		element.appendChild(gradeElement);
		
		node.appendChild(element);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/student.xml"));
		studentList.add(student);
		System.out.println("------������ݳɹ�------");
		return student;
	}
	
	//ɾ������
	public static void deleteStudent() throws Exception{
		System.out.println("������ɾ����ѧ��������");
		String name = input.next();
		//��ȡxml�ļ�
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");
		//��ȡ���� name �ڵ�
		NodeList nodeList = document.getElementsByTagName("name");
		//�õ�ָ���� name �ڵ�
		for(int i = 0;i < nodeList.getLength();i++){
			Node node = nodeList.item(i);
			//�õ�name�ڵ��������
			String nodeText = node.getTextContent();
			//System.out.println(nodeText);
			if(name.equals(nodeText)){
				//�õ�student�ڵ�
				System.out.println("");
				Node parNode = node.getParentNode();
				parNode.getParentNode().removeChild(parNode);
			}
		}
		System.out.println("------�ѳɹ�ɾ��ѧ����Ϣ------");
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.transform(new DOMSource(document), new StreamResult("src/student.xml"));
	}
	
	//��ѯ�ɼ�
	public static void SearchGrade() throws Exception{
		DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document document = documentBuilder.parse("src/student.xml");
		System.out.println("�������ѯ��ѧ��׼��֤�ţ�");
		String examId = input.next();
		//�õ����е�student�ڵ�
		NodeList nodeList = document.getElementsByTagName("student");
		//Node node = nodeList.item(0);
		//System.err.println(node.getTextContent());
		for(int i=0;i<nodeList.getLength();i++){
			//�õ�examId�ڵ��������
			Node node1 = nodeList.item(i);
			String examIdText = node1.getAttributes().getNamedItem("examId").getNodeValue();
			//System.out.println(examIdText);
			if(examId.equals(examIdText)){
				//��ȡ"grade" �Ľڵ�
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
