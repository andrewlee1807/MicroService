package com.banvien.fc.promotion;

import com.banvien.fc.promotion.dto.rules.DiscountDTO;
import com.banvien.fc.promotion.dto.rules.Enum.BaseOn;
import com.banvien.fc.promotion.dto.rules.Enum.DiscountType;
import com.banvien.fc.promotion.dto.rules.Enum.Operation;
import com.banvien.fc.promotion.dto.rules.Enum.PromotionType;
import com.banvien.fc.promotion.dto.rules.PromotionRuleDTO;
import com.banvien.fc.promotion.dto.rules.RuleInputDTO;
import com.banvien.fc.promotion.dto.rules.RuleProductDTO;
import com.banvien.fc.promotion.utils.rule.RuleBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import io.github.jamsesso.jsonlogic.JsonLogic;
import io.github.jamsesso.jsonlogic.JsonLogicException;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by son.nguyen on 6/18/2020.
 */
//@RunWith(SpringRunner.class)
public class PromotionRulesTests {

    static JsonLogic jsonLogic;
    Map<String, Object> orderData;
    String rule;
    DiscountDTO discountDTO;

    @BeforeClass
    public static void init() {
        jsonLogic = new JsonLogic();
        jsonLogic.addOperation("out", args -> new DiscountDTO(args[0], args[1], args[2], args[3]));
    }

    @Before
    public void setSampleOrder() {
        RuleInputDTO ruleInputData = new RuleInputDTO();
        List<RuleProductDTO> products = new ArrayList<>();

        products.add(new RuleProductDTO(1L, 500D, 1, 1L, 1L));
        products.add(new RuleProductDTO(2L, 200D, 3, 1L, 2L));
        products.add(new RuleProductDTO(3L, 50D, 2, 2L, 2L));
        products.add(new RuleProductDTO(4L, 1000D, 1, 1L, 3L));
        products.add(new RuleProductDTO(10506L, 20D, 10, 3L, 1L));
        ruleInputData.setProducts(products);
//        ruleInputData.setTotalPrice(500D + 200D + 50D + 1000D + 20D);
//        ruleInputData.setTotalQuantity(1 + 3 + 2 + 1 + 10);
        ObjectMapper oMapper = new ObjectMapper();
        orderData = oMapper.convertValue(ruleInputData, Map.class);
    }

    @Test
    public void test() throws Exception {
        PromotionRuleDTO dto = new PromotionRuleDTO();
        dto.getIds().add(10506L);
        dto.getIds().add(368L);
        dto.setBaseOn(BaseOn.QUANTITY);
        dto.setOperation(Operation.OR);
        dto.setPromotionType(PromotionType.CATEGORY);
        dto.getExcludeIds().add(1L);
        dto.getExcludeIds().add(2L);

        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setMinValue(5);
        discountDTO.setDiscountValue(10);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
        discountDTO.getTargetIds().add(1L);
        discountDTO.getTargetIds().add(2L);
        discountDTO.getTargetIds().add(3L);
        dto.getDiscounts().add(discountDTO);

        discountDTO = new DiscountDTO();
        discountDTO.setMinValue(10);
        discountDTO.setDiscountValue(20);
        discountDTO.setDiscountType(DiscountType.PERCENT_OFF);
        discountDTO.getTargetIds().add(2L);
        discountDTO.getTargetIds().add(3L);
        discountDTO.getTargetIds().add(4L);
        dto.getDiscounts().add(discountDTO);

        rule = RuleBuilder.createRule(dto);
        DiscountDTO result = (DiscountDTO) jsonLogic.apply(rule, orderData);
        assertThat(result.getDiscountValue()).isEqualTo(15);
    }

    @Test
    public void asdf() throws Exception {
        byte[] s = new byte[30000];
        (this.getClass().getClassLoader().getResourceAsStream("logic.js")).read(s);
        String cc = new String(s);
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        engine.eval(cc);

        engine.eval("var rule = {\"if\":[{\"and\":[{\"and\":[{\">\":[{\"reduce\":[{\"filter\":[{\"var\":\"products\"},{\"and\":[{\"==\":[{\"var\":\"id\"},\"1\"]},{\"!=\":[{\"var\":\"id\"},\"1\"]},{\"!=\":[{\"var\":\"id\"},\"2\"]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},0]},{\">\":[{\"reduce\":[{\"filter\":[{\"var\":\"products\"},{\"and\":[{\"==\":[{\"var\":\"id\"},\"2\"]},{\"!=\":[{\"var\":\"id\"},\"1\"]},{\"!=\":[{\"var\":\"id\"},\"2\"]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},0]}]},{\">=\":[{\"reduce\":[{\"filter\":[{\"var\":\"products\"},{\"and\":[{\"or\":[{\"==\":[{\"var\":\"id\"},\"1\"]},{\"==\":[{\"var\":\"id\"},\"2\"]}]},{\"!=\":[{\"var\":\"id\"},\"1\"]},{\"!=\":[{\"var\":\"id\"},\"2\"]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},100.0]}]},{\"out\":[{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"100.0\"]},{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"200.0\"]},\"200.0\",\"100.0\"]},\"null\"]},{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"100.0\"]},{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"200.0\"]},\"20.0\",\"10.0\"]},\"null\"]},\"percentOff\",{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"100.0\"]},{\"if\":[{\">=\":[{\"var\":\"totalQuantity\"},\"200.0\"]},[6,9,8],[2,3,4]]},\"null\"]}]},\"null\"]}");
        engine.eval("var data = {\"products\":[{\"id\":1,\"price\":500.0,\"quantity\":1,\"categoryId\":1,\"brandId\":1},{\"id\":2,\"price\":200.0,\"quantity\":3,\"categoryId\":1,\"brandId\":2},{\"id\":3,\"price\":50.0,\"quantity\":2,\"categoryId\":2,\"brandId\":2},{\"id\":4,\"price\":1000.0,\"quantity\":1,\"categoryId\":1,\"brandId\":3},{\"id\":5,\"price\":20.0,\"quantity\":10,\"categoryId\":3,\"brandId\":1}],\"totalPrice\":1770.0,\"totalQuantity\":17}");

        engine.eval("var result = JSON.stringify(jsonLogic.apply(rule,data))");
        String rs = (String) engine.get("result");

        ObjectMapper oMapper = new ObjectMapper();
        DiscountDTO dto = oMapper.readValue(rs, DiscountDTO.class); // json 2 obj
        String tmp = oMapper.writeValueAsString(dto); // obj 2 json
        assertThat(dto.getDiscountValue()).isEqualTo(15);
        int k = 10;
    }

