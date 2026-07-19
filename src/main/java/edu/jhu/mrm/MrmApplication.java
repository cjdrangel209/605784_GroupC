package edu.jhu.mrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * File: MrmApplication.java
 * This file contains the application start of the Maintenance Request Manager
 * Service Application
 * Author: Cory Drangel and Matthew Kim
 *
 */

@SpringBootApplication
public class MrmApplication 
{
    public static void main( String[] args )
    {
        System.out.println( "Beginning MRM Application..." );
        SpringApplication.run(MrmApplication.class, args);
    }
}
