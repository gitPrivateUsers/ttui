package org.pussinboots.morning.cms.controller.product;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.pussinboots.morning.cms.common.result.CmsResult;
import org.pussinboots.morning.cms.common.security.AuthorizingUser;
import org.pussinboots.morning.cms.common.util.SingletonLoginUtils;
import org.pussinboots.morning.common.base.BaseController;
import org.pussinboots.morning.common.constant.CommonReturnCode;
import org.pussinboots.morning.common.enums.StatusEnum;
import org.pussinboots.morning.product.entity.ProductImage;
import org.pussinboots.morning.product.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
		//新文件名
		String df = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		Random r = new Random();
		for (int i = 0;i<3;i++){
			df += r.nextInt(10);
		}
		String newFileName = df + suffix;
		//上传文件的路径
		String path = request.getServletContext().getRealPath("uploads\\photo\\");
		//这是商品的id prefix
		String prefix = oldFileName.replace(suffix,"");
		String dateFile = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		path = path + File.separator + dateFile;
		path = path + File.separator + prefix;
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
			json.put("success","upload/photo/"+suffix+ File.separator +newFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if(authorizingUser != null){
			ProductImage pi=new ProductImage();
			pi.setStatus(StatusEnum.INVALID.getStatus());
			pi.setPicImg(String.valueOf(json.get("success")));
			pi.setProductId(Long.valueOf(prefix));
			pi.setSort(0);
		 productImageService.insertProductImage(pi,authorizingUser.getUserName());
			return json;
		}
			return null;
	}




	@ApiOperation(value = "上传图片页面",notes = "上传图片页面")
	@GetMapping(value = "/addImg/uploadPic/page")
	public String getPicUploadPage(Model model){
		return "/modules/product/upload/product_picupload_page";
	}







	@ApiOperation(value = "创建保存商品图片信息", notes = "创建保存商品图片信息")
//	@RequiresPermissions("product:detail:create")
	@PostMapping(value = "/save/addImg")
	@ResponseBody
	public Object saveInsertProductImage(ProductImage productImage, @RequestParam(value = "status", defaultValue = "0") Integer status ) {
		AuthorizingUser authorizingUser = SingletonLoginUtils.getUser();
		if(authorizingUser != null){
			productImage.setStatus(status);
			Integer count = productImageService.insertProductImage(productImage,authorizingUser.getUserName());
			return new CmsResult(CommonReturnCode.SUCCESS,count);
		}else {
			return new CmsResult(CommonReturnCode.UNAUTHORIZED);
		}
	}

}


