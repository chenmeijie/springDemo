package com.great.springdemo.controller;

import com.great.springdemo.bean.DataBean;
import com.great.springdemo.bean.Menu;
import com.great.springdemo.entity.Document;
import com.great.springdemo.entity.Log;
import com.great.springdemo.entity.Role;
import com.great.springdemo.entity.UserIn;
import com.great.springdemo.service.UserService;
import com.great.springdemo.utils.MD5Utils;
import com.great.springdemo.utils.ResponseUtils;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/UserController")
public class UserController
{
	@Autowired
	private UserService userService;
	@RequestMapping("/userQuery.action")
	public String userQuery(){
		return "back/userQuery";
	}

	@RequestMapping("/query/{url}")
	public String roleQuery(@PathVariable (value = "url") String path){
		return "back/" + path;
	}
	@RequestMapping("/addUser")
	@ResponseBody
	@Log( operationType = "添加操作",operationName = "addUser")
	public String  addUser(String name,String pwd,String sex){
		UserIn user = new UserIn();
		pwd = MD5Utils.md5(pwd);
			user.setuName(name);
			user.setuPwd(pwd);
			if ("男".equals(sex)){
				user.setuSex("0");
			}else {
				user.setuSex("1");
			}
			user.setPid(1);
			System.out.println(name+pwd+sex);
			boolean b = userService.addUser(user);
			if (b){
				return "1";
			}else {
				return "0";
			}
	}
	@RequestMapping("/addDocument")
	@ResponseBody
	public String  addDocument(Document document){
		document.setPid(3);
		boolean b = userService.addDocument(document);
		if (b){
			return "1";
		}
		return "0";
	}

	@RequestMapping("/delUser")
	@ResponseBody
	public String delUser(String id){
		System.out.println(id);
		boolean b = userService.delUser(Integer.parseInt(id));
		if (b){
			return "1";
		}else {
			return "0";
		}
	}

	@RequestMapping("/delDocument")
	@ResponseBody
	public String delDocument(String id){
		System.out.println(id);
		boolean b = userService.delDocument(Integer.parseInt(id));
		if (b){
			return "1";
		}else {
			return "0";
		}
	}


	@RequestMapping("/updateDocument")
	@ResponseBody
	public String updateDocument(String id,String name){
		System.out.println(id+name);
		Document document= new Document();
		document.setDid(Integer.valueOf(id));
		document.setdName(name);
		boolean b = userService.updateDocument(document);
		if (b){
			return "1";
		}else {
			return "0";
		}
	}

	@RequestMapping("/updateUser")
	@ResponseBody
	public String updateUser(String id,String name){
		System.out.println(id+name);
		UserIn userIn = new UserIn();
		userIn.setUserId(Integer.valueOf(id));
		userIn.setuName(name);
		boolean b = userService.updateUser(userIn);
		if (b){
			return "1";
		}else {
			return "0";
		}
	}

