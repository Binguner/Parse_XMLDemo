package main;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentCenter {
	
	private static HandleXML handleXML = new HandleXML();
	static ArrayList<StudentBean> studentBeans = new ArrayList<StudentBean>();
	
	public static void main(String[] args) throws Exception {
		String test = null;
		Scanner input = new Scanner(System.in);
		System.out.println("����û�(a)		ɾ���û�(b)		��ѯ�ɼ�(c)");
		System.out.println("�������������");
		test = input.next();
		System.out.println(test);
		switch (test) {
		case "a":
			StudentBean student = HandleXML.addStudent();
			studentBeans.add(student);
			break;
		case "b":
			HandleXML.deleteStudent();
			break;
		case "c":
			HandleXML.SearchGrade();
			break;
		default:
			System.out.println("����ָ������");
			break;
		}
		//System.out.println(studentBeans.get(0).toString());
	}
	
}
