package cn.mrxiao.graduation.controller;

import cn.mrxiao.graduation.beans.Store;
import cn.mrxiao.graduation.service.StoreLoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author mrxiao
 * @date 2020/4/14 - 19:25
 **/
@Controller
@CrossOrigin
public class StoreLoginController {
    //日志记录器
    @Autowired
    Logger logger;
    @Autowired
    StoreLoginService storeLoginService;

    @GetMapping("/store-login")
    public String storeHome(HttpServletRequest request, HttpServletResponse response) {
        String loginEmail="",up="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail")){
                    loginEmail=c.getValue();
                    Cookie cookie = new Cookie(c.getName(),null);
                    cookie.setMaxAge(0);
                    cookie.setPath(request.getContextPath());  // 相同路径
                    response.addCookie(cookie);
                }
                if(c.getName().equals("up"))
                    up=c.getValue();
            }
        }
        request.getSession().removeAttribute(loginEmail+"LoginJudge");
        return "login/store-login";
    }

    /**
     * 获取验证码映射
     *
     * @param email 邮箱
     * @return
     */
    @ResponseBody
    @PostMapping("/store-login/produceSecurityCode")
    public String securityCode(@RequestParam(value = "email") String email,HttpServletResponse response, HttpServletRequest request) {
        String security = null;
        try {
            security = storeLoginService.sendSecurityCode(email);
            logger.info("商家获取验证码成功" + email);
        } catch (Exception e) {
            logger.error("商家获取验证码失败" + email);
            return "false";
        }
        request.getSession().setAttribute(email + "StoreSecurityCode", security);
        //设置验证码session存活时间为15分钟，秒为单位，即在没有活动15分钟后，session将失效
        request.getSession().setMaxInactiveInterval(15*60);
        return "true";
    }

    /**
     * 商家提交登陆映射
     *
     * @param email        邮箱
     * @param securityCode 验证码
     * @return
     */
    @PostMapping("/submit-store-login")
    public String login(@RequestParam("email") String email, @RequestParam("securityCode") String securityCode, RedirectAttributes attributes
            , HttpServletRequest request, HttpServletResponse response) {
        email += "@qq.com";
        if ((request.getSession().getAttribute(email + "StoreSecurityCode")) != null
                && securityCode.equals(request.getSession().getAttribute(email + "StoreSecurityCode"))) {
            logger.info("商家登陆成功" + email);
            Cookie cookie = new Cookie("loginEmail", email);
            request.getSession().setAttribute(email+"LoginJudge",true);
            request.getSession().setMaxInactiveInterval(4*60*60);
            cookie.setMaxAge(4*60*60);
            response.addCookie(cookie);
            String uuid= UUID.randomUUID().toString().replace("-", "");
            if (storeLoginService.checkStoreExist(email)) {
                return "redirect:/store";
            }
            return "redirect:/store-login/register-store-info";
        } else {
            //重定向到映射需要用RedirectAttributes传递信息
            attributes.addFlashAttribute("msg", "验证码错误，请重新获取");
            logger.error("商家登陆失败，验证码错误" + email);
            return "redirect:/store-login";
        }
    }

    @GetMapping("/store-login/register-store-info")
    public String login(HttpServletRequest request, Model model) {
        String loginEmail="";
        Cookie[] cs=request.getCookies();
        if(cs!=null) {
            for (Cookie c : cs) {
                if (c.getName().equals("loginEmail"))
                    loginEmail=c.getValue();
            }
        }
        Store store=storeLoginService.getByEmail(loginEmail);
        if (store!=null){
            model.addAttribute("loginStorePhoneNumber",store.getPhoneNumber());
            model.addAttribute("loginStoreName",store.getStoreName());
            model.addAttribute("loginStoreCapacity",store.getCapacity());
            model.addAttribute("loginStoreEquipmentEatTime",store.getEquipmentEatTime());
            model.addAttribute("loginStoreState",store.getState());
            model.addAttribute("loginStoreCity",store.getCity());
            model.addAttribute("loginStoreAddress",store.getAddress());
            model.addAttribute("loginStorePositionLat",store.getPositionLat());
            model.addAttribute("loginStorePositionLng",store.getPositionLng());
            model.addAttribute("loginStoreNotice",store.getNotice());
        }
        return "login/register-store-info";
    }

    @PostMapping("/submit-store-info")
    public String submitRegisterInfo(Store store, @RequestParam("storePictureFile") MultipartFile storePictureFile, @RequestParam("licensePictureFile") MultipartFile licensePictureFile
            , @RequestParam("identityPictureFile") MultipartFile identityPictureFile,@RequestParam("wechatPictureFile")MultipartFile wechatPictureFile
            ,@RequestParam("zhifubaoPictureFile")MultipartFile zhifubaoPictureFile, HttpServletRequest request, RedirectAttributes attributes) {
        try {
            store.setStorePicture(storeLoginService.saveStorePicture(request,storePictureFile));
            store.setLicensePicture(storeLoginService.saveLicensePicture(request,licensePictureFile));
            store.setIdentityPicture(storeLoginService.saveIdentityPicture(request,identityPictureFile));
            store.setWechatPicture(storeLoginService.saveWechatPicture(request,wechatPictureFile));
            store.setZhifubaoPicture(storeLoginService.saveZhiFuBaoPicture(request,zhifubaoPictureFile));
        } catch (Exception e) {
            attributes.addFlashAttribute("msg", "保存失败");
            return "redirect:/store-login/register-store-info";
        }
        if (store.getPositionLat().isEmpty() || store.getPositionLng().isEmpty()) {
            attributes.addFlashAttribute("loginStoreName",store.getStoreName());
            attributes.addFlashAttribute("loginStorePhoneNumber",store.getPhoneNumber());
            attributes.addFlashAttribute("loginStoreCapacity",store.getCapacity());
            attributes.addFlashAttribute("loginStoreEquipmentEatTime",store.getEquipmentEatTime());
            attributes.addFlashAttribute("loginStoreState",store.getState());
            attributes.addFlashAttribute("loginStoreCity",store.getCity());
            attributes.addFlashAttribute("loginStoreAddress",store.getAddress());
            attributes.addFlashAttribute("loginStoreNotice",store.getNotice());
            attributes.addFlashAttribute("msg", "必须在地图上标出餐厅所在准确位置");
            logger.error("商家编辑数据库信息失败" + store);
            return "redirect:/store-login/register-store-info";
        }
        Store store1=storeLoginService.getByEmail(store.getEmail());
        Store store2=storeLoginService.updateInfo(store);
        if(store1!=null){
            storeLoginService.updateCityStoreList(store1.getCity());
            storeLoginService.changeStoreLicenseByStoreId(store1.getId(),0);
            try{
                storeLoginService.sendStoreEditInfo(store1.getEmail());
                logger.info("给商家发送修改信息邮件成功");
            }catch (Exception e){
                logger.info("给商家发送修改信息邮件失败");
            }
        }else{
            try{
                storeLoginService.sendStoreSuccessRegister(store.getEmail());
                logger.info("给商家发送注册成功邮件成功");
            }catch (Exception e){
                logger.info("给商家发送注册成功邮件失败");
            }
        }
        storeLoginService.updateCityStoreList(store.getCity());
        storeLoginService.updateStoreById(store2.getId());
        storeLoginService.updateAllStore();
        return "redirect:/store";
    }
}