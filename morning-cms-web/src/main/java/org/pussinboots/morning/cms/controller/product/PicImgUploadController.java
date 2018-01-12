package org.pussinboots.morning.cms.controller.product;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.product.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping(value = "/product/upload")
@Api(value = "商品管理", description = "商品管理")
public class PicImgUploadController extends BaseController {
	@Autowired
	private IProductImageService productImageService;


	@ApiOperation(value = "上传商品图片", notes = "上传商品图片")
//    @RequiresPermissions("product:detail:create")
	@RequestMapping(value = "/addImg/uploadPic/uploads")
	@ResponseBody
	public Map<String,Object> uploadPhoto(HttpServletRequest request, HttpServletResponse response, @RequestParam("myFile")MultipartFile myFile) {
		Map<String, Object> json = new HashMap<String, Object>();

		//原文件名
		String oldFileName = myFile.getOriginalFilename();
		//文件后缀
		String suffix = oldFileName.substring(oldFileName.lastIndexOf(".")).toLowerCase();
	//	获取文件前缀
		String prefix = oldFileName.replace(suffix,"");
		//新文件名
		String df = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Random r = new Random();
		for (int i = 0;i<3;i++){
			df += r.nextInt(10);
		}
		String newFileName = df + suffix;
		//上传文件的路径
		String path = request.getServletContext().getRealPath("uploads\\photo\\");
		String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
		path = path + File.separator + dateFile;
		File file = new File(path);
        logger.info("文件的上传路径是：{}", path);
		//不存在则创建
		if (!file.exists()) {
			file.mkdirs();
		}
		//目标文件
		File dest = new File(path + File.separator + newFileName);
		//上传
		try {
			myFile.transferTo(dest);
			json.put("success","upload/photo/"+newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json ;
	}


}


