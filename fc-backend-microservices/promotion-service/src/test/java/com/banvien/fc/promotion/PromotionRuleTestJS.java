package com.banvien.fc.promotion;

import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.dto.rules.Enum.BaseOn;
import com.banvien.fc.promotion.dto.rules.Enum.DiscountType;
import com.banvien.fc.promotion.dto.rules.Enum.Operation;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.banvien.fc.promotion.dto.rules.RuleInputDTO;
import com.banvien.fc.promotion.utils.rule.RuleBuilder;
import com.banvien.fc.promotion.utils.rule.RuleProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.script.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class PromotionRuleTestJS {

    @Test
    public void test() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("logic.js").toURI()), StandardCharsets.UTF_8));
        PromotionRuleDTO dto = new PromotionRuleDTO();
        dto.getIds().add(851L);
        dto.getIds().add(848L);
//        dto.getIds().add(14879L);
//        dto.getIds().add(3L);
//        dto.getIds().add(4L);
//        dto.getIds().add(5L);
        dto.setBaseOn(BaseOn.QUANTITY);
        dto.setOperation(Operation.OR);
        dto.setPromotionType(PromotionType.BRAND);
//        dto.getExcludeIds().add(1L);
//        dto.getExcludeIds().add(3L);
//        dto.getExcludeIds().add(5L);

        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setMinValue(100);
        discountDTO.setDiscountValue(10);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
//        discountDTO.getTargetIds().add(10577L);
//        discountDTO.getTargetIds().add(2L);
//        discountDTO.getTargetIds().add(3L);
        dto.getDiscounts().add(discountDTO);

        discountDTO = new DiscountDTO();
        discountDTO.setMinValue(200);
        discountDTO.setDiscountValue(20);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
//        discountDTO.getTargetIds().add(2L);
//        discountDTO.getTargetIds().add(3L);
//        discountDTO.getTargetIds().add(4L);
        dto.getDiscounts().add(discountDTO);

        String rule = RuleBuilder.createRuleTest(dto);

//        engine.eval("var rule = " + rule);
//        engine.eval("var data = {\"products\":[{\"id\":1,\"amount\":500,\"quantity\"  :1,\"categoryId\":1,\"brandId\":1},{\"id\":2,\"amount\":200,\"quantity\":3,\"categoryId\":1,\"brandId\":2},{\"id\":3,\"amount\":50,\"quantity\":2,\"categoryId\":2,\"brandId\":2},{\"id\":4,\"amount\":1000,\"quantity\":1,\"categoryId\":1,\"brandId\":3},{\"id\":5,\"amount\":20,\"quantity\":10,\"categoryId\":3,\"brandId\":1}],\"isByPass\":false}");
        ObjectMapper oMapper = new ObjectMapper();
        String sampleData = "{\"products\":[{\"id\":10506,\"amount\":7500.0,\"quantity\":50,\"categoryId\":377,\"brandId\":848},{\"id\":10739,\"amount\":15000.0,\"quantity\":100,\"categoryId\":385,\"brandId\":917},{\"id\":11654,\"amount\":3000.0,\"quantity\":20,\"categoryId\":368,\"brandId\":851},{\"id\":14879,\"amount\":2850.0,\"quantity\":19,\"categoryId\":368,\"brandId\":851}],\"isByPass\":false,\"byPass\":false}";

//        RuleInputDTO data = oMapper.readValue(sampleData, RuleInputDTO.class);

        String rs = RuleProcessor.getInstance().execute(rule, sampleData);
//        engine.eval("var result = JSON.stringify(jsonLogic.apply(rule,data))");
//        String rs = (String) engine.get("result");

        DiscountDTO dtos = oMapper.readValue(rs, DiscountDTO.class); // json 2 obj
        String tmp = oMapper.writeValueAsString(rs); // obj 2 json
//        assertThat(dto.getDiscountValue()).isEqualTo(15);
    }

    @Test
    public void test1() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        engine.eval(Files.newBufferedReader(Paths.get("logic.js"), StandardCharsets.UTF_8));

