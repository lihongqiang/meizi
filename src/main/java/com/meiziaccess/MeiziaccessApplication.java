package com.meiziaccess;

import com.meiziaccess.model.UploadItem;
import com.meiziaccess.upload.UploadTool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Null;
import java.util.*;


@SpringBootApplication
@RestController
//@EnableScheduling
public class MeiziaccessApplication  {

	@RequestMapping("/authenticate")
	public Map<String, Object> authenticate(String username, String password){
		Map<String, Object> model = new HashMap<String, Object>();
		if(username == null || password == null){
			model.put("status", false);
		}else{
			if(username.equals("lhq") && password.equals("lhq")){
				model.put("status", true);
			}else{
				model.put("status", false);
			}
		}
		return model;
	}

	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

	@Value("${configure.upload.local_path}")
	private String upload_local_path;

	@RequestMapping("/data-source")
	@ResponseBody
	public Map<String, Object> getItems() {
		Map<String, Object> map = new HashMap<>();
		List<UploadItem> list = UploadTool.getUploadItems(upload_local_path);
//		List<UploadItem> list = UploadTool.getUploadItems("E:\\program\\媒资\\data\\低码");
		map.put("data", list);
		return map;
	}

	public static void main(String[] args) {
		SpringApplication.run(MeiziaccessApplication.class, args);
	}

}
