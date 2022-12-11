package com.example.demo3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // должен быть класс с методом мейн с точкой входа, долдна быть аннтоация
//вот эта
public class Demo3Application {

	public static void main(String[] args) {
		//всегда есть эта дефолтная конструкция и список аргументов командной строки
		//генерируется сами
		SpringApplication.run(Demo3Application.class, args);
	}

}