//        engine.eval("var rule = {}");
        engine.eval("var rule = {\"countIn\": [{\"var\": \"products\"}, \"id\", [1,2,3,4,5]]}");

//        engine.eval("var rule = {\"if\":[{\"and\":[{\">\":[{\"countIn\":[{\"var\":\"products\"},\"id\",[1,2,3,4,5]]},0]},{\">=\":[{\"sumIn\":[{\"var\":\"products\"},\"quantity\",\"id\",[1,2,3,4,5]]},10]}]},{\"out\":[{\"maxOf\":[\"minValue\",{\"sumIn\":[{\"var\":\"products\"},\"quantity\",\"id\",[1,2,3,4,5]]},\"minValue\",[{\"minValue\":10,\"discountValue\":10,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[2,3,4]},{\"minValue\":20,\"discountValue\":20,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[6,9,8]}]]},{\"maxOf\":[\"discountValue\",{\"sumIn\":[{\"var\":\"products\"},\"quantity\",\"id\",[1,2,3,4,5]]},\"minValue\",[{\"minValue\":10,\"discountValue\":10,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[2,3,4]},{\"minValue\":20,\"discountValue\":20,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[6,9,8]}]]},\"percentOff\",{\"maxOf\":[\"targetIds\",{\"sumIn\":[{\"var\":\"products\"},\"quantity\",\"id\",[1,2,3,4,5]]},\"minValue\",[{\"minValue\":10,\"discountValue\":10,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[2,3,4]},{\"minValue\":20,\"discountValue\":20,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[6,9,8]}]]},{\"selectIn\":[{\"var\":\"products\"},\"id\",\"id\",[1,2,3,4,5]]}]},null]}");
//        engine.eval("var rule = {\"maxOf\":[\"minValue\",{\"sumIn\":[{\"var\":\"products\"},\"quantity\",\"id\",[1,2,3,4,5]]},\"minValue\",[{\"minValue\":10,\"discountValue\":10,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[2,3,4]},{\"minValue\":20,\"discountValue\":20,\"discountType\":\"PERCENT_OFF\",\"targetIds\":[6,9,8]}]]}");

        engine.eval("var data = { \"products\":[{\"id\":3,\"price\":500,\"quantity\":1,\"categoryId\":1,\"brandId\":1},{\"id\":2,\"price\":200,\"quantity\":3,\"categoryId\":1,\"brandId\":2},{\"id\":3,\"price\":50,\"quantity\":2,\"categoryId\":2,\"brandId\":2},{\"id\":4,\"price\":1000,\"quantity\":1,\"categoryId\":1,\"brandId\":3},{\"id\":5,\"price\":20,\"quantity\":10,\"categoryId\":3,\"brandId\":1}]}");

        engine.eval("var result = JSON.stringify(jsonLogic.apply(rule,data))");
        String rs = (String) engine.get("result");
        int k = 10;
    }

    // ORDER/ SHIPPING
    @Test
    public void createNewRuleOrder() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("logic.js").toURI()), StandardCharsets.UTF_8));
        PromotionRuleDTO dto = new PromotionRuleDTO();

        dto.setBaseOn(BaseOn.AMOUNT);
        dto.setPromotionType(PromotionType.SHIPPING);


        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setMinValue(100);
        discountDTO.setDiscountValue(10);
        discountDTO.setDiscountType(DiscountType.SHIPPING_DISCOUNT);
        dto.getDiscounts().add(discountDTO);

        discountDTO = new DiscountDTO();
        discountDTO.setMinValue(200);
        discountDTO.setDiscountValue(20);
        discountDTO.setDiscountType(DiscountType.SHIPPING_DISCOUNT);
        dto.getDiscounts().add(discountDTO);

        String rule = RuleBuilder.createRuleTest(dto);
        int king = -1;
    }

    // NEXT PURCHASE
    @Test
    public void createNewRuleNextPurchase() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("logic.js").toURI()), StandardCharsets.UTF_8));
        PromotionRuleDTO dto = new PromotionRuleDTO();
        dto.getIds().add(368L);
        dto.getIds().add(377L);

        dto.setBaseOn(BaseOn.QUANTITY);
        dto.setOperation(Operation.OR);
        dto.setPromotionType(PromotionType.CATEGORY);


        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setMinValue(40);
        discountDTO.setDiscountValue(10);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
        discountDTO.getTargetIds().add(10577L); // The same for all threshold
        dto.getDiscounts().add(discountDTO);

        discountDTO = new DiscountDTO();
        discountDTO.setMinValue(50);
        discountDTO.setDiscountValue(20);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
        discountDTO.getTargetIds().add(10577L); // The same for all threshold
        dto.getDiscounts().add(discountDTO);

        String rule = RuleBuilder.createRuleTest(dto);
        int king = -1;
    }

    // PRODUCT with FIX_FRICE
    @Test
    public void createNewRuleProduct() throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource("logic.js").toURI()), StandardCharsets.UTF_8));
        PromotionRuleDTO dto = new PromotionRuleDTO();
        dto.getIds().add(14879L);
        dto.getIds().add(2L);

        dto.setBaseOn(BaseOn.QUANTITY);
        dto.setOperation(Operation.OR);
        dto.setPromotionType(PromotionType.PRODUCT);


        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setMinValue(10);
        discountDTO.setDiscountValue(2500);
        discountDTO.setDiscountType(DiscountType.FIX_PRICE);
        discountDTO.getTargetIds().add(14729L);
        dto.getDiscounts().add(discountDTO);

        discountDTO = new DiscountDTO();
        discountDTO.setMinValue(20);
        discountDTO.setDiscountValue(2000);
        discountDTO.setDiscountType(DiscountType.FIX_PRICE);
        discountDTO.getTargetIds().add(14729L);
        dto.getDiscounts().add(discountDTO);

        String rule = RuleBuilder.createRuleTest(dto);
        int king = -1;
    }


    @Test
    public void testDeepCopy() throws Exception {
        Integer a = 12;
        Integer b = a;
        b = 1;
        System.out.println(a);
        System.out.println(b);

        PromotionRuleDTO dto1 = new PromotionRuleDTO();
        PromotionRuleDTO dto2 = new PromotionRuleDTO();



        dto1.setFirstOrder(true);
//        dto2 = dto1;
        dto2.setFirstOrder(false);
        int t = 0;

    }

    @Test
    public void testCovertJS2DTO() throws Exception {
        JSONObject jsonObject = new JSONObject("{\"outletPromotionId\":null,\"outletId\":5296,\"outletName\":\"User2107\",\"maxPerPromotion\":100,\"customerGroupIds\":[],\"priority\":10,\"promotionRule\":{\"ids\":[],\"promotionType\":\"ORDER\",\"excludeIds\":[],\"baseOn\":\"AMOUNT\",\"operation\":\"AND\",\"discountType\":\"FIX_PRICE\",\"discounts\":[{\"minValue\":20,\"discountValue\":2,\"discountType\":\"FIX_PRICE\",\"targetIds\":[18169],\"productIds\":[]}],\"expiredDate\":null,\"firstOrder\":false,\"isAll\":true,\"easyOrderCode\":null},\"offerNextProductId\":null,\"customerTargetIds\":null}");
        JSONObject jsonObjectRule = jsonObject.getJSONObject("promotionRule");

        JSONObject discountsJS = jsonObject.getJSONObject("promotionRule").getJSONArray("discounts").getJSONObject(0);

        ObjectMapper oMapper = new ObjectMapper();
        DiscountDTO dto = oMapper.readValue(discountsJS.toString(), DiscountDTO.class); // json 2 obj

        JSONObject expiredDateJS = jsonObjectRule.getJSONObject("expiredDate");
        Long expireDate;
        if (expiredDateJS != null){
            expireDate = jsonObject.getLong("expiredDate");
        }

        int k = 0;
    }

    @Test
    public void testFor() throws Exception {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(100L);
        list.add(200L);
        list.add(300L);
        list.add(400L);
        for (int i = 0; i < list.size(); i++){
            System.out.println(list.get(i));
        }

        int k = 0;
    }

}
