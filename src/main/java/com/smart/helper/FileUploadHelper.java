package com.smart.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileUploadHelper {
	//public final String Upload_dir="/home/rohit/Documents/workspace-spring-tool-suite-4-4.9.0.RELEASE/bookapi/src/main/resources/static/image";
	public final String Upload_dir=new ClassPathResource("static\\img").getFile().getAbsolutePath();
	public FileUploadHelper()throws IOException {
		
	}
	public boolean uploadFile(MultipartFile mpfile) {
boolean f=false;
try {
	InputStream is=mpfile.getInputStream();
	byte data[]=new byte[is.available()];
	is.read(data);
	
	//write
	FileOutputStream fos=new FileOutputStream(Upload_dir+File.separator+mpfile.getOriginalFilename());
	System.out.println(Upload_dir+File.separator+mpfile.getOriginalFilename());
	fos.write(data);
	fos.flush();
	fos.close();
	f=true;
}
catch (Exception e ) {
	e.printStackTrace();
}
return f;
}
}
