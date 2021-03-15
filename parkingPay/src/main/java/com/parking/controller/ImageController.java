package com.parking.controller;

import com.parking.domain.Image;
import com.parking.service.ImageService;
import com.parking.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/images")
public class ImageController {

    private static final Logger logger =  LoggerFactory.getLogger(ImageController.class);
    @Autowired
    private ImageService imageService;

    @ResponseBody
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Image> generate() throws Exception {
        //logger.info("进入获取图片日志服务器");
        return imageService.getAllImages();
    }

    /**
     * 重定向 redirect
     * ==============================
     * redirect 目标有三种构建方式
     * 1. 使用 redirect: 前缀url方式构建目标url
     * 2. 使用 RedirectView 类型指定目标, 推荐使用这个,
     * 3. 使用 ModelAndView 类型指定目标, ModelAndView 视图名默认是forward, 所以对于redirect, 需要加上 redirect: 前缀
     * 传参和取参方式:
     * 1. 传参: 以字符串的形式构建目标url, 可以使用 query variable的格式拼url. 取参: @RequestParam()来fetch
     * 2. 传参: redirectAttributes.addAttribute() 加的attr. 取参: @RequestParam()来fetch
     * 3. 传参: redirectAttributes.addFlashAttribute() 加的attr. 取参: @ModelAttribute()来fetch
     *
     * Flash attribute的特点:
     * 1. addFlashAttribute() 可以是任意类型的数据(不局限在String等基本类型), addAttribute()只能加基本类型的参数.
     * 2. addFlashAttribute() 加的 attr, 不会出现在url 地址栏上.
     * 3. addFlashAttribute() 加的 attr, 一旦fetch后, 就会自动清空, 非常适合 form 提交后 feedback Message
     * @param mode
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/alls", method = RequestMethod.GET)
    public ModelAndView error(ModelAndView mode, HttpServletRequest request, HttpServletResponse response) throws Exception {
        mode.setViewName("aliPayOrder");
        mode.addObject("orde","异常");
        System.out.println(FileUtil.readResourceFile("aliPayOrder.html"));
        //request.getRequestDispatcher("/error.html").forward(request, response);
       return mode;
    }
    @RequestMapping("/redirect1")
    public String updateOrAddProject() {
        return "redirect:要访问的相对网址或绝对网址?参数名="+"参数值";
    }

    /*
     * forward 示例: 以字符串的形式构建目标url, url 需要加上 forward: 前缀
     * */
    @RequestMapping("/forwardTest1")
    public String forwardTest1() {
        return "forward:/forwardTarget?param1=v1&param2=v2";
    }

    /*
     * forward 示例: 使用 ModelAndView() 设置转发的目标url
     * */
    @RequestMapping("/forwardTest2")
    public ModelAndView forwardTest2() {
        ModelAndView mav=new ModelAndView("/forwardTarget"); // 绝对路径OK
        //ModelAndView mav=new ModelAndView("forwardTarget"); // 相对路径也OK
        mav.addObject("param1", "value1");
        mav.addObject("param2", "value2");
        return mav ;
    }