	@RequestMapping(value = "/test")
	@ResponseBody
	public ModelAndView testValidation(@Validated UserIn userIn,BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		StringBuilder sb = new StringBuilder();
		if (bindingResult!=null && bindingResult.hasErrors()){
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors)
			{
				System.out.println(error);
				sb.append(error.getDefaultMessage()).append(",");
			}
		}
		modelAndView.setViewName("back/index");
		modelAndView.addObject("msg",sb.toString());
		return modelAndView;
	}

	@RequestMapping(value = "/login.action")
	@ResponseBody
	public ModelAndView login(HttpServletRequest request,  @RequestParam( value = "title",required = false) String name ,  @RequestParam( value = "password",required = false) String password){
		System.out.println(name+":"+password);
		password = MD5Utils.md5(password);
		String[] arr = new String[]{name,password};
		boolean login = userService.login(arr);
		ModelAndView modelAndView = new ModelAndView();
		if (login){
			request.getSession().setAttribute("loginUser",name);
			List<Menu> list = userService.findMenu(name);
			Map<String,List<Menu>> map = new HashMap<>();
			if (null != list){
				for (int i = 0; i < list.size(); i++)
				{
					Menu menu = list.get(i);
					if (map.containsKey(menu.getParentName())){
						List<Menu> list1 = map.get(menu.getParentName());
						list1.add(menu);
					}else {
						List<Menu> list1 = new ArrayList<>();
						list1.add(menu);
						map.put(menu.getParentName(),list1);
					}
				}
			}
			modelAndView.addObject("map",map);
			modelAndView.setViewName("back/mainFrame");
		}else {
			modelAndView.setViewName("back/index");
		}
		return modelAndView;

	}
	@RequestMapping("/index.action")
	public ModelAndView index(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("back/index");
		return modelAndView;
	}

	@RequestMapping("/findDocument")
	@ResponseBody
	public DataBean findDocument(Document document){
		document.setPage((document.getPage()-1)*document.getLimit());
		DataBean<Document> dataBean = new DataBean<>();
		List<Document> documentList= userService.findDocument(document);
		Integer count = userService.countDocument();
		dataBean.setCount(count);
		dataBean.setData(documentList);
		return dataBean;
	}

	@RequestMapping("/findRole")
	@ResponseBody
	public DataBean findRole(Role role){
		role.setPage((role.getPage()-1)*role.getLimit());
		DataBean<Role> dataBean = new DataBean<>();
		List<Role> roleList = userService.findRole(role);
		Integer count = userService.countRole(role);
		dataBean.setCount(count);
		dataBean.setData(roleList);
		return dataBean;
	}
	@RequestMapping("/findUser.action")
	@ResponseBody
	public DataBean findUser(UserIn userIn){
//		ModelAndView modelAndView = new ModelAndView();
//		System.out.println("page:"+page+"limit:"+limit);
//		System.out.println("uName:"+uName);
//		UserIn userIn = new UserIn();
		userIn.setPage((userIn.getPage()-1)*userIn.getLimit());
//		userIn.setLimit(Integer.valueOf(limit));
//		userIn.setuName(uName);
		DataBean<UserIn> dataBean = new DataBean<>();
		List<UserIn> userList = userService.findUser(userIn);
		Integer count = userService.countUser(userIn);
		dataBean.setCount(count);
		dataBean.setData(userList);
		return dataBean;
	}

	/**
	 * 文件下载
	 */
	@RequestMapping("downloadFile")
	public void downFile(HttpServletResponse response){
		//设置字符名以及文件名、后缀
		response.setCharacterEncoding("UTF-8");
		response.setContentType("txt");
		String fileName = "Readme.txt";
		try {
			response.setHeader("Content-Disposition", "attachment;fileName="+new String(fileName.getBytes(), "iso8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String path = "D:\\fileUpload\\Readme.txt";
		File file = new File(path);
		if (file.exists()){
			System.out.println("文件有找到");
			try {
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				byte[] bytes = new byte[1024];
				int len = 0;
				while((len = bis.read(bytes))!= -1){
					bos.write(bytes,0,len);
					bos.flush();
				}
				bos.close();
				bis.close();
				System.out.println("写出完毕");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			System.out.println("文件没找到");
		}

	}

	@RequestMapping("/updateMenu")
	@ResponseBody
	public String updateMenu(String str,String rid){
		List<Integer> list = userService.queryMenuByRid(Integer.valueOf(rid));
		Map<Integer,Integer> map = new HashMap<>();
		JSONArray array = JSONArray.fromObject(str);
		for (int i = 0; i < array.size(); i++)
		{
			int id = array.getJSONObject(i).getInt("id");
			map.put(id,id);
			JSONArray children = array.getJSONObject(i).getJSONArray("children");
			for (int i1 = 0; i1 < children.size(); i1++)
			{
				int id1 = children.getJSONObject(i1).getInt("id");
				map.put(id1,id1);
			}
		}
		List<Integer> list1 = new ArrayList<>();
		for (int i = 0; i < list.size(); i++)
		{
			if (map.containsKey(list.get(i))){
				map.remove(list.get(i));
			}
		}
		for (Map.Entry<Integer,Integer> entry:map.entrySet()){
			list1.add(entry.getValue());
		}
		if (list1.size() > 0){
			boolean b = userService.distributeMenu(list1,Integer.valueOf(rid));
			if (b){
				return "1";
			}else {
				return "0";
			}
		}
		return "0";
	}


	private Object fun(List<Menu> menuList, List<HashMap<String, Object>> result)
	{
		for (Menu m : menuList)
		{
			HashMap<String, Object> map = new HashMap<>();
			map.put("id", m.getMenuId());
			map.put("title", m.getMenuName());
			map.put("spread", true);      //设置是否展开
			List<HashMap<String, Object>> result1 = new ArrayList<>();
			List<Menu> children = m.getChildren();    //下级菜单
			if (m.getChildren().size() > 0) {
				map.put("children", fun(children, result1));
			}
			result.add(map);
		}
		return result;

	}


	@RequestMapping("/findMenuByRid")
	@ResponseBody
	public Object findMenuByRid(String uName){
		Integer rid = userService.queryRidByName(uName);
		List<Menu> menuList = userService.findMenuByRid(rid);
		Map<Integer,Menu > map = new HashMap<>();
		for (Menu menu : menuList)
		{
			if (menu.getMenuPid() == 0){
				map.put(menu.getMenuId(),menu);
			}else {
				Menu menu1 = map.get(menu.getMenuPid());
				menu1.getChildren().add(menu);
			}
		}
		List<Menu> menuPlist = new ArrayList<>();
		for (Map.Entry<Integer,Menu> entry:map.entrySet()){
			menuPlist.add(entry.getValue());
		}
		List<HashMap<String, Object>> result = new ArrayList<>();
		return fun(menuPlist,result);
	}

	@RequestMapping("/upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request, @RequestParam("file") MultipartFile file)  {
		Map map = new HashMap<String,Object>();
		//如果文件不为空，写入上传路径
		try
		{
			if (!file.isEmpty()) {
				//上传文件名
				String filename = file.getOriginalFilename();
				File filepath = new File("D:fileUpload/");
				//判断路径是否存在，如果不存在就创建一个
				if (!filepath.exists()) {
					filepath.mkdirs();
				}
				System.out.println(filepath);
				Path path = Paths.get(filepath.toString().substring(0,2), filepath.toString().substring(2),filename);
				//将上传文件保存到一个目标文件当中\
				file.transferTo(path);
				map.put("msg","ok");
				map.put("code",200);
			}
		}catch (Exception e){
			map.put("msg","error");
			map.put("code",0);
			e.printStackTrace();
		}
		return map;
	}


	@Log( operationType = "增加操作",operationName = "增加用户")
	public void addUser(String userName,String password){
		System.out.println("UserController被调用了~~~~~~~~~~~~~~~~···");
	}
}
