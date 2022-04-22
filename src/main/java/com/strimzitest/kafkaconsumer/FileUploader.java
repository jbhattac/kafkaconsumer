package com.strimzitest.kafkaconsumer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.YearMonth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.boku.application.financereportmanager.exception.FinanceReportManagerException;

import model.LogData;

@Service
public class FileUploader {
	
	@Autowired
	private  AmazonS3 amazonS3;
	
	private static String BUCKET_NAME = "strimzi-kafka-playground-bucket";
	 
	    public void uploadS3Report(LogData logdata){
	        String fileName = logdata.getApplication() + "-" +logdata.getDateTime();
	        amazonS3.putObject(BUCKET_NAME, fileName,  toFile(logdata.getLogDetails(),fileName));
	    }
	    private File toFile (String content,  String fileName) {
		    File file = new File(fileName);
		    try(FileOutputStream fos = new FileOutputStream(file);
	                BufferedOutputStream bos = new BufferedOutputStream(fos)) {
	            //convert string to byte array
	            byte[] bytes = content.getBytes();
	            //write byte array to file
	            bos.write(bytes);
	            bos.close();
	            fos.close();
		    }
		    catch (IOException e) {
	            e.printStackTrace();
	        }
		    return file;
	    }

}