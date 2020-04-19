package com.amsidh.mvc.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class MainApp {

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException, FileNotFoundException, IOException, ClassNotFoundException, CloneNotSupportedException {
		// 1 way to create the object using new Operator
		Demo d = new Demo();
		MyClass myClass = new MyClass("Hello World");
		myClass.displayMessage();

		// 2 way is Load your class in Class object and call newInstance on that
		// but this requires default constructor public constructor
		Class classDemo = Demo.class;
		Demo newInstance = (Demo) classDemo.newInstance();

		// 3 way loading class and getting constructor and calling instance on
		// that constructor.
		Class c = MyClass.class;
		Constructor constructor = c.getConstructor(new Class[] { String.class });
		MyClass myClass2 = (MyClass) constructor.newInstance(new Object[] { "Hello Amsidh" });
		myClass2.displayMessage();
		
		
		// 4th way
		    //store the state of the Demo class in a file
		File file = new File("demo.txt");
		file.createNewFile();
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(file));
		out.writeObject(new MyClass("Serializing MyClass"));
		
		ObjectInputStream in=new ObjectInputStream(new FileInputStream(file));
		MyClass MyClass3=(MyClass) in.readObject();  //In Deserilization process constructor will not called
		MyClass3.displayMessage();
		
		//5th way by implementing Cloneable interface and overriding clone method
		MyClass clone = (MyClass) myClass2.clone();
		clone.displayMessage();
		Demo clone2 = (Demo) d.clone(); // In clone construtor is not called
		
		//6th way
		Constructor constructor2 = Class.forName("com.amsidh.mvc.main.MyClass").getConstructor(new Class[]{String.class});
		MyClass obj=(MyClass) constructor2.newInstance(new Object[]{"Loading class using forName"});
		obj.displayMessage();
	}

}

class Demo implements Serializable,Cloneable{

	public Demo() {
		System.out.println("Constructor of Demo class is called ");
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

class MyClass implements Serializable,Cloneable{
	String message;

	public MyClass(String message) {
		this.message = message;
	}

	public void displayMessage() {
		System.out.println(message);
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}