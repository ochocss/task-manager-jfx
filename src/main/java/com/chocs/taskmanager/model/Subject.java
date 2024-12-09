package com.chocs.taskmanager.model;

public class Subject {
	private int id;
	private Teacher teacher;
	private String name;
	
	public Subject() {};
	
	public Subject(Teacher teacher, String name) {
		super();
		this.teacher = teacher;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [teacher=" + teacher + ", name=" + name + "]";
	}
}