package com.meiziaccess;

import com.meiziaccess.model.UploadItem;
import com.meiziaccess.model.UploadItemList;
import com.meiziaccess.model.UploadObject;
import com.meiziaccess.model.UploadRepository;
import com.meiziaccess.upload.UploadTool;
import com.meiziaccess.upload.UploadToolInterface;
import com.meiziaccess.uploadModel.UploadLogRepository;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.web.bind.annotation.*;

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

	@Autowired
	UploadRepository uploadRepository;

	@RequestMapping("/data-source")
	@ResponseBody
	public Map<String, Object> getItems() {
		Map<String, Object> map = new HashMap<>();
		List<UploadItem> list = UploadTool.getUploadItems(upload_local_path);

		List<UploadItem> uploadList = uploadRepository.findAll();
		list.removeAll(uploadList);
		map.put("data", list);
		return map;
	}

	@Value("${configure.upload.remote_path}")
	String upload_remote_path;

	@Autowired
	UploadLogRepository uploadLogRepository;

	@Value("${configure.upload.vendor_name}")
	String vendor_name;

	@Value("${configure.local.vendor_path}")
	String vendor_path;

	@Value("${configure.upload.uploader_name}")
	String uploader_name;

	@Value("${configure.upload.trans_path}")
	String trans_path;

	@Value("${configure.upload.play_path}")
	String play_path;

	public boolean updateDatabase(List<UploadItem> list){
		for(UploadItem item : list){
			item.setUpload_time(new Date());
			item.setUpload(true);
			uploadRepository.save(item);
		}
		return true;
	}

	@RequestMapping(value = "/upload", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> uploadItems(@RequestBody UploadItem item) {
		Map<String, Object> map = new HashMap<>();
		if (item == null){
			map.put("status", false);
			return map;
		}
		//使用测试数据
//		List<UploadItem> list = UploadTool.getUploadItems(upload_local_path);

		//测试获取数据
//		System.out.println("upload");
//		System.out.println("title = " + item.getTitle() + '\t' + "path = " + item.getPath());
//		System.out.println("title = " + item.getPrice() + '\t' + "path = " + item.getDuration());

		List<UploadItem> list = new ArrayList<>();
//		UploadItem it = new UploadItem(item.getTitle(), item.getMd5(), item.getPath());
		list.add(item);

		UploadToolInterface tool = new UploadTool();
		tool.uploadItemDirs(upload_remote_path, list, uploadLogRepository, vendor_name, vendor_path, uploader_name, trans_path, play_path);
		updateDatabase(list);

		map.put("status", true);
		return map;
	}

	public static void main(String[] args) {
		SpringApplication.run(MeiziaccessApplication.class, args);
	}

}
