package com.konan.controller;

import com.alibaba.fastjson.JSONObject;
import com.konan.pojo.ProductInfo;
import com.konan.pojo.vo.ProductVo;
import com.konan.service.ProductInfoServiceImpl;
import com.konan.utils.FileNameUtil;
import com.konan.utils.constant;
import com.konan.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductInfoControl {

    private String saveFileName="";

    @Autowired
    private ProductInfoService productInfoService;

    //初始加载会分页后显示第一页
    @RequestMapping("/split.do")
    public  String split(HttpServletRequest request){
        List<ProductInfo> page=null;
        if (request.getSession().getAttribute("Vo")!=null) {
            ProductVo vo = (ProductVo) request.getSession().getAttribute("Vo");
            page = productInfoService.getProductInfoBySort(vo.getPage(), constant.pageSizeNum);
            request.getSession().removeAttribute("Vo");
        }else {
            page = productInfoService.getProductInfoBySort(1, constant.pageSizeNum);
        }
        request.setAttribute("list",page);
        request.setAttribute("info", ProductInfoServiceImpl.pageInfo);
        return "product";
    }

    //查询
    @ResponseBody
    @RequestMapping("/condition.do")
    public void condition(ProductVo productVo,HttpServletRequest request){
        System.out.println(productVo);
        List<ProductInfo> condition = productInfoService.condition(productVo, 1, constant.pageSizeNum);

        request.getSession().setAttribute("list",condition);
        request.getSession().setAttribute("info",ProductInfoServiceImpl.pageInfo);
    }

    //利用ajax翻页
    @ResponseBody
    @RequestMapping("/ajaxSplit.do")
    public void ajaxSplit(ProductVo productVo,HttpSession session) {
        Integer page = productVo.getPage();
        if (page==0) {
            productVo.setPage(1);
        }
        List<ProductInfo> condition = productInfoService.condition(productVo, productVo.getPage(), constant.pageSizeNum);
        session.setAttribute("list",condition);
        session.setAttribute("info", ProductInfoServiceImpl.pageInfo);
    }

    //添加商品
    @RequestMapping("/save.do")
    public String save(@RequestParam("imag") CommonsMultipartFile imag,HttpServletRequest request, ProductInfo productInfo) {

        //获取文件名 : file.getOri下ginalFilename();
        String uploadFileName = imag.getOriginalFilename();

        //如果文件名为空，直接回到首页！
        if ("".equals(uploadFileName)){
            return "addproduct";
        }
        //截取随机uuid的前五位拼接上日期再加上文件格式后缀
        SimpleDateFormat simpleDateFormat0 = new SimpleDateFormat("yyyyMMdd");
        Date date0 = new Date();
        String originalFilename = imag.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String s = originalFilename.substring(i);
        UUID uuid = UUID.randomUUID();
        String uuidpath=uuid.toString().replace("-","").substring(0,5);
        uploadFileName=uuidpath+simpleDateFormat0.format(date0)+s;
        System.out.println("上传文件名 : "+uploadFileName);

        //上传路径保存设置
        //String path = request.getServletContext().getRealPath("/imagedetail");
        String path = "/Users/ppsn/Documents/mimissm/MiMi-SSM/src/main/webapp/image_big";
        //如果路径不存在，创建一个
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        System.out.println("上传文件保存地址："+realPath);

        //通过CommonsMultipartFile的方法直接写入图片
        try {
            imag.transferTo(new File(realPath +"/"+ uploadFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        productInfo.setpImage(uploadFileName);
        productInfo.setpDate(simpleDateFormat.format(date));

        System.out.println(productInfo.getpName());
        System.out.println(productInfo.getpContent());
        System.out.println(productInfo.getpPrice());
        System.out.println(productInfo.getpImage());
        System.out.println(productInfo.getpNumber());
        System.out.println(productInfo.getTypeId());
        System.out.println(productInfo.getpDate());

        int num = -1;
        try {
            num = productInfoService.save(productInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("添加失败"+num);
        }

        if(num > 0) {

            // 新增商品成功后，销毁当前查询文本框的数据
            request.getSession().removeAttribute("vo");
            request.setAttribute("msg", "增加成功！");
        } else {
            request.setAttribute("msg", "增加失败！");
        }

        // 清空saveFileName的内容
        saveFileName = "";

        return "forward:/product/split.do";
    }

    //更新商品(传递过程)
    @RequestMapping("/one.do")
    public String one(Model model,int pid,ProductVo productVo,HttpSession session) {
        session.setAttribute("Vo",productVo);    //将多条件及页码放入session中，更新处理结果后分页时读取条件个页码进行处理
        ProductInfo productInfo = productInfoService.queryByID(pid);
        model.addAttribute("product",productInfo);
        return "update";
    }

    //更新商品
    @RequestMapping("update.do")
    public String update(ProductInfo productInfo,HttpServletRequest request) {
        if (!saveFileName.equals("")) {
            productInfo.setpImage(saveFileName);
        }
        int i = productInfoService.updateByID(productInfo);
        if (i>0){
            request.setAttribute("msg","编辑成功");
        }else {
            request.setAttribute("msg","编辑失败");
        }
        saveFileName="";
        return "forward:/product/split.do";
    }


    // 异步ajax文件上传处理,处理图片回显问题
    @ResponseBody
    @RequestMapping(value = "/ajaxImg.do")
    public Object ajaxImg(HttpServletRequest request, MultipartFile pimage) {

        // 提取生成文件名(UUID+文件名后缀)
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());

        // 设置项目中图片存储的路径,于Tomcat的server.xml中配置了映射的虚拟路径
        String path="/Users/ppsn/Documents/mimissm/MiMi-SSM/src/main/webapp/image_big";
        // 转存
        try {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回给客户端JSON对象，封装图片的路径，为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);

        return object.toString();
    }


    //删除商品及服务器中的图片
    @RequestMapping("/delete.do")
    public String delete(int pid,String pImage,HttpServletRequest request,ProductVo productVo){
        String path="/Users/ppsn/Documents/mimissm/MiMi-SSM/src/main/webapp/image_big/"+pImage;
        int i = -1;
        try {
            i=productInfoService.deleteByID(pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i > 0) {
            request.setAttribute("massage", "删除成功!");
            File file = new File(path);
            if (file.isFile()&&file.exists()) {
                file.delete();
            }
        } else {
            request.setAttribute("massage", "删除失败!");
        }
        request.getSession().setAttribute("delVo",productVo);
        return "forward:/product/deleteAjaxSplit.do";
    }

    //删除商品后停留在当前页
    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit.do", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request){
        List<ProductInfo> page=null;
        if (request.getSession().getAttribute("delVo") != null) {
            ProductVo delVo = (ProductVo) request.getSession().getAttribute("delVo");
            page = productInfoService.getProductInfoBySort(delVo.getPage(), constant.pageSizeNum);
        } else {
            page = productInfoService.getProductInfoBySort(1, constant.pageSizeNum);
        }
        request.getSession().setAttribute("list",page);
        request.getSession().setAttribute("info", ProductInfoServiceImpl.pageInfo);

        request.getSession().removeAttribute("delVo");
        return request.getAttribute("massage");
    }

    //批量删除商品
    @RequestMapping("/deleteBatch.do")
    public String deleteBatch(String pids,HttpServletRequest request,ProductVo productVo) {
        String[] ids=pids.split(",");
        int i = -1;
        try {
            i=productInfoService.deleteBatch(ids);
            if (i > 0) {
                request.setAttribute("massage", "批量删除成功!");
            } else {
                request.setAttribute("massage","删除失败!");
            }
        } catch (Exception e) {
            request.setAttribute("massage","商品不可删除!");
        }
        request.getSession().setAttribute("delVo",productVo);
        return "forward:/product/deleteAjaxSplit.do";
    }

}
