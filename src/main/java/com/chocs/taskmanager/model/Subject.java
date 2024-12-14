package com.chocs.taskmanager.model;

public class Subject {
	private int id;
	private int idTeacher;
	private String name;
	
	public Subject() {};
	
	public Subject(int id, int idTeacher, String name) {
		this.id = id;
		this.idTeacher = idTeacher;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdTeacher() {
		return idTeacher;
	}
	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Subject [idTeacher=" + id + ", " + idTeacher + ", name=" + name + "]";
	}
}