    @Test
    public void shippingTest() throws JsonLogicException {
        PromotionRuleDTO dto = new PromotionRuleDTO();
        List<Long> ids = new ArrayList<>();
        ids.add(3L);
        ids.add(2L);
        dto.setIds(ids);
        dto.setPromotionType(PromotionType.SHIPPING);
        List<DiscountDTO> discounts = new ArrayList<>();
        discounts.add(new DiscountDTO(5, 10, DiscountType.DELIVERY_DISCOUNT, null));
        discounts.add(new DiscountDTO(10, 15, DiscountType.DELIVERY_DISCOUNT, null));
        discounts.add(new DiscountDTO(15, 20, DiscountType.DELIVERY_DISCOUNT, null));
        discounts.add(new DiscountDTO(20, 30, DiscountType.DELIVERY_DISCOUNT, null));
        dto.setDiscounts(discounts);
        rule = RuleBuilder.createRule(dto);
        DiscountDTO result = (DiscountDTO) jsonLogic.apply(rule, orderData);
        assertThat(result.getDiscountValue()).isEqualTo(20);
    }

    @Test
    public void tmp() throws JsonLogicException {
        rule = "{\"and\":[{\"or\":[{\">\":[{\"reduce\":[{\"var\":[\"searchResult\",{\"filter\":[{\"var\":\"products\"},{\"==\":[{\"var\":\"id\"},\"pid1\"]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},0]},{\">\":[{\"reduce\":[{\"var\":[\"searchResult\",{\"filter\":[{\"var\":\"products\"},{\"==\":[{\"var\":\"id\"},\"pid2\"]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},0]}]},{\">=\":[{\"reduce\":[{\"var\":[\"searchResult\",{\"filter\":[{\"var\":\"products\"},{\"or\":[{\"==\":[{\"var\":\"id\"},\"pid1\"]},{\"==\":[{\"var\":\"id\"},\"pid2\"]}]}]}]},{\"+\":[{\"var\":\"current.quantity\"},{\"var\":\"accumulator\"}]},0]},3]}]}";
        String datastr = "{\"products\":[{\"id\":\"pid1\",\"price\":10,\"quantity\":4,\"categoryId\":\"cid1\",\"brandId\":\"bid1\"},{\"id\":\"pid2\",\"price\":20,\"quantity\":2,\"categoryId\":\"cid1\",\"brandId\":\"bid2\"},{\"id\":\"pid3\",\"price\":20,\"quantity\":2,\"categoryId\":\"cid2\",\"brandId\":\"bid2\"},{\"id\":\"pid4\",\"price\":20,\"quantity\":2,\"categoryId\":\"cid1\",\"brandId\":\"bid3\"},{\"id\":\"pid5\",\"price\":20,\"quantity\":2,\"categoryId\":\"cid3\",\"brandId\":\"bid1\"}]}";
        Gson gson = new Gson();
        Map<String, Object> data = gson.fromJson(datastr, Map.class);
        DiscountDTO result = (DiscountDTO) jsonLogic.apply(rule, data);
        assertThat(result.getDiscountValue()).isEqualTo(15);
    }

    @Test
    public void test12() throws IOException {
        String data = getBase64EncodedImage("https://p.bigstockphoto.com/GeFvQkBbSLaMdpKXF1Zv_bigstock-Aerial-View-Of-Blue-Lakes-And--227291596.jpg");
        int k = 10;
    }

    private String getBase64EncodedImage(String imageURL) throws IOException {
        java.net.URL url = new java.net.URL(imageURL);
        InputStream is = url.openStream();
        byte[] bytes = org.apache.commons.io.IOUtils.toByteArray(is);
        return Base64.encodeBase64String(bytes);
    }

    @Test
    public void formatDiscountPromotion2Json() throws Exception {
        DiscountDTO discountDTO = new DiscountDTO();
        discountDTO.setDiscountType(DiscountType.AMOUNT_OFF);
        discountDTO.setDiscountValue(12);
        ObjectMapper oMapper = new ObjectMapper();
        String tmp = oMapper.writeValueAsString(discountDTO); // obj 2 json

        int k = 0;
    }
}
