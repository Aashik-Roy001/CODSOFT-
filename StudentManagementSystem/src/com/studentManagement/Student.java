package com.studentManagement;

public class Student {
	private String name;
	private int rollNumber;
	private String grade;
	private int id;

	public Student(String name, int rollNumber, String grade) {
		this.name = name;
		this.rollNumber = rollNumber;
		this.grade = grade;
	}

	// Getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(int rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Student{" + "name='" + name + '\'' + ", rollNumber=" + rollNumber + ", grade='" + grade + '\'' + '}';
	}
}
