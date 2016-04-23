package info.xiaomo.admin.test.UserTest;

import com.alibaba.fastjson.JSONObject;
import info.xiaomo.admin.test.base.BaseTest;
import info.xiaomo.core.constant.GenderType;
import info.xiaomo.core.constant.WebDefaultValueConst;
import info.xiaomo.core.exception.UserNotFoundException;
import info.xiaomo.core.model.UserModel;
import info.xiaomo.core.service.UserService;
import info.xiaomo.core.untils.DateUtil;
import info.xiaomo.core.untils.MD5Util;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.Date;

/**
 * │＼＿＿╭╭╭╭╭＿＿／│
 * │　　　　　　　　　│
 * │　　　　　　　　　│
 * │　－　　　　　　－│
 * │≡　　　　ｏ　≡   │
 * │　　　　　　　　　│
 * ╰——┬Ｏ◤▽◥Ｏ┬——╯
 * ｜　　ｏ　　｜
 * ｜╭－－－╮把今天最好的表现当作明天最新的起点．．～
 * いま 最高の表現 として 明日最新の始発．．～
 * Today the best performance  as tomorrow newest starter!
 * Created by IntelliJ IDEA.
 *
 * @author: xiaomo
 * @github: https://github.com/qq83387856
 * @email: hupengbest@163.com
 * @QQ_NO: 83387856
 * @Date: 16/4/5 23:41
 * @Description: admin测试
 * @Copyright(©) 2015 by xiaomo.
 */
public class UserControllerTest extends BaseTest {

    @Autowired
    private UserService service;


    @Test
    public void testRegister() {
        UserModel userModel = new UserModel();
        userModel.setEmail("hupengbest@163.com");
        userModel.setImgUrl(WebDefaultValueConst.defaultImage);
        userModel.setValidateCode(MD5Util.encode(userModel.getEmail()));
        userModel.setAddress("万轮科技园");
        userModel.setPhone(15172299114L);
        userModel.setGender(1);
        userModel = service.addUser(userModel);
        System.out.println(JSONObject.toJSON(userModel));
    }


    @Test
    public void testFindAll() {
        Page<UserModel> all = service.findAll(1, 5);
        for (UserModel userModel : all) {
            System.out.println(JSONObject.toJSON(userModel));
        }
    }

    @Test
    public void testFindById() {
        UserModel userModel = service.findUserById(25L);
        System.out.println(JSONObject.toJSON(userModel));
    }

    @Test
    public void testFindByEmail() {
        UserModel userModel = service.findUserByEmail("83387856@qq.com");
        System.out.println(JSONObject.toJSON(userModel));
    }

    @Test
    public void testUpdate() throws UserNotFoundException {
        UserModel userModel = new UserModel();
        userModel.setEmail("hupengbest@163.com");
        userModel.setNickName("hp");
        userModel.setPassword(MD5Util.encode("hp"));
        userModel.setImgUrl(WebDefaultValueConst.defaultImage);
        userModel.setValidateCode(MD5Util.encode(userModel.getEmail()));
        userModel.setAddress("万轮科技园7号楼");
        userModel.setPhone(15172299114L);
        userModel.setGender(0);
        userModel = service.updateUser(userModel);
        System.out.println(JSONObject.toJSON(userModel));
    }

    @Test
    public void testDelete() throws UserNotFoundException {
        UserModel userModel = service.deleteUserById(26L);
        System.out.println(JSONObject.toJSON(userModel));
    }

    @Test
    public void testValidateEmail() {
        String email = "83387856@qq.com";
        String password = "woshixiaomo";
        //数据访问层，通过email获取用户信息
        UserModel userModel = service.findUserByEmail("83387856@qq.com");
        //验证用户是否存在
        if (userModel != null) {
            System.out.println(201);
            return;
        }
        //验证码是否过期
        if (DateUtil.getNowOfMills() + DateUtil.ONE_DAY_IN_MILLISECONDS * 2 < DateUtil.getNowOfMills()) {
            System.out.println("己过期");
            return;
        }
        //激活
        userModel = new UserModel();
        userModel.setNickName(email);
        userModel.setEmail(email);
        userModel.setGender(GenderType.secret);
        userModel.setImgUrl(WebDefaultValueConst.defaultImage);//默认是个百度的LOGO，作测试用
        userModel.setValidateStatus(1);//状态:己激活
        userModel.setValidateCode(MD5Util.encode(email));
        userModel.setPhone(0L);
        userModel.setAddress("");
        userModel.setPassword(MD5Util.encode(password));
        userModel.setCreateTime(new Date());
        userModel.setUpdateTime(new Date());
        userModel = service.addUser(userModel);
        System.out.println(userModel.getNickName());
    }
}
