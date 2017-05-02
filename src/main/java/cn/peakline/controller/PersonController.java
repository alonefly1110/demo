package cn.peakline.controller;

import cn.peakline.dto.PersonDTO;
import cn.peakline.service.PersonService;
import cn.peakline.utils.Constants;
import cn.peakline.utils.ReturnResult;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息
 *
 * @author maxiaoliang
 * @create 2017-04-26 下午2:47
 **/
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final Gson gson;

    @Autowired
    public PersonController(PersonService personService, Gson gson) {
        this.personService = personService;
        this.gson = gson;
    }

    @RequestMapping("/savePersonInfo")
    @ApiOperation(value = "保存用户信息")
    public String savePersonInfo(String callback, PersonDTO personDTO) {
        personService.savePersonInfo(personDTO);
        ReturnResult returnResult = new ReturnResult();
        returnResult.setStatus(Constants.SUCCESS_STATUS);
        returnResult.setMsg("保存成功");
        return callback + "(" + gson.toJson(returnResult) + ")";
    }
}