    @RequestMapping("/forwardTarget")
    public String forwardTargetView(Model model, @RequestParam("param1") String param1,
                                    @RequestParam("param2") String param2) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "forwardTarget";
    }

    /**
     * result.jsp中直接通过request域获取数据，以下两种方式均可。
     *
     * ${requestScope.school.schoolName}
     *
     * ${school.schoolName}
     * @param school
     * @return
     * @throws Exception
     */
    @RequestMapping("/forwardStr.do")
    public String forwardStr(Image school)throws Exception{

        //默认会使用转发
        //return "result";
        //显式的使用转发
        return "forward:/jsp/result.html";
    }

    /**
     * result.jsp中需要通过param来获取数据：
     * ${param.schoolName}
     *
     * ${param.address}
     * @param school
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/redirectStr.do")
    public String redirectStr(Image school, Model model)throws Exception{

        //这里的数据同样会放在url中，所以只能传递基本数据类型和String类型
        model.addAttribute("schoolName", school.getDescription());
        model.addAttribute("address", school.getFileName());

        return "redirect:/jsp/result.jsp";
    }


    /*
     * redirect 目标有三种构建方式
     * 1. 使用 redirect: 前缀url方式构建目标url
     * 2. 使用 RedirectView 类型指定目标
     * 3. 使用 ModelAndView 类型指定目标, ModelAndView 视图名默认是forward, 所以对于redirect, 需要加上 redirect: 前缀
     * */
    @RequestMapping("/noParamRedirect")
    public RedirectView noParamTest() {
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("noParamTarget");
        return redirectTarget;
    }

    @RequestMapping("/noParamTarget")
    public String redirectTarget() {
        return "noParamTarget";
    }

    @RequestMapping("/withParamRedirect")
    public RedirectView withParamRedirect(RedirectAttributes redirectAttributes) {
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("withParamTarget");

        redirectAttributes.addAttribute("param1", "value1");
        redirectAttributes.addAttribute("param2", "value2");
        return redirectTarget;
    }

    @RequestMapping("/withParamTarget")
    public String withParamTarget(Model model, @RequestParam("param1") String param1,
                                  @RequestParam("param2") String param2) {
        model.addAttribute("param1", param1);
        model.addAttribute("param2", param2);
        return "withParamTarget";
    }

    @RequestMapping("/withFlashRedirect")
    public RedirectView withFlashTest(RedirectAttributes redirectAttributes) {
        RedirectView redirectTarget = new RedirectView();
        redirectTarget.setContextRelative(true);
        redirectTarget.setUrl("withFlashTarget");

        redirectAttributes.addAttribute("param", "value");
        redirectAttributes.addFlashAttribute("flashParam", "flashValue");
        return redirectTarget;
    }


    /*
     * redirectAttributes.addAttribute加的attr, 使用 @RequestParam()来fetch
     * redirectAttributes.addFlashAttribute()加的attr, 使用 @ModelAttribute()来fetch
     * */
    @RequestMapping("/withFlashTarget")
    public String withFlashTarget(Model model, @RequestParam("param") String param,
                                  @ModelAttribute("flashParam") String flashParam) {
        model.addAttribute("param", param);
        model.addAttribute("flashParam", flashParam);
        return "withFlashTarget";
    }



    @GetMapping("/input")
    public String input() {
        return "input";
    }

    /*
     * form 提交后, 如果form数据有问题, 使用redirectAttributes.addFlashAttribute()加上 flash message.
     * addFlashAttribute()可以是任意类型的数据(不局限在String等基本类型)
     * addFlashAttribute() 加的 attr, 不会出现在url 地址栏上.
     * addFlashAttribute() 加的 attr, 一旦fetch后, 就会自动清空, 非常适合 form 提交后 feedback Message.
     * */
    @PostMapping("/submit")
    public RedirectView submit(RedirectAttributes redirectAttributes) {
        boolean passed = false;
        if (passed==false) {
            RedirectView redirectTarget = new RedirectView();
            redirectTarget.setContextRelative(true);
            redirectTarget.setUrl("input");
            redirectAttributes.addFlashAttribute("errorMessage", "some error information here");
            return redirectTarget;
        }else {
            RedirectView redirectTarget = new RedirectView();
            redirectTarget.setContextRelative(true);
            redirectTarget.setUrl("inputOK");
            return redirectTarget;
        }
    }

    @RequestMapping("/gen1")
    @ResponseBody
    public String genFreemarkerFile()throws Exception
    {

        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        Resource resource = new ClassPathResource("templates/");
        configuration.setDirectoryForTemplateLoading(resource.getFile());
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        // 第四步：加载一个模板，创建一个模板对象。
        /**
         hello.ftl的内容为
         ${hello!}${name!}
         **/
        Template template = configuration.getTemplate("hello.ftl");
        // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataModel = new HashMap<>();
        //向数据集中添加数据
        dataModel.put("hello", "this is my first freemarker test.");
        dataModel.put("name", "zhangli");
        // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。请确保D盘存在
        Writer out = new FileWriter(new File("D://hello.html"));
        // 第七步：调用模板对象的process方法输出文件。
        template.process(dataModel, out);
        // 第八步：关闭流。
        out.close();
        return "ok";
    }
}
