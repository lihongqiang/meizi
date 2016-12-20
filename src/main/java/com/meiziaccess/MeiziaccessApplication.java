package com.meiziaccess;

import com.meiziaccess.CommandTool.MyHttpUtil;
import com.meiziaccess.model.UploadItem;
import com.meiziaccess.model.UploadItemList;
import com.meiziaccess.model.UploadObject;
import com.meiziaccess.model.UploadRepository;
import com.meiziaccess.upload.UploadTool;
import com.meiziaccess.upload.UploadToolInterface;
import com.meiziaccess.uploadModel.UploadLogRepository;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.*;
import com.meiziaccess.secure.*;


@SpringBootApplication
@RestController
//@EnableScheduling
public class MeiziaccessApplication  {

	private int vendor_type = 1;

	@RequestMapping("/authenticate")
	public Map<String, Object> authenticate(String username, String password){
		Map<String, Object> model = new HashMap<String, Object>();

		JSONObject objData = MyHttpUtil.post(username, password);
		if(objData == null){
			model.put("status", false);
		}else{
			if(objData.getInt("code") ==  200){
				model.put("status", true);
				vendor_type = objData.getInt("data");
				System.out.println(objData.toString());
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

	//一个视频的所有数据在同一个文件夹
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

	/**
	 *
	 * 1 网络台
	 * 2 BTV
	 * 3 南方素材
	 * 4 海外素材
	 * 5 电视剧
     */
	//xml，视频，关键帧在不同文件夹
	@RequestMapping("/data-source-association")
	@ResponseBody
	public Map<String, Object> getItemsAssociation() {
		Map<String, Object> map = new HashMap<>();
		List<UploadItem> list = UploadTool.getUploadItemsAssociation(upload_local_path, vendor_type);
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

		List<UploadItem> list = new ArrayList<>();
		list.add(item);

		UploadToolInterface tool = new UploadTool();
		tool.uploadItemDirs(upload_remote_path, list, uploadLogRepository, vendor_name, vendor_path, uploader_name, trans_path, play_path);
		updateDatabase(list);

		map.put("status", true);
		return map;
	}

	@RequestMapping(value = "/upload-association", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> uploadItemsAssociation(@RequestBody UploadItem item) {

		Map<String, Object> map = new HashMap<>();

		if (item == null){
			map.put("status", false);
			return map;
		}

		List<UploadItem> list = new ArrayList<>();
		list.add(item);

		UploadToolInterface tool = new UploadTool();

		tool.uploadItemDirsAssociation(upload_remote_path, list, uploadLogRepository, vendor_name, vendor_path, uploader_name, trans_path, play_path);
		updateDatabase(list);

		map.put("status", true);
		return map;
	}

	public static void main(String[] args) {
		SpringApplication.run(MeiziaccessApplication.class, args);
	}

}
